package logic;

import indigoSrc.DeadlineTask;
import indigoSrc.FloatingTask;
import indigoSrc.Parser;
import indigoSrc.TaskList;

import java.util.ArrayList;

public class Search extends CommandClass {

	private static final String NOT_FOUND = "Search words not found";
	private static final String NO_TASKS_FOUND = "No Tasks with search words found";
	private static final String FOUND = "Results found";
	private static final String NO_FLOATING_TASKS = "No floating tasks found with search keys";
	private static final String FLOATING_TASKS = "Floating Tasks found: ";
	private static final String NO_DEADLINE_TASKS = "No deadline tasks found with search keys";
	private static final String DEADLINE_TASKS = "DeadLine Tasks found: ";
	public String searchResult = new String();
	private int foundTasks = 0;
	private ArrayList<SearchListNode> searchList = new ArrayList<SearchListNode>();
	
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
		StringList.append(DEADLINE_TASKS + "\n");
		for(int i=0; i<timeTasks; i++){
			if(timeTaskList.get(i).getDescription().toLowerCase().contains(keyWords)){
				DeadlineTask temp = (DeadlineTask) timeTaskList.get(i);
				SearchListNode node = new SearchListNode(temp, i);
				searchList.add(node);
				StringList.append(i+1 + ". " + temp.toString() + "\n");
				found++;
			}
		}
		int foundNow = found;
		if(foundNow==0){
			StringList.append(NO_DEADLINE_TASKS + "\n");
		}
		StringList.append("\n" + FLOATING_TASKS + "\n");
		for(int j=0; j<floatTasks; j++){
			if(floatTaskList.get(j).getDescription().toLowerCase().contains(keyWords)){
				int sum = j + timeTasks + 1;
				FloatingTask temp = floatTaskList.get(j);
				SearchListNode node = new SearchListNode(temp, j);
				searchList.add(node);
				StringList.append(sum + ". " + temp.getDescription() + "\n");
				found++;
			}
		}
		if(foundNow==found){
			StringList.append(NO_FLOATING_TASKS + "\n");
		}
		foundTasks = found;
		//display = StringList.toString();
		if(found!=0){
			searchResult = "There are " + found + " tasks listed: " + "\n"; 
			searchResult += StringList.toString();
			return FOUND;
		} else {
			searchResult = NO_TASKS_FOUND + "\n";
			return NOT_FOUND;
		}
	}
	
	public String findWithNumInOrder(){
		StringBuilder StringList = new StringBuilder();
		int found = 1;
		String keyWords = parserVar.getCommand().trim().toLowerCase();
		ArrayList<FloatingTask> floatTaskList = taskListVar.getFloatingList();
		ArrayList<FloatingTask> timeTaskList = taskListVar.getTimedList();
		int floatTasks = floatTaskList.size();
		int timeTasks = timeTaskList.size();
		StringList.append(DEADLINE_TASKS + "\n");
		for(int i=0; i<timeTasks; i++){
			if(timeTaskList.get(i).getDescription().toLowerCase().contains(keyWords)){
				DeadlineTask temp = (DeadlineTask) timeTaskList.get(i);
				SearchListNode node = new SearchListNode(temp, i);
				searchList.add(node);
				StringList.append(found++ + ". " + temp.toString() + "\n");
			}
		}
		int foundNow = found-1;
		if(foundNow==0){
			StringList.append(NO_DEADLINE_TASKS + "\n");
		}
		StringList.append("\n" + FLOATING_TASKS + "\n");
		for(int j=0; j<floatTasks; j++){
			if(floatTaskList.get(j).getDescription().toLowerCase().contains(keyWords)){
				FloatingTask temp = floatTaskList.get(j);
				SearchListNode node = new SearchListNode(temp, j);
				searchList.add(node);
				StringList.append(found++ + ". " + floatTaskList.get(j).getDescription() + "\n");
			}
		}
		if(foundNow==found-1){
			StringList.append(NO_FLOATING_TASKS + "\n");
		}
		foundTasks = foundNow;
		//display = StringList.toString();
		if(found!=0){
			searchResult = "There are " + found + " tasks listed: " + "\n"; 
			searchResult += StringList.toString();
			return FOUND;
		} else {
			searchResult = NO_TASKS_FOUND + "\n";
			return NOT_FOUND;
		}
	}
	
	public int getFound(){
		return foundTasks;
	}
	
	public ArrayList<SearchListNode> getSearchArray(){
		return searchList;
	}
	
	@Override
	public String undo() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
