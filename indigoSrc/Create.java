package indigoSrc;

/* This class is the create class which adds the task that user
 * would want to add. This class has the function to add task either
 * in the floating tasklist or the deadline tasklist.
 * 
 * @author Ken
 */

public class Create extends CommandClass {

	FloatingTask toDo;{
		assert parserVar.getCommand() instanceof String;
	}
	Integer editIndex;{
		assert editIndex instanceof Integer;
	}
	
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return add();
	}	
	
	public Create(Parser parsing, UndoList pslist, TaskList taskList){
		parserVar = parsing;
		uList = pslist;
		taskListVar = taskList;
		editIndex = parserVar.getEditIndex();
		if (parserVar.isDeadlineTask()){
			toDo = new DeadlineTask(parserVar.getCommand(),parserVar.getEndTime());
		} else if (parserVar.isTimedTask()){
			toDo = new TimedTask(parserVar.getCommand(),parserVar.getStartTime(),parserVar.getEndTime());
		} else {
			toDo = new FloatingTask(parserVar.getCommand());
		}
	}
	
	public String add() {

		int totalSize = taskListVar.getFloatingList().size() + taskListVar.getTimedList().size() + 1;
		if (editIndex > totalSize || editIndex < 1){
			System.out.println("invalid index");
			return "Invalid index";
		} else {
		taskListVar.addTask(editIndex, toDo);
		uList.push(new Parser("delete " + editIndex), parserVar);
		return toDo.toString() + " is added to taskList!";
		}

	}

}