public abstract class ExecutionClass {
	protected static Parser parser;
	protected static ParserList psl = new ParserList();
	protected static TaskList taskList = new TaskList();
	
	public abstract String execute();
	
}
