package indigoSrc;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class Undo extends CommandClass {
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yy");
	private static final String FILE_NAME = "myTask";

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return undo();
	}

	public Undo(Parser parsing, ParserList parseL, TaskList taskList){
		parserVar = parsing;
		psl = parseL;
		this.taskListVar = taskList;
	}
	
	private static String undo(){
		if (psl.isUndoAble() == false){
			return "Cannot Undo! Adoi.";
		}
		Parser commandPre = psl.undo();
		Command commandInput = new Command(commandPre.getKeyCommand());
		COMMAND_KEY comm = commandInput.getKey();
		switch (comm){
			case CREATE:
				taskListVar.addTask(commandPre.getEditIndex(), new FloatingTask(commandPre.getCommand()));
				return "Undo a create";
			case READ:
				return "Cannot undo a read";
			case UPDATE:
				taskListVar.editTask(commandPre.getEditIndex(), new FloatingTask(commandPre.getCommand()));
				return "Undo an Update";
			case DELETE:
				taskListVar.deleteTask(commandPre.getEditIndex());
				return "Undo a delete";
			case UNDO:
				return "Undo an undo?";
			case UNCOMPLETE:
				taskListVar.get(commandPre.getEditIndex()).unComplete();
				return "undo an uncomplete";
			default: 
				return "view";
		}
	}
	
}
