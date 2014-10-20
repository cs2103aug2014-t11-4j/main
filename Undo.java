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
		parseris = parsing;
		psl = parseL;
		this.taskList = taskList;
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
				taskList.addTask(commandPre.getEditIndex(), new FloatingTask(commandPre.getCommand()));
				return "Undo a create";
			case READ:
				return "Cannot undo a read";
			case UPDATE:
				taskList.editTask(commandPre.getEditIndex(), new FloatingTask(commandPre.getCommand()));
				return "Undo an Update";
			case DELETE:
				taskList.deleteTask(commandPre.getEditIndex());
				return "Undo a delete";
			case UNDO:
				return "Undo an undo?";
			case UNCOMPLETE:
				taskList.get(commandPre.getEditIndex()).unComplete();
				return "undo an uncomplete";
			default: 
				return "WalauEh";
		}
	}
	
}
