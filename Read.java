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
		parserVar = parsing;
		psl = parseL;
		this.taskListVar = taskList;
	}
	
/*	@Test
	public void testRead(){
		Parser testParse = new Parser("view");
		//assertEquals(view(), taskList.writeXMLDocument(FILE_NAME));
	}
	*/
	public String view(){
		if(parserVar.getCommand().contains("-done")){
			return viewDone();
		} else if(parserVar.getCommand().contains("-undone")){
			return viewUndone();
		} else if (parserVar.getCommand().contains("-f")){
			return taskListVar.viewFloatingTask();
		} else if (parserVar.getCommand().contains("-d")){
			return taskListVar.viewDeadlineTask(DATE_FORMAT);
		} else if (parserVar.getCommand().contains("-t")){
			return taskListVar.viewTimedTask(DATE_FORMAT);
		} else{
			return taskListVar.viewAll(DATE_FORMAT);
		}
	}
	
	public String viewDone(){
		return taskListVar.viewDone();
	}
	
	public String viewUndone(){
		return taskListVar.viewUndone();
	}

}
