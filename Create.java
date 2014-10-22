import static org.junit.Assert.*;

import org.junit.Test;

public class Create extends CommandClass {

	FloatingTask to_Do = new FloatingTask(parserVar.getCommand());{
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
		this.taskListVar = taskList;
		if (parserVar.isDeadlineTask()){
			to_Do = new DeadlineTask(parserVar.getCommand(),parserVar.getEndTime());
		} else if (parserVar.isTimedTask()){
			to_Do = new TimedTask(parserVar.getCommand(),parserVar.getStartTime(),parserVar.getEndTime());
		} else {
			to_Do = new FloatingTask(parserVar.getCommand());
		}
	}
	
	@Test
	public void testCreate(){
		Parser testParse = new Parser("add code finish v0.2");
		assertEquals(add(), to_Do.toString() + "is added to taskList!");
	}
	
	public String add() {
		if(editIndex==null){
			psl.push(new Parser("delete" + taskListVar.getList().size()));
			taskListVar.addTask(to_Do);
		} else {
			taskListVar.addTask(editIndex, to_Do);
			psl.push(new Parser("delete" + editIndex));
		}
		return to_Do.toString() + "is added to taskList!";
	}

}