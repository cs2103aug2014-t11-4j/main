package indigoSrc;
import static org.junit.Assert.*;

import org.junit.Test;

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
	
	public Create(Parser parsing, ParserList pslist, TaskList taskList){
		parserVar = parsing;
		psl = pslist;
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
	
	@Test
	public void testCreate(){
		Parser testParse = new Parser("add code finish v0.2");
		add();
	}
	
	public String add() {
		/*int totalSize = taskListVar.getFloatingList().size() + taskListVar.getTimedList().size();
		if (editIndex > totalSize || editIndex < 1){
			return "Invalid index";
		} else {*/
		taskListVar.addTask(editIndex, toDo);
		psl.push(new Parser("delete " + editIndex));
		return toDo.toString() + " is added to taskList!";
		//}
	}

}