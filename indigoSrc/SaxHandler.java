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
	private ArrayList<Task> timedList;
	private ArrayList<Task> floatingList;
	private Task tempTask;
	private String tempValue;
    
    public ArrayList<Task> getFloatingList(){
    	return floatingList;
    }
    
    public ArrayList<Task> getTimedList(){
    	return timedList;
    }
    
    public void startElement(String s, String s1, String tagName, Attributes attributes) 
    		throws SAXException {
    	if (tagName.equalsIgnoreCase("rootTaskList")){
    		floatingList = new ArrayList<Task>();
    		timedList = new ArrayList<Task>();
    	}
    	
    }

    public void endElement(String uri, String localName, String tagName)
    throws SAXException {
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
    			tempTask= new FloatingTask(tempTaskName);
    			floatingList.add(tempTask);
    		} else if (Integer.parseInt(tempValue) == 1){
    			tempTask = new DeadlineTask(tempTaskName, tempEndTime);
    			timedList.add(tempTask);
    		} else if (Integer.parseInt(tempValue) == 2){
    			tempTask = new TimedTask(tempTaskName, tempStartTime, tempEndTime);
    			timedList.add(tempTask);
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
