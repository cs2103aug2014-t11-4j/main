package indigoSrc;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class TimedTask extends DeadlineTask {
	public static void main(String[] args){
		Task time = new TimedTask("timed task", new DateTime(2014,10,9,19,15,00), new DateTime());
		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
		System.out.println(time.toString(dtf));
	}
	
	public TimedTask(String description, DateTime beginTime, DateTime endTime){
		super(description,endTime);
		this.endTime = endTime;
		startTime = beginTime;
		numDates = 2;
		keyTime = beginTime;
	}
	
	@Override
	public String toString(DateTimeFormatter dtf){
		StringBuilder result = new StringBuilder("[" + dtf.print(startTime) + " - " + dtf.print(endTime) + "]: " + newLine);
		result.append(toString());
		return result.toString();
	}
	
	@Override
	public boolean equals(Object anotherTask) {
		if (anotherTask instanceof TimedTask){
			TimedTask myTask = (TimedTask) anotherTask;
			return super.equals(anotherTask) && this.startTime.equals(myTask.startTime);
		} else{
			return false;
		}
	}

}
