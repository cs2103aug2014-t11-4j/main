import static org.junit.Assert.*;

import org.junit.Test;

public class Read extends ExecutionClass {

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return view();
	}
	
	public Read(Parser parsing, ParserList parseL, TaskList list){
		parser = parsing;
		psl = parseL;
		taskList = list;
	}
	
	@Test
	public void testRead(){
		Parser testParse = new Parser("view");
		assertEquals(view(), "view all");
	}
	
	public String view(){
		if(parser.getCommand().contains("-done")){
			return viewDone();
		} else if(parser.getCommand().contains("-undone")){
			return viewUndone();
		} else {
			return "view all";
		}
	}
	
	public String viewDone(){
		return taskList.viewDone();
	}
	
	public String viewUndone(){
		return taskList.viewUndone();
	}
}
