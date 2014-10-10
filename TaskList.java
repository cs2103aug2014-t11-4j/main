import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
		tasks.addFloatingTask(new FloatingTask("floating task."));
		tasks.addFloatingTask(new TimedTask("timed task.", new DateTime(), new DateTime()));
		tasks.addFloatingTask(new DeadlineTask("deadline task.", new DateTime()));
		DateTimeFormatter dtf = DateTimeFormat.forPattern("MMMM, yyyy");
		System.out.println(tasks.write("mydata", dtf));
		TaskList testTasks = new TaskList();
		System.out.println(testTasks.read("mydata", dtf));
		System.out.println(testTasks.write("mydata2",dtf));
	}
	
	// basic functions
	// add
	public FloatingTask addFloatingTask(FloatingTask newFloatingTask){
		taskList.add(newFloatingTask);
		return newFloatingTask;
	}
	public FloatingTask addFloatingTask(int index, FloatingTask newTask){
		taskList.add(index, newTask);
		return newTask;
	}
	
	// delete
	public FloatingTask deleteFloatingTask(FloatingTask task){
		taskList.remove(task);
		return task;
	}
	public FloatingTask deleteFloatingTask(int index){
		int indexComputing = index - 1;
		FloatingTask tempFloatingTask = taskList.get(indexComputing);
		taskList.remove(indexComputing);
		return tempFloatingTask;
	}
	
	// edit
	public FloatingTask editFloatingTask(int index, FloatingTask newFloatingTask){
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
	
	public String viewAll(DateTimeFormatter dtf){
		StringBuilder result = new StringBuilder("There are " + taskList.size() + " of tasks listed:" + newLine);
		result.append(viewFloatingTask() + viewDeadlineTask(dtf) + viewTimedTask(dtf));
		return result.toString();
	}
	
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
			return (fileContent);
	}
	
	public String read(String fileName, DateTimeFormatter dtf){
		//String existingFileContent = "";
		BufferedReader reader = null;
		try
		{
		    reader = new BufferedReader( new FileReader(fileName));
		    String line = reader.readLine();
		    while (line != null){
		    	//System.out.print(line);
		    	//existingFileContent = existingFileContent + line.substring(3) + System.lineSeparator();
				//System.out.print(existingFileContent);
		    	if (line.contains("tasks listed:")) {
		    		line = reader.readLine();
		    	} 
		    	int markerDone;
		    	int markerImpor;
		    	if (line.contains("Floating tasks are:")){
		    		line = reader.readLine();
		    		markerDone = line.indexOf("@isDone");
		    		markerImpor = line.indexOf("@isImpor");
		    		FloatingTask someTask = new FloatingTask(line.substring(3,markerDone));
		    		if (line.substring(markerDone,markerImpor-1).contains("true")){
		    			someTask.complete();
		    		}
		    		if (line.substring(markerImpor).contains("true")){
		    			someTask.highlight();
		    		}
		    		taskList.add(someTask);
		    		line = reader.readLine();
		    		//System.out.println("floating task indentified");
		    	} else if(line.contains("Deadline tasks are:")){
		    		line = reader.readLine();
		    		//System.out.println("deadline task indentified");
		    		markerDone = line.indexOf("@isDone");
		    		markerImpor = line.indexOf("@isImpor");
		    		int markerDue = line.indexOf("@due");
		    		DateTime time = dtf.parseDateTime(line.substring(markerDue+5));
		    		DeadlineTask newTask = new DeadlineTask(line.substring(3,markerDone),time);
		    		if (line.substring(markerDone,markerImpor-1).contains("true")){
		    			newTask.complete();
		    		}
		    		if (line.substring(markerImpor,markerDue-1).contains("true")){
		    			newTask.highlight();
		    		}
		    		//System.out.println(newTask.toString(dtf));
		    		taskList.add(newTask);
		    	} else if (line.contains("Timed tasks are:")){
		    		line = reader.readLine();
		    		markerDone = line.indexOf("@isDone");
		    		markerImpor = line.indexOf("@isImpor");
		    		int markerFrom = line.indexOf("@from");
		    		int markerTo = line.indexOf("@to");
		    		DateTime tempStartTime = dtf.parseDateTime(line.substring(markerFrom+6,markerTo-1));
		    		DateTime tempEndTime = dtf.parseDateTime(line.substring(markerTo+4));
		    		TimedTask tempTask = new TimedTask(line.substring(3,markerDone),tempStartTime,tempEndTime);
		    		if (line.substring(markerDone,markerImpor-1).contains("true")){
		    			tempTask.complete();
		    		}
		    		if (line.substring(markerImpor,markerFrom-1).contains("true")){
		    			tempTask.highlight();
		    		}
		    		taskList.add(tempTask);
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

}
