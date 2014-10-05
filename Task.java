/**
 * this class is meant for storage of one task only 3 way to create a task
 * -task(Parser) -task() -task(String, Type, String, String, String) There are 5
 * fields for creating a new task: the title, the description, the time, the
 * type of the task and the level of importance.
 * 
 * @author jjlu
 *
 */

public class Task {

	private String taskTitle; // without time

	private Type taskType;

	private String taskDescription; // with time

	// TODO data type to be amended
	private int[] taskTime;
	// exact time period, date, week or month in the format of DD/MM/YY

	private String taskImportance;

	// important, immediate, normal or pleasure

	public enum Type {
		FLOATING, TIMED, DEADLINE
	}

	public Task() {
		// a stub default value for default constructor
		this("CS2103", Type.FLOATING, "finish CS2103 assignments", null,
				"immediate");
	}

	// TODO exact class method to be ammended
<<<<<<< HEAD
<<<<<<< HEAD
	/*public Task(Parser userCommand){
		this (userCommand.getCommand(), Type.FLOATING, "Enter your task description", userCommand.getTime(), "immediate");
	}*/
=======
	public Task(Parser userCommand){
		this (userCommand.toDo, Type.FLOATING, "Enter your task description", userCommand.dateStr, "immediate");
	}
>>>>>>> 11750a10ff622c25b72a0beaa9c638a9aa943301
	
	public Task(String title, Type type, String description, String time, String importance){
=======
	public Task(Parser userCommand) {
		this(userCommand.getCommand(), Type.FLOATING,
				"Enter your task description", userCommand.getDate(),
				"immediate");
	}

	public Task(String title, Type type, String description, int[] time,
			String importance) {
>>>>>>> 69d1925e9b922a1192a971a9b0f91925d5f3bbae
		taskTitle = title;
		taskType = type;
		taskDescription = description;
		taskTime = time;
		taskImportance = importance;
	}

	// floating task
	public Task(String taskDescription) {
		this("title", Type.FLOATING, taskDescription, null, "immediate");
	}
	
	// deadline task
	public Task(String taskDescription, int[] taskTime){
		this("titie", Type.DEADLINE, taskDescription, taskTime, "immediate");
	}
	
	// timed task

	// accessor
	public String getTitle() {
		return taskTitle;
	}

	public Type getType() {
		return taskType;
	}

	public String getDescription() {
		return taskDescription;
	}

	public int[] getTime() {
		return taskTime;
	}

	public String getImportance() {
		return taskImportance;
	}

	// mutator
	public String editTitle(String newTitle) {
		taskTitle = newTitle;
		return newTitle;
	}

	public String editDescription(String newDescription) {
		taskDescription = newDescription;
		return newDescription;
	}

	public int[] editTime(int[] newTime) {
		taskTime = newTime;
		return newTime;
	}

	public String editImportance(String newImportance) {
		taskImportance = newImportance;
		return newImportance;
	}

	// TODO support floating task only
	public String toString() {
		return this.getDescription();
	}

	@Override
	public boolean equals(Object anotherTask) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
