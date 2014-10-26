package indigoSrc;


public class Undo extends CommandClass {
	//private ParserList redoBucket = new ParserList();

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return undo();
	}
	
	public String rexecute(){
		return redo();
	}

	public Undo(Parser parsing, ParserList parseL, TaskList taskList){
		parserVar = parsing;
		psl = parseL;
		taskListVar = taskList;
	}
	
	private static String undo(){
		if (psl.isUndoAble() == false){
			return "Cannot Undo!";
		}
		Parser commandPre = psl.undo();
		Command commandInput = new Command(commandPre.getKeyCommand());
		System.out.println(commandPre.getKeyCommand());
		COMMAND_KEY comm = commandInput.getKey();
		switch (comm){
			case CREATE:
				taskListVar.addTask(commandPre.getEditIndex(), new FloatingTask(commandPre.getCommand()));
				return "Undo a delete";
			case UPDATE:
				taskListVar.editTask(commandPre.getEditIndex(), new FloatingTask(commandPre.getCommand()));
				return "Undo an Update";
			case DELETE:
				taskListVar.deleteTask(commandPre.getEditIndex());
				return "Undo a create";
			case UNCOMPLETE:
				taskListVar.get(commandPre.getEditIndex()).unComplete();
				return "undo an complete";
			default: 
				return "view";
		}
	}
	
	private static String redo(){
		Parser commandNext = psl.redo();
		Command commandInput = new Command(commandNext.getKeyCommand());
		System.out.println(commandNext.getKeyCommand());
		COMMAND_KEY comm = commandInput.getKey();
		switch (comm){
			case CREATE:
				taskListVar.addTask(commandNext.getEditIndex(), new FloatingTask(commandNext.getCommand()));
				return "Task recreated";
			case UPDATE:
				taskListVar.editTask(commandNext.getEditIndex(), new FloatingTask(commandNext.getCommand()));
				return "Task reupdated";
			case DELETE:
				taskListVar.deleteTask(commandNext.getEditIndex());
				return "Task is deleted again";
			case UNCOMPLETE:
				taskListVar.get(commandNext.getEditIndex()).unComplete();
				return "Task is complete!";
			default: 
				return "view";
		}
	}
}
