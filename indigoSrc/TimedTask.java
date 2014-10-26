package indigoSrc;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class TimedTask extends DeadlineTask {
	
	protected DateTime startTime;
	
	protected Interval interval;
	
	protected String newLine = System.getProperty("line.separator");
	
	public static void main(String[] args){
		FloatingTask time = new TimedTask("timed task", new DateTime(2014,10,9,19,15,00), new DateTime());
		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
		TimedTask newTask = (TimedTask)time;
		System.out.println(newTask.toString(dtf));
	}
	
	public TimedTask(String description, DateTime beginTime, DateTime endTime){
		super(description, endTime);
		startTime = beginTime;
		interval = new Interval(beginTime, endTime);
		numDates = 2;
		keyTime = beginTime;
	}
	
	public DateTime getStartTime(){
		return startTime;
	}
	
	public String getInterval(){
		return interval.toString();
	}
	
	public String toString(DateTimeFormatter dtf){
		FloatingTask temp = new FloatingTask(this);
		StringBuilder result = new StringBuilder("[" + dtf.print(startTime) + " - " + dtf.print(endTime) + "]: "+newLine);
		result.append(temp.toString());
		return result.toString();
	}

}