package indigoSrc;
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
 */


public class TaskList {
	
	private ArrayList<FloatingTask> floatingTaskList;
	private ArrayList<FloatingTask> timedTaskList;
	
	public String newLine = System.getProperty("line.separator");

	public TaskList() {
		floatingTaskList = new ArrayList<FloatingTask>();
		timedTaskList = new ArrayList<FloatingTask>();
	}	
	
	// basic functions
	// add
	public FloatingTask addTask(FloatingTask newTask){
		return addTask(1,newTask);
	}
	
	public FloatingTask addTask(int index, FloatingTask newTask){
		if (newTask.numDates==0){
			if ( index > timedTaskList.size()){
				floatingTaskList.add(index-timedTaskList.size()-1,newTask);
			}	else {
				floatingTaskList.add(0,newTask);
			}
		} else {
			// a timedtask should place at a position according to key time
			for (int i=0; i<timedTaskList.size();){
				DateTime timeToBeAdded = newTask.toDeadlineTask().getKeyTime();
				DateTime timeInList = timedTaskList.get(i).toDeadlineTask().getKeyTime();
				if (timeToBeAdded.isBefore(timeInList)){
					timedTaskList.add(i,newTask);
					return newTask;
				} else {
					i++;
				}
			}
			timedTaskList.add(timedTaskList.size(),newTask);
			return newTask;
		}
		return newTask;
	}
	
	// delete
	public FloatingTask deleteTask(int index){
		FloatingTask tempFloatingTask;
		if (index>timedTaskList.size()){
			tempFloatingTask = floatingTaskList.remove(index-timedTaskList.size()-1);
		} else {
			tempFloatingTask = timedTaskList.remove(index-1);
		}
		return tempFloatingTask;
	}
	
	// edit
	public FloatingTask editTask(int index,  FloatingTask newTask){
		FloatingTask tempFloatingTask = deleteTask(index);
		addTask(index,newTask);
		return tempFloatingTask;
	}
	
	// view the taskList in a particular format
	public String viewFloatingTask(){
		StringBuilder result = new StringBuilder("Floating tasks are:" + newLine);
		for (int i=0,j=1;i<floatingTaskList.size();i++){
			assert floatingTaskList.get(i).toDeadlineTask()==null;
			result.append("[No." + j++ + "]" + floatingTaskList.get(i).toString() + newLine);
		}
		return result.toString();
	}
	
	public String viewDeadlineTask(DateTimeFormatter dtf){
		StringBuilder result = new StringBuilder("Deadline tasks are:" + newLine);
		for (int i=0,j=1;i<timedTaskList.size();i++){
			DeadlineTask temp = (DeadlineTask) timedTaskList.get(i);
			result.append("[No." + j++ + "]" + temp.toString(dtf) + newLine);
		}
		return result.toString();
	}
	
	public String viewDone(){
		StringBuilder str = new StringBuilder("Tasks Completed: " + newLine);
		int j=1; // for indexing
		for (int i=0; i<timedTaskList.size();i++){
			if (timedTaskList.get(i).isCompleted()){
				str.append( j++ + ". " + timedTaskList.get(i).toString() + newLine);
			}
		}
		for (int i=0; i<floatingTaskList.size();i++){
			if (floatingTaskList.get(i).isCompleted()){
				str.append( j++ + ". " + floatingTaskList.get(i).toString() + newLine);
			}
		}
		return str.toString();
	}
	
	public String viewUndone(){
		StringBuilder str = new StringBuilder("FloatingTasks Due: " + newLine);
		int j = 1;
		for (int i=0; i<timedTaskList.size();i++){
			if (!timedTaskList.get(i).isCompleted()){
				str.append( j++ + ". " + timedTaskList.get(i).toString() + newLine);
			}
		}
		for (int i=0; i<floatingTaskList.size();i++){
			if (!floatingTaskList.get(i).isCompleted()){
				str.append( j++ + ". " + floatingTaskList.get(i).toString() + newLine);
			}
		}
		return str.toString();
	}
	
