import java.util.ArrayList;

import org.joda.time.DateTime;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SaxHandler extends DefaultHandler {
	private DateTime tempStartTime;
	private DateTime tempEndTime;
	private String tempTaskName;
	private boolean tempCompletion;
	private boolean tempImportance;
	private ArrayList<FloatingTask> tasks;
	private FloatingTask tempTask;

    public void startElement(String uri, String localName,
            String tagName, Attributes attributes)
    throws SAXException {
    	if (tagName.equalsIgnoreCase("Task")){
    		tempTaskName = attributes.getValue("TaskDescription");
    		if (attributes.getValue("numDates").equals(0)){
    			tempTask= new FloatingTask(tempTaskName);
    		} else if (attributes.getValue("numDates").equals(1)){
    			tempEndTime = new DateTime(attributes.getValue("DueDate"));
    			tempTask = new DeadlineTask(tempTaskName, tempEndTime);
    		} else if (attributes.getValue("numDates").equals(2)){
    			tempEndTime = new DateTime(attributes.getValue("To"));
    			tempStartTime = new DateTime(attributes.getValue("From"));
    			tempTask = new TimedTask(tempTaskName, tempStartTime, tempEndTime);
    		} else {
    			assert false;
    		}
    		assert tempTask != null;
    		if (attributes.getValue("CompletionStatus").equals(true)){
    			tempTask.complete();
    		}
    		if (attributes.getValue("Importance").equals(true)){
    			tempTask.highlight();
    		}
    		tasks.add(tempTask);
    	}

    }
    
    public ArrayList<FloatingTask> getList(){
    	return tasks;
    }

    public void endElement(String uri, String localName, String qName)
    throws SAXException {
    }

    public void characters(char ch[], int start, int length)
    throws SAXException {
    }

    public void ignorableWhitespace(char ch[], int start, int length)
    throws SAXException {
    }
}
