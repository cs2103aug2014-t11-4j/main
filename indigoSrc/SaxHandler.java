package indigoSrc;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SaxHandler extends DefaultHandler {
	private DateTime tempStartTime;
	private DateTime tempEndTime;
	private String tempTaskName;
	private ArrayList<FloatingTask> tasks;
	private FloatingTask tempTask;
	private String tempValue;
    
    public ArrayList<FloatingTask> getList(){
    	return tasks;
    }
    
    public void startElement(String s, String s1, String tagName, Attributes attributes) 
    		throws SAXException {
    	if (tagName.equalsIgnoreCase("rootTaskList")){
    		tasks = new ArrayList<FloatingTask>();
    	}
    	
    	if (tagName.equalsIgnoreCase("Task")){
    		tempTask = new FloatingTask();
    	}
    	
    }

    public void endElement(String uri, String localName, String tagName)
    throws SAXException {
    	if (tagName.equalsIgnoreCase("Task")){
    		//System.out.println("A Task detected");
    		tasks.add(tempTask);
    	}
    	if (tagName.equalsIgnoreCase("TaskDescription")){
    		tempTaskName = tempValue;
    	}
    	
    	if (tagName.equalsIgnoreCase("DueDate") ||
    			tagName.equalsIgnoreCase("To")){
    		tempEndTime = new DateTime(tempValue);
    	}
    	
    	if (tagName.equalsIgnoreCase("From")){
    		tempStartTime = new DateTime(tempValue);
    	}
    	
    	if (tagName.equalsIgnoreCase("numDates")){
    		if (Integer.parseInt(tempValue)== 0){
    			System.out.println("numDates detected");
    			tempTask= new FloatingTask(tempTaskName);
    		} else if (Integer.parseInt(tempValue) == 1){
    			System.out.println("A deadlineTask detected");
    			tempTask = new DeadlineTask(tempTaskName, tempEndTime);
    		} else if (Integer.parseInt(tempValue) == 2){
    			System.out.println("A timedTask detected");
    			tempTask = new TimedTask(tempTaskName, tempStartTime, tempEndTime);
    		}
    	}
    	
    	if (tagName.equalsIgnoreCase("CompletionStatus") && tempValue.equalsIgnoreCase("true")){
    		tempTask.complete();
    	}
    	
    	if (tagName.equalsIgnoreCase("Importance") && tempValue.equalsIgnoreCase("true")){
    		tempTask.highlight();
    	}
    	
    }

    public void characters(char ch[], int start, int length)
    throws SAXException {
    	tempValue = new String(ch,start,length);
    }

}
