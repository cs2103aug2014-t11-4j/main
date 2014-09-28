import java.util.Scanner;
import java.util.LinkedList;

public class Indigo{
	
    public final static String WELCOME_MESSAGE = new String("Welcome to Indigo,"
    		+ " where you get tasks from to-do to done!");
	private static final int STRING_SPLIT_LIMIT = 2;
	private static final int MAIN_POSITION = 0;
	private static final int DETAILS_POSITION = 1;
	private static final String ERROR_MESSAGE = "Error!";     
    public static Scanner input = new Scanner(System.in);
    
    enum Commands {
        ADD, EDIT, DELETE, VIEW, CLEAR;
    }
    
    public static void main(String[] args){
		LinkedList<String> taskList= new LinkedList<String>();
		
		executeUserCommand(taskList);
	}
    
	//This method is the interaction with user. It reads and execute the commands 
	//the user wants.
	public static void executeUserCommand(LinkedList<String> taskList) {
		String commandLine = new String();
		//This boolean variable will become false if the user wants to exit.
		boolean continueProgramme = true;
		while(continueProgramme){
			commandLine = input.nextLine().trim();
			continueProgramme = readCommand(commandLine, taskList);
		}
	}
	
	public static boolean readCommand(String commandLine, LinkedList<String> taskList){
		String instruction = new String(getFirstParameter(commandLine));
		String detail = new String(getSecondParameter(commandLine));
		boolean continueProgramme = true;
		continueProgramme = executeCommand(taskList, instruction, detail);
		return continueProgramme;
	}
	
	public static String getFirstParameter(String commandLine){
		String parameter = new String();
		if(!(commandLine.contains(" "))){
			parameter = commandLine;
		}
		else{
			String[] splitLine = commandLine.split(" ", STRING_SPLIT_LIMIT);
			parameter = splitLine[MAIN_POSITION];
		}
		return parameter;
	}

	public static String getSecondParameter(String commandLine){
		if(!commandLine.contains(" ")){
			return "";
		} else {
			String nextParameter = new String();
			String[] splitLine = commandLine.split(" ", STRING_SPLIT_LIMIT);
			nextParameter = splitLine[DETAILS_POSITION].trim();
			return nextParameter;
		}
	}

	public static boolean executeCommand(LinkedList<String> taskList,
										 String instruction, String detail) {
		switch (instruction.toLowerCase()){
			case "add":
				addTask(detail, taskList);
				break;
			case "delete":
				deleteTask(detail, taskList);
				break;
			case "display":
				displayTaskList(taskList, detail);
				break;
			case "clear":
				clearTaskList(taskList);
				break;
			case "search":
				searchTaskList(detail, taskList);
				break;
			case "edit":
				editTask(detail, taskList);
				break;
			case "exit":
				return false;
			default:
				System.out.println(ERROR_MESSAGE);
				break;
		}
		return true;
	}
	public static void addTask(String details, LinkedList<String> taskList){
		taskList.add(details);
	}

	public static void deleteTask(String detail, LinkedList<String> taskList){
		try{
			int number = Integer.parseInt(detail);
			if(number <= taskList.size()){
				taskList.remove(number-1);
			} else {
				printMessage(ERROR_MESSAGE);
			}
		} catch(NumberFormatException exception) {
			printMessage(ERROR_MESSAGE);
		}
	}

	private static void printMessage(String errorMessage) {
		// TODO Auto-generated method stub
		System.out.println(errorMessage);
	}

	//This method works either by displaying all the tasks or just a specific task.
	public static void displayTaskList(LinkedList<String> taskList, String detail){
		int noOfTasks = taskList.size();
		if(noOfTasks == 0){
			printMessage("No Tasks Added!");
		}
		try{
			int number = Integer.parseInt(detail);
			if(number <= noOfTasks){
				printMessage(number + ". " + taskList.get(number-1));
			} else {
				printMessage(ERROR_MESSAGE);
			}
		} catch(NumberFormatException exception) {
			if(noOfTasks > 0){
				for(int i = 0; i < noOfTasks; i++){
					printMessage(i+1 + ". " + taskList.get(i));
				}
			}
		}
	}

	public static void clearTaskList(LinkedList<String> taskList){
		taskList.clear();
	}

	public static void editTask(String detail, LinkedList<String> taskList) {
		int taskNo;
		String toBeEdited = new String();
		try{
			taskNo = Integer.parseInt(getFirstParameter(detail)) - 1; 
			toBeEdited = getSecondParameter(detail);
			taskList.set(taskNo, toBeEdited);
		} catch (NumberFormatException exception) {
			printMessage("Please type \" edit 'taskNumber' 'changes to task' \" ");
			printMessage("  eg. edit 3 Finish CE1 today (not tomorrow).");
		}
	}
	
	public static boolean searchTaskList(String detail, LinkedList<String> taskList){
		if(taskList == null || taskList.isEmpty()
			|| detail.equals("") || detail.equals("\\s+")){
			printMessage(ERROR_MESSAGE);
			return false;
		}
		int noOfTasks = taskList.size();
		String task = new String();
		boolean foundTask = false;
		for(int i = 0; i < noOfTasks; i++){
			task = taskList.get(i).toLowerCase();
			if(task.contains(detail.toLowerCase())){
				String number = new String(String.valueOf(i+1));
				displayTaskList(taskList, number);
				foundTask = true;
			}
		}
		if(foundTask == false){
			printMessage("No such task found.");
			return false;
		}
		
		return true;
	}

}