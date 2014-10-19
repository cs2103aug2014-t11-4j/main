import static org.junit.Assert.*;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

public class Read extends CommandClass{
	
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
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
	
/*	@Test
	public void testRead(){
		Parser testParse = new Parser("view");
		//assertEquals(view(), taskList.writeXMLDocument(FILE_NAME));
	}
	*/
	public String view(){
		if(parseris.getCommand().contains("-done")){
			return viewDone();
		} else if(parseris.getCommand().contains("-undone")){
			return viewUndone();
		} else if (parseris.getCommand().contains("-f")){
			return taskList.viewFloatingTask();
		} else if (parseris.getCommand().contains("-d")){
			return taskList.viewDeadlineTask(DATE_FORMAT);
		} else if (parseris.getCommand().contains("-t")){
			return taskList.viewTimedTask(DATE_FORMAT);
		} else{
			return taskList.viewAll(DATE_FORMAT);
		}
	}
	
	public String viewDone(){
		return taskList.viewDone();
	}
	
	public String viewUndone(){
		return taskList.viewUndone();
	}

}
