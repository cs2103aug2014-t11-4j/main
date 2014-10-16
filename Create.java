import static org.junit.Assert.*;

import org.junit.Test;

public class Create extends ExecutionClass {
	
	FloatingTask to_Do = new FloatingTask(parseris.getCommand());{
		assert parseris.getCommand() instanceof String;
	}
	Integer editIndex = parseris.getEditIndex();{
		assert editIndex instanceof Integer;
	}
	
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return add();
	}	
	
	public Create(Parser parsing, ParserList parseL, TaskList list){
		parseris = parsing;
		psl = parseL;
		taskList = list;
		to_Do = new FloatingTask(parseris.getCommand());
	}
	
	@Test
	public void testCreate(){
		Parser testParse = new Parser("add code finish v0.2");
		assertEquals(add(), to_Do.toString() + "is added to taskList!");
	}
	
	public String add() {
		if(editIndex==null){
			psl.push(new Parser("delete" + taskList.getList().size()));
			taskList.addTask(to_Do);
		} else {
			taskList.addTask(editIndex, to_Do);
			psl.push(new Parser("delete" + editIndex));
		}
		return to_Do.toString() + "is added to taskList!";
	}
}