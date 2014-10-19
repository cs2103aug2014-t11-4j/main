import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class TimedTask extends DeadlineTask {
	
	protected DateTime startTime;
	
	protected Interval interval;
	
	public static void main(String[] args){
		FloatingTask time = new TimedTask("timed task", new DateTime(2014,10,9,19,15,00), new DateTime());
		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
		TimedTask newTask = (TimedTask)time;
		System.out.println(newTask.toString(dtf));
	}
	
	public TimedTask(String description, DateTime startTime, DateTime endTime){
		super(description, endTime);
		interval = new Interval(startTime, endTime);
		typeIndex = 0;
	}
	
	public DateTime getStartTime(){
		return startTime;
	}
	
	public String getInterval(){
		return interval.toString();
	}
	
	public String toString(DateTimeFormatter dtf){
		FloatingTask temp = this;
		StringBuilder result = new StringBuilder("[" + dtf.print(startTime) + " - " + dtf.print(endTime) + "]: \n");
		result.append(temp.toString());
		return result.toString();
	}

}
