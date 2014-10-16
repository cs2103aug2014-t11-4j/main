import static org.junit.Assert.*;

import org.junit.Test;

public class Update extends ExecutionClass {

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return edit();
	}

	public Update(Parser parsing, ParserList parseL, TaskList list){
		parseris = parsing;
		psl = parseL;
		taskList = list;
	}
	
	@Test
	public void testUpdate(){
		Parser testParse = new Parser("edit 1 change to study");
		assertEquals(edit(), "Task updated");
	}
	
	public String edit() throws ArrayIndexOutOfBoundsException{
		int index = parseris.getEditIndex();
		FloatingTask task = new FloatingTask(parseris.getCommand());
		try{
			psl.push(new Parser("edit " + index + " " + taskList.get(index).getDescription()));
		} catch (ArrayIndexOutOfBoundsException err){
			return "index is not within the number of tasks in taskList";
		}
		taskList.editTask(index, task);
		return "Task updated";
	}
}
