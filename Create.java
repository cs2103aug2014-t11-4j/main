import static org.junit.Assert.*;

import org.junit.Test;

public class Create extends ExecutionClass {
	protected static Parser parser;
	protected static ParserList psl = new ParserList();
	protected static TaskList taskList = new TaskList();

	FloatingTask to_Do = new FloatingTask(parser.getCommand());
	Integer editIndex = parser.getEditIndex();
	
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return add();
	}	
	
	public Create(Parser parseing, ParserList parseL, TaskList listy){
		parser = parseing;
		psl = parseL;
		taskList = listy;
		to_Do = new FloatingTask(parser.getCommand());
	}
	
	@Test
	public void testCreate(){
		assertEquals(add(), to_Do.toString() + "is added to taskList!");
	}
	
	public String add(){
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