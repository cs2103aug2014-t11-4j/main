import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
		tasks.writeXMLDocument("NewFile");
		
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
	
	public String viewNormal(DateTimeFormatter dtf){
		StringBuilder result = new StringBuilder("A default view of tasks:");
		for (int i=0,j=1;i<taskList.size();i++){
			if (taskList.get(i).numDates == 2){
				result.append(j++ + ". " + taskList.get(i).toString());
			} else if (taskList.get(i).numDates == 1){
				result.append(j++ + ". " + taskList.get(i).toDeadlineTask().toString());
			} else {
				result.append(j++ + ". " + taskList.get(i).toTimedTask().toString());
			}
		}
		return result.toString();
	}
	
	//sort
	public String sort(){
		Collections.sort(taskList);
		return "tasklist is sorted.";
	}
	
	// write and read
	public void writeXMLDocument(String fileName){
		 DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
	     DocumentBuilder build;
		try {
			build = dFact.newDocumentBuilder();
		    Document doc = build.newDocument();

	        Element root = (Element) doc.createElement("rootTaskList");
	        doc.appendChild(root);
	        
	        
	        for (int i =0; i<taskList.size(); i++){
	        	Element taskTag = doc.createElement("Task");
	        	root.appendChild(taskTag);
	        	
	        	Element taskID = doc.createElement("TaskID");
	        	taskID.appendChild(doc.createTextNode(i + ""));
	        	taskTag.appendChild(taskID);
	        	
	        	Element numDates = doc.createElement("numDates:");
	        	numDates.appendChild(doc.createTextNode(taskList.get(i).numDates + ""));
	        	taskTag.appendChild(numDates);
	        	
	        	Element taskDetail = doc.createElement("TaskDescription");
	        	taskDetail.appendChild(doc.createTextNode(taskList.get(i).getDescription()));
	        	taskTag.appendChild(taskDetail);
	        	
	        	Element isCompleted = doc.createElement("CompletionStatus");
	        	isCompleted.appendChild(doc.createTextNode(taskList.get(i).isCompleted() + ""));
	        	taskTag.appendChild(isCompleted);
	        	
	        	Element isImportant = doc.createElement("Importance");
	        	isImportant.appendChild(doc.createTextNode(taskList.get(i).isImportant()+ ""));
	        	taskTag.appendChild(isImportant);
	        	
	        	switch (taskList.get(i).numDates){
	        		case 0:
	        			break;
	        		case 1:
	        			Element endTime = doc.createElement("DueDate");
	        			endTime.appendChild(doc.createTextNode(taskList.get(i).toDeadlineTask().endTime.toString()));
	        			taskTag.appendChild(endTime);
	        			break;
	        		case 2:
	        			Element startDate = doc.createElement("From");
	        			startDate.appendChild(doc.createTextNode(taskList.get(i).toDeadlineTask().endTime.toString()));
	        			taskTag.appendChild(startDate);
	        			
	        			Element endDate = doc.createElement("To");
	        			endDate.appendChild(doc.createTextNode(taskList.get(i).toDeadlineTask().endTime.toString()));
	        			taskTag.appendChild(endDate);
	        			break;
	        		default:
	        		assert false;
	        	}
	        }
	        
	        writeToDisk(fileName, doc);
	        
		} catch (ParserConfigurationException e) {
			System.out.println("Parser Exception found.");
		}
	        
	}
	
	private void writeToDisk(String fileName, Document doc) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName));
			
			transformer.transform(source, result);
			 
			System.out.println("File saved!");
		} catch (TransformerConfigurationException e) {
			System.out.println("TransformerConfig Exception Found.");
		} catch (TransformerException e) {
			System.out.println("Transformer Exception Found.");
		}
		
	}

	public String readFromXML(String fileName){
		//String existingFileContent = "";
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			
		    InputStream xmlInput  = new FileInputStream(fileName);
		    SAXParser saxParser = factory.newSAXParser();

		    SaxHandler handler   = new SaxHandler();
		    saxParser.parse(xmlInput, handler);
		    taskList = handler.getList();

		} catch (Throwable err) {
		    System.out.println("SAXparser Exception.");
		    writeXMLDocument(fileName);
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
		// assert the storage file is in the right format
		assert line.contains("@isDone");
		
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
	
	public ArrayList<FloatingTask> getList(){
		return taskList;
	}

}
