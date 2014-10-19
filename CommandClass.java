//This is the Command abstract class used in the Logic component implementing the 
//Command Pattern. The methods return String because they are used for testing.
//@Ken

public abstract class CommandClass {
	protected static Parser parseris; 
	protected static ParserList psl = new ParserList();
	protected static TaskList taskList = new TaskList();
	
	public abstract String execute();
	
}