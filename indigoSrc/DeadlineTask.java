package indigoSrc;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class DeadlineTask extends FloatingTask{
	protected DateTime endTime;
	protected DateTime currentTime = DateTime.now();
	protected DateTime keyTime;
	
	public static void main(String[] args){
		DeadlineTask time = new DeadlineTask("deadline task", new DateTime(2014,10,9,19,15,00));
		DateTimeFormatter dtf = DateTimeFormat.forPattern("YY-MM-dd HH:mm");
		System.out.println(time.toString(dtf));
	}

	public DeadlineTask(String description, DateTime time) {
		super(description);
		endTime = time;
		numDates = 1;
		keyTime = endTime;
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
		FloatingTask temp = new FloatingTask(this);
		StringBuilder result = new StringBuilder(format.print(endTime)+", ");
		result.append(temp.toString());
		return result.toString();
	}
	public String toStringWODate(){
		FloatingTask temp = new FloatingTask(this);
		StringBuilder result = new StringBuilder();
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
	
	@Override
	public DateTime getKeyTime(){
		return keyTime;
	}

	@Override
	public boolean equals(Object anotherTask) {
		// TODO Auto-generated method stub
		if (anotherTask instanceof DeadlineTask){
			DeadlineTask myTask = (DeadlineTask) anotherTask;
			return super.equals(anotherTask) && this.endTime.equals(myTask.endTime);
		} else{
			return false;
		}
	}

}
