import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/** This class stores the task-list as an arrayList.
 *  The basic functions have been provided as a skeleton program.
 * @author jjlu
 *
 *
 * API:
 * FloatingTask addFloatingTask(FloatingTask)
 * FloatingTask deleteFloatingTask(FloatingTask)
 * FloatingTask deleteFloatingTask(int)
 * FloatingTask viewFloatingTask(int)
 * FloatingTask editFloatingTask(int, FloatingTask)
 * return the original task before editing.
 */


public class TaskList {
	
	private ArrayList<FloatingTask> taskList;
	
	public String newLine = System.getProperty("line.separator");

	public TaskList() {
		taskList = new ArrayList<FloatingTask>();
	}	
	
	public static void main(String[] args){
		TaskList tasks = new TaskList();
		tasks.addTask(new FloatingTask("floating task."));
		tasks.addTask(new FloatingTask("floating task."));
		tasks.addTask(new TimedTask("timed task.", new DateTime(), new DateTime()));
		tasks.addTask(new DeadlineTask("deadline task1.", new DateTime(12,10,3,2,3)));
		tasks.addTask(new TimedTask("timed task.", new DateTime(), new DateTime()));
		tasks.addTask(new DeadlineTask("deadline task3.", new DateTime(14,11,3,2,3)));
		tasks.addTask(new DeadlineTask("deadline task2.", new DateTime(13,6,6,6,6)));
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yy");
		System.out.println(tasks.write("mydata", dtf));
		TaskList testTasks = new TaskList();
		System.out.println(testTasks.read("mydata", dtf));
		System.out.println(testTasks.write("mydata2",dtf));
		tasks.sort();
		System.out.println("after sorting:" + tasks.viewAll(dtf));
	}
	
	// basic functions
	// add
	public FloatingTask addTask(FloatingTask newFloatingTask){
		taskList.add(newFloatingTask);
		return newFloatingTask;
	}
	public FloatingTask addTask(int index, FloatingTask newTask){
		System.out.println("index:" + index);
		taskList.add(index-1, newTask);
		return newTask;
	}
	
	// delete
	public FloatingTask deleteTask(FloatingTask task){
		taskList.remove(task);
		return task;
	}
	public FloatingTask deleteTask(int index){
		int indexComputing = index - 1;
		FloatingTask tempFloatingTask = taskList.get(indexComputing);
		taskList.remove(indexComputing);
		return tempFloatingTask;
	}
	
	// edit
	public FloatingTask editTask(int index, FloatingTask newFloatingTask){
		int indexComputing = index - 1;
		FloatingTask tempFloatingTask = taskList.get(indexComputing);
		taskList.add(indexComputing, newFloatingTask);
		taskList.remove(index);
		return tempFloatingTask;
	}
	
	// view the taskList in a particular format
	public String viewFloatingTask(){
		StringBuilder result = new StringBuilder("Floating tasks are:" + newLine);
		for (int i=0,j=1;i<taskList.size();i++){
			if (! (taskList.get(i) instanceof DeadlineTask)){
				result.append(j++ + ". " + taskList.get(i).toString() + newLine);
			}
		}
		return result.toString();
	}
	
	public String viewDeadlineTask(DateTimeFormatter dtf){
		StringBuilder result = new StringBuilder("Deadline tasks are:" + newLine);
		for (int i=0,j=1;i<taskList.size();i++){
			if ((taskList.get(i) instanceof DeadlineTask) && !(taskList.get(i) instanceof TimedTask)){
				DeadlineTask temp = (DeadlineTask) taskList.get(i);
				result.append(j++ + ". " + temp.toString(dtf) + newLine);
			}
		}
		return result.toString();
	}
	
	public String viewTimedTask(DateTimeFormatter dtf){
		StringBuilder result = new StringBuilder("Timed tasks are:" + newLine);
		for (int i=0,j=1;i<taskList.size();i++){
			if (taskList.get(i) instanceof TimedTask){
				TimedTask temp = (TimedTask) taskList.get(i);
				result.append(j++ + ". " + temp.toString(dtf) + newLine);
			}
		}
		return result.toString();
	}
	
	public String viewDone(){
		StringBuilder str = new StringBuilder("FloatingTasks Completed: \n");
		for (int i=0,j=1; i<taskList.size();i++){
			if (taskList.get(i).isCompleted()){
				str.append( j++ + ". " + taskList.get(i).getDescription() + "\n");
			}
		}
		return str.toString();
	}
	
	public String viewUndone(){
		StringBuilder str = new StringBuilder("FloatingTasks Due: \n");
		for (int i=0,j=1; i<taskList.size();i++){
			if (!taskList.get(i).isCompleted()){
				str.append( j++ + ". " + taskList.get(i).getDescription() + "\n");
			}
		}
		return str.toString();
	}
	
