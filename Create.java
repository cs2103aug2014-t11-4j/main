import static org.junit.Assert.*;

import org.junit.Test;

public class Create extends CommandClass {

	FloatingTask toDo = new FloatingTask(parserVar.getCommand());{
		assert parserVar.getCommand() instanceof String;
	}
	Integer editIndex = parserVar.getEditIndex();{
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
		assertEquals(add(), toDo.toString() + "is added to taskList!");
	}
	
	public String add() {
		if(editIndex==null){
			psl.push(new Parser("delete" + taskListVar.getList().size()));
			taskListVar.addTask(toDo);
		} else {
			taskListVar.addTask(editIndex, toDo);
			psl.push(new Parser("delete" + editIndex));
		}
		return toDo.toString() + "is added to taskList!";
	}

}