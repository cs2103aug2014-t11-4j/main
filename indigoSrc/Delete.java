package indigoSrc;
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
		int totalSize = taskListVar.getFloatingList().size() + taskListVar.getTimedList().size();
		if (index > totalSize || index < 1){
			return "Invalid index";
		} else {
		psl.push(new Parser("add " + index + " " + taskListVar.get(index).getDescription()));
		taskListVar.deleteTask(index);
		return "Task deleted";
		}
	}

}
