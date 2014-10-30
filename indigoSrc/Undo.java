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

	public Undo(Parser parsing, UndoList psList, TaskList taskList){
		parserVar = parsing;
		uList = psList;
		taskListVar = taskList;
	}
	
	private static String undo(){
		if (uList.isUndoAble() == false){
			return "Cannot Undo!";
		}
		Parser commandPre = uList.undo();
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
		if (uList.isRedoAble() == false){
			return "Cannot redo!";
		}
		Parser commandNext = uList.redo();
		Command commandInput = new Command(commandNext.getKeyCommand());
		System.out.println(commandNext.getKeyCommand());
		COMMAND_KEY comm = commandInput.getKey();
		switch (comm){
			case CREATE:
				FloatingTask toDo = new FloatingTask();
				if (commandNext.isDeadlineTask()){
					toDo = new DeadlineTask(commandNext.getCommand(),commandNext.getEndTime());
				} else if (commandNext.isTimedTask()){
					toDo = new TimedTask(commandNext.getCommand(),commandNext.getStartTime(),commandNext.getEndTime());
				} else {
					toDo = new FloatingTask(commandNext.getCommand());
				}
				taskListVar.addTask(commandNext.getEditIndex(), toDo);
				return "Task recreated";
			case UPDATE:
				taskListVar.editTask(commandNext.getEditIndex(), new FloatingTask(commandNext.getCommand()));
				return "Task reupdated";
			case DELETE:
				taskListVar.deleteTask(commandNext.getEditIndex());
				return "Task is deleted again";
			case COMPLETE:
				taskListVar.get(commandNext.getEditIndex()).complete();
				return "Task is complete!";
			default: 
				return "view";
		}
	}
}