	public String viewAll(DateTimeFormatter dtf){
		StringBuilder result = new StringBuilder("There are " + taskList.size() + " tasks listed:" + newLine);
		result.append(viewFloatingTask() + viewDeadlineTask(dtf) + viewTimedTask(dtf));
		return result.toString();
	}
	
	//sort
	public String sort(){
		Collections.sort(taskList);
		return "tasklist is sorted.";
	}
	
	// write and read
	public String write(String fileName, DateTimeFormatter dtf){
			String fileContent = "" + viewAll(dtf);
			//System.out.print(fileContent);
			BufferedWriter writer = null;
			try
			{
			    writer = new BufferedWriter( new FileWriter(fileName));
			    writer.write(fileContent);

			}
			catch ( IOException e)
			{
			}
			finally
			{
			    try
			    {
			        if ( writer != null)
			        writer.close( );
			    }
			    catch ( IOException e)
			    {
			    	return "IOException";
			    }
			}
			return fileContent;
	}
	
	public String read(String fileName, DateTimeFormatter dtf){
		//String existingFileContent = "";
		taskList = new ArrayList<FloatingTask>();
		BufferedReader reader = null;
		try
		{
		    reader = new BufferedReader( new FileReader(fileName));
		    String line = reader.readLine();
		    while (line != null){
		    	//System.out.println(line);
		    	//existingFileContent = existingFileContent + line.substring(3) + System.lineSeparator();
				//System.out.print(existingFileContent);
		    	if (line.contains("tasks listed:")) {
		    		line = reader.readLine();
		    	} 
		    	if (line.contains("Floating tasks are:")){
		    		line = reader.readLine();
		    		while (!(line.contains("Deadline tasks are:"))){
		    			readFloatingTask(line);
		    			line = reader.readLine();
		    		}
		    	} else if(line.contains("Deadline tasks are:")){
		    		line = reader.readLine();
		    		//System.out.println("deadline task indentified");
		    		while (!(line.contains("Timed tasks are:"))){
		    			readDeadlineTask(dtf, line);
		    			line = reader.readLine();
		    		}
		    	} else if (line.contains("Timed tasks are:")){
		    		line = reader.readLine();
		    		while (line != null){
		    			readTimedTask(dtf, line);
		    			line = reader.readLine();
		    		}
		    	} else {
			    	line = reader.readLine();
		    	}
		    	
		    }

		}
		catch ( IOException e)
		{
			return fileName + "is not found.";
		}
		finally
		{
		    try
		    {
		        if ( reader != null)
		        reader.close( );
		    }
		    catch ( IOException e)
		    {
		    	return "IOException";
		    }
		}
		return fileName + " is loaded.";
	}

	private void readTimedTask(DateTimeFormatter dtf, String line) {
		int markerIndex = line.indexOf(".");
		int markerDone = line.indexOf("@isDone");
		int markerImpor = line.indexOf("@isImpor");
		int markerFrom = line.indexOf("@from");
		int markerTo = line.indexOf("@to");
		DateTime tempStartTime = dtf.parseDateTime(line.substring(markerFrom+6,markerTo-1));
		DateTime tempEndTime = dtf.parseDateTime(line.substring(markerTo+4));
		TimedTask tempTask = new TimedTask(line.substring(markerIndex+2,markerDone-1),tempStartTime,tempEndTime);
		if (line.substring(markerDone,markerImpor-1).contains("true")){
			tempTask.complete();
		}
		if (line.substring(markerImpor,markerFrom-1).contains("true")){
			tempTask.highlight();
		}
		taskList.add(tempTask);
	}

	private void readDeadlineTask(DateTimeFormatter dtf, String line) {
		int markerIndex = line.indexOf(".");
		int markerDone = line.indexOf("@isDone");
		int markerImpor = line.indexOf("@isImpor");
		int markerDue = line.indexOf("@due");
		DateTime time = dtf.parseDateTime(line.substring(markerDue+5));
		DeadlineTask newTask = new DeadlineTask(line.substring(markerIndex+2,markerDone-1),time);
		if (line.substring(markerDone,markerImpor-1).contains("true")){
			newTask.complete();
		}
		if (line.substring(markerImpor,markerDue-1).contains("true")){
			newTask.highlight();
		}
		//System.out.println(newTask.toString(dtf));
		taskList.add(newTask);
	}

	private void readFloatingTask(String line) {
		int markerIndex = line.indexOf(".");
		int markerDone = line.indexOf("@isDone");
		int markerImpor = line.indexOf("@isImpor");
		FloatingTask someTask = new FloatingTask(line.substring(markerIndex+2,markerDone-1));
		if (line.substring(markerDone,markerImpor-1).contains("true")){
			someTask.complete();
		}
		if (line.substring(markerImpor).contains("true")){
			someTask.highlight();
		}
		taskList.add(someTask);
		//System.out.println("floating task indentified");
	}

	public boolean complete(int index) {
		return taskList.get(index-1).complete();
	}

	public FloatingTask get(int index) {
		return taskList.get(index-1);
	}

}
