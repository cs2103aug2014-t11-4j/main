package indigoSrc;

import java.util.ArrayList;

public class Search extends CommandClass {

	String displayLine = new String();
	
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return find();
	}
	
	public Search(Parser parser, TaskList taskList) {
		// TODO Auto-generated constructor stub
		parserVar = parser;
		taskListVar = taskList;
	}

	public String find(){
		StringBuilder StringList = new StringBuilder();
		int found = 0;
		String keyWords = parserVar.getCommand().trim().toLowerCase();
		ArrayList<FloatingTask> floatTaskList = taskListVar.getFloatingList();
		ArrayList<FloatingTask> timeTaskList = taskListVar.getTimedList();
		int floatTasks = floatTaskList.size();
		int timeTasks = timeTaskList.size();
		StringList.append("DeadLine Tasks found: " + "\n");
		for(int i=0; i<timeTasks; i++){
			if(timeTaskList.get(i).getDescription().contains(keyWords)){
				DeadlineTask temp = (DeadlineTask) timeTaskList.get(i);
				StringList.append(i+1 + ". " + temp.toString() + "\n");
				found++;
			}
		}
		int foundNow = found;
		if(foundNow==0){
			StringList.append("No deadline tasks found with search keys" + "\n");
		}
		StringList.append("\n" + "Floating Tasks found: " + "\n");
		for(int j=0; j<floatTasks; j++){
			if(floatTaskList.get(j).getDescription().contains(keyWords)){
				int sum = j + timeTasks + 1;
				StringList.append(sum + ". " + floatTaskList.get(j).getDescription() + "\n");
				found++;
			}
		}
		if(foundNow==found){
			StringList.append("No floating tasks found with search keys" + "\n");
		}
		
		//display = StringList.toString();
		if(found!=0){
			displayLine = "There are " + found + " tasks listed: " + "\n"; 
			displayLine += StringList.toString();
			return "Results found";
		} else {
			displayLine = "No Tasks with search words found" + "\n";
			return "Search words not found";
		}
	}
	
}