	public String viewAll(DateTimeFormatter dtf){
		int sum = floatingTaskList.size() + timedTaskList.size();
		StringBuilder result = new StringBuilder("There are " + sum + " tasks listed:" + newLine);
		result.append(viewDeadlineTask(dtf) + viewFloatingTask());
		return result.toString();
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
	        
	        
	        for (int i =0; i<timedTaskList.size(); i++){
	        	Element timedTask = doc.createElement("TimedTasks");
	        	root.appendChild(timedTask);
	        	
	        	Element taskTag = doc.createElement("Task");
	         	timedTask.appendChild(taskTag);
	        	
	        	Element taskDetail = doc.createElement("TaskDescription");
	        	taskDetail.appendChild(doc.createTextNode(timedTaskList.get(i).getDescription()));
	        	taskTag.appendChild(taskDetail);
	        	
	        	switch (timedTaskList.get(i).numDates){
	        		case 0:
	        			assert false;
	        			break;
	        		case 1:
	        			Element endTime = doc.createElement("DueDate");
	        			endTime.appendChild(doc.createTextNode(timedTaskList.get(i).toDeadlineTask().endTime.toString()));
	        			taskTag.appendChild(endTime);
	        			break;
	        		case 2:
	        			Element startDate = doc.createElement("From");
	        			startDate.appendChild(doc.createTextNode(timedTaskList.get(i).toTimedTask().startTime.toString()));
	        			taskTag.appendChild(startDate);
	        			
	        			Element endDate = doc.createElement("To");
	        			endDate.appendChild(doc.createTextNode(timedTaskList.get(i).toDeadlineTask().endTime.toString()));
	        			taskTag.appendChild(endDate);
	        			break;
	        		default:
	        		assert false;
	        	}
	        	
	        	Element numDates = doc.createElement("numDates");
	        	numDates.appendChild(doc.createTextNode(timedTaskList.get(i).numDates + ""));
	        	taskTag.appendChild(numDates);
	        	
	        	Element isCompleted = doc.createElement("CompletionStatus");
	        	isCompleted.appendChild(doc.createTextNode(timedTaskList.get(i).isCompleted() + ""));
	        	taskTag.appendChild(isCompleted);
	        	
	        	Element isImportant = doc.createElement("Importance");
	        	isImportant.appendChild(doc.createTextNode(timedTaskList.get(i).isImportant()+ ""));
	        	taskTag.appendChild(isImportant);
	        }
	        
	        for (int i=0;i<floatingTaskList.size();i++){

	        	Element floatingTask = doc.createElement("FloatingTasks");
	        	root.appendChild(floatingTask);
	        	
	        	Element taskTag = doc.createElement("Task");
	         	floatingTask.appendChild(taskTag);
	        	
	        	Element taskDetail = doc.createElement("TaskDescription");
	        	taskDetail.appendChild(doc.createTextNode(floatingTaskList.get(i).getDescription()));
	        	taskTag.appendChild(taskDetail);
	        	
	        	Element numDates = doc.createElement("numDates");
	        	numDates.appendChild(doc.createTextNode(floatingTaskList.get(i).numDates + ""));
	        	taskTag.appendChild(numDates);
	        	
	        	Element isCompleted = doc.createElement("CompletionStatus");
	        	isCompleted.appendChild(doc.createTextNode(floatingTaskList.get(i).isCompleted() + ""));
	        	taskTag.appendChild(isCompleted);
	        	
	        	Element isImportant = doc.createElement("Importance");
	        	isImportant.appendChild(doc.createTextNode(floatingTaskList.get(i).isImportant()+ ""));
	        	taskTag.appendChild(isImportant);
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
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName+".xml"));
			
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
			
		    InputStream xmlInput  = new FileInputStream(fileName+".xml");
		    SAXParser saxParser = factory.newSAXParser();

		    SaxHandler handler   = new SaxHandler();
		    saxParser.parse(xmlInput, handler);
		    floatingTaskList = handler.getFloatingList();
		    timedTaskList = handler.getTimedList();

		} catch (Throwable err) {
		    System.out.println("SAXparser Exception.");
		    return fileName + " is corrupted.";
		    //writeXMLDocument(fileName);
		}
		return fileName + " is loaded.";
	}
	
	// index start from 1
	public boolean complete(int index) {
		if (index>timedTaskList.size()){
			return floatingTaskList.get(index-1-timedTaskList.size()).complete();
		} else {
			return timedTaskList.get(index-1).complete();
		}
	}

	// index start from 1
	public FloatingTask get(int index) {
		if (index>timedTaskList.size()){
			return floatingTaskList.get(index-1-timedTaskList.size());
		} else {
			return timedTaskList.get(index-1);
		}
	}
	
	public ArrayList<FloatingTask> getTimedList(){
		return timedTaskList;
	}
	
	public ArrayList<FloatingTask> getFloatingList(){
		return floatingTaskList;
	}

}