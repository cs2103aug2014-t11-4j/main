import static org.junit.Assert.*;
import org.junit.Test;

public class Delete extends ExecutionClass {

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Delete(Parser parsing, ParserList parseL, TaskList list){
		parseris = parsing;
		psl = parseL;
		taskList = list;
	}
	
	@Test
	public void testDelete(){
		Parser testParse = new Parser("delete 1");
		assertEquals(delete(), "Task deleted");
	}
	
	public String delete() throws ArrayIndexOutOfBoundsException{
		int index = parseris.getDelIndex();
		try{
			psl.push(new Parser("add " + index + " " + taskList.get(index).getDescription()));
		} catch (ArrayIndexOutOfBoundsException err){
			return "index is not within the number of tasks in taskList";
		}
		taskList.deleteTask(index);
		return "Task deleted";
	}

}
