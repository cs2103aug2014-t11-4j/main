import static org.junit.Assert.*;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

public class Read extends CommandClass{
	
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yy");
	private static final String FILE_NAME = "myTask";

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return view();
	}
	
	public Read(Parser parsing, ParserList parseL, TaskList taskList){
		parseris = parsing;
		psl = parseL;
		this.taskList = taskList;
	}
	
	@Test
	public void testRead(){
		Parser testParse = new Parser("view");
		assertEquals(view(), taskList.write(FILE_NAME, DATE_FORMAT));
	}
	
	public String view(){
		if(parseris.getCommand().contains("-done")){
			return viewDone();
		} else if(parseris.getCommand().contains("-undone")){
			return viewUndone();
		} else {
			return taskList.write(FILE_NAME, DATE_FORMAT);
		}
	}
	
	public String viewDone(){
		return taskList.viewDone();
	}
	
	public String viewUndone(){
		return taskList.viewUndone();
	}

	@Override
	public String undo() {
		// TODO Auto-generated method stub
		return null;
	}
}
