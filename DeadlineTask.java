import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class DeadlineTask extends FloatingTask{
	protected DateTime endTime;
	protected static DateTime currentTime;
	
	public static void main(String[] args){
		DeadlineTask time = new DeadlineTask("deadline task", new DateTime(2014,10,9,19,15,00));
		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
		System.out.println(time.toString(dtf));
	}

	public DeadlineTask(String description, DateTime time) {
		super(description);
		endTime = time;
		typeIndex = 1;
	}

	public DeadlineTask(String description) {
		super(description);
	}
	
	public DateTime editTime(DateTime newTime){
		endTime = newTime;
		return endTime;
	}
	
	public DateTime getTime(){
		return endTime;
	}
	
	public boolean isOverdue(){
		return endTime.isBefore(currentTime);
	}
	
	public String toString(DateTimeFormatter format){
		FloatingTask temp = this;
		StringBuilder result = new StringBuilder("[" + format.print(endTime) + "]");
		result.append(temp.toString());
		return result.toString();
	}
	
	private Duration evaluateDuration(){
		Duration duration = new Duration(currentTime, endTime);
		return duration;
	}
	
	public int evaluateMinutes(){
		return evaluateDuration().toStandardMinutes().getMinutes();
	}
	
	public int evaluateDays(){
		return evaluateDuration().toStandardDays().getDays();
	}
	
	public int evaluateHours(){
		return evaluateDuration().toStandardHours().getHours();
	}

}
