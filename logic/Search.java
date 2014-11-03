package logic;

import indigoSrc.DeadlineTask;
import indigoSrc.FloatingTask;
import indigoSrc.Parser;
import indigoSrc.Task;
import indigoSrc.TaskList;

import java.util.ArrayList;

public class Search extends CommandClass {

	public String searchResult = new String();
	private int foundTasks = 0;
	
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
		ArrayList<Task> floatTaskList = taskListVar.getFloatingList();
		ArrayList<Task> timeTaskList = taskListVar.getTimedList();
		int floatTasks = floatTaskList.size();
		int timeTasks = timeTaskList.size();
		StringList.append("DeadLine Tasks found: " + "\n");
		for(int i=0; i<timeTasks; i++){
			if(timeTaskList.get(i).getDescription().toLowerCase().contains(keyWords)){
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
			if(floatTaskList.get(j).getDescription().toLowerCase().contains(keyWords)){
				int sum = j + timeTasks + 1;
				StringList.append(sum + ". " + floatTaskList.get(j).getDescription() + "\n");
				found++;
			}
		}
		if(foundNow==found){
			StringList.append("No floating tasks found with search keys" + "\n");
		}
		foundTasks = found;
		//display = StringList.toString();
		if(found!=0){
			searchResult = "There are " + found + " tasks listed: " + "\n"; 
			searchResult += StringList.toString();
			return "Results found";
		} else {
			searchResult = "No Tasks with search words found" + "\n";
			return "Search words not found";
		}
	}
	
	public String findWithNumInOrder(){
		StringBuilder StringList = new StringBuilder();
		int found = 1;
		String keyWords = parserVar.getCommand().trim().toLowerCase();
		ArrayList<Task> floatTaskList = taskListVar.getFloatingList();
		ArrayList<Task> timeTaskList = taskListVar.getTimedList();
		int floatTasks = floatTaskList.size();
		int timeTasks = timeTaskList.size();
		StringList.append("DeadLine Tasks found: " + "\n");
		for(int i=0; i<timeTasks; i++){
			if(timeTaskList.get(i).getDescription().toLowerCase().contains(keyWords)){
				DeadlineTask temp = (DeadlineTask) timeTaskList.get(i);
				StringList.append(found++ + ". " + temp.toString() + "\n");
			}
		}
		int foundNow = found-1;
		if(foundNow==0){
			StringList.append("No deadline tasks found with search keys" + "\n");
		}
		StringList.append("\n" + "Floating Tasks found: " + "\n");
		for(int j=0; j<floatTasks; j++){
			if(floatTaskList.get(j).getDescription().toLowerCase().contains(keyWords)){
				StringList.append(found++ + ". " + floatTaskList.get(j).getDescription() + "\n");
			}
		}
		if(foundNow==found-1){
			StringList.append("No floating tasks found with search keys" + "\n");
		}
		foundTasks = foundNow;
		//display = StringList.toString();
		if(found!=0){
			searchResult = "There are " + found + " tasks listed: " + "\n"; 
			searchResult += StringList.toString();
			return "Results found";
		} else {
			searchResult = "No Tasks with search words found" + "\n";
			return "Search words not found";
		}
	}
	
	public int getFound(){
		return foundTasks;
	}
	
	@Override
	public String undo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
