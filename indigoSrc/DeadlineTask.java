package indigoSrc;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class DeadlineTask extends FloatingTask{
	
	public static void main(String[] args){
		DeadlineTask time = new DeadlineTask("deadline task", new DateTime(2014,10,9,19,15,00));

		DateTimeFormatter dtf = DateTimeFormat.forPattern("YY-MM-dd HH:mm");

		Task time2 = time;
		
		System.out.println(time.toString(dtf));
		System.out.println(time2.toString(dtf));
	}

	public DeadlineTask(String description, DateTime time) {
		super(description);
		endTime = time;
		numDates = 1;
		keyTime = endTime;
	}
	

	
	@Override
	public String toString(DateTimeFormatter format){

		StringBuilder result = new StringBuilder(format.print(endTime)+", ");
		result.append(super.toString());
		return result.toString();
	}

	@Override
	public int compareTo(Task aTask) {
		// TODO Auto-generated method stub
		if (aTask.getNumDates()==0){
			return -1;
		} else {
			if (this.getKeyTime().isBefore(aTask.getKeyTime())){
				return -1;
			} else {
				return 1;
			}
		}
	}

	@Override
	public boolean equals(Object anotherTask) {
		if (anotherTask instanceof DeadlineTask){
			Task myTask = (DeadlineTask) anotherTask;
			return super.equals(anotherTask) && this.endTime.equals(myTask.endTime);
		} else {
			return false;
		}
	}	
}
