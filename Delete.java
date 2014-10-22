import static org.junit.Assert.*;

import org.junit.Test;

public class Delete extends CommandClass{

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Delete(Parser parsing, ParserList parseL, TaskList taskList){
		parserVar = parsing;
		psl = parseL;
		this.taskListVar = taskList;
	}
	
	@Test
	public void testDelete(){
		Parser testParse = new Parser("delete 1");
		assertEquals(delete(), "Task deleted");
	}
	
	public String delete() throws ArrayIndexOutOfBoundsException{
		int index = parserVar.getEditIndex();
		try{
			psl.push(new Parser("add " + index + " " + taskListVar.get(index).getDescription()));
		} catch (ArrayIndexOutOfBoundsException err){
			return "index is not within the number of tasks in taskList";
		}
		taskListVar.deleteTask(index);
		return "Task deleted";
	}

}
