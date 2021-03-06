package test;

import static org.junit.Assert.*;
import indigoSrc.LogicFacade;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import Storage.DeadlineTask;
import Storage.FloatingTask;
import Storage.Task;
import Storage.TaskList;
import Storage.TimedTask;
//@author A0116678U
public class testTaskList {
	private static String[] testStr;
	private static DateTime[] testDates;
	private static TaskList testList;
	private static String newLine = System.getProperty("line.separator");
	private static StringBuilder outputStr = new StringBuilder("There are 9 tasks listed:"+newLine
			+ "Deadline tasks are:"+newLine
			+ "1. 10/10/2010, 18:00, buy a fish"+newLine
			+ "2. 10/10/2010, 18:00 - 01/09/2011, 22:22,"+newLine
			+ "something happens" + '\u2713'+newLine
			+ "3. 01/09/2011, 22:22 - 04/03/2015, 05:08,"+newLine
			+ "something happens"+newLine);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String[] strs = {"buy a fish",
				"project meeting",
				"something happens",
				"buy a cat",
				"lecture on Monday"};
		// boundary case for string with spaces, special chars
		testStr = strs;
		DateTime[] dates = {new DateTime(2010,10,10,18,0,0),
				new DateTime(2011,9,1,22,22,22),
				new DateTime(2015,3,4,5,8,9)};
		testDates = dates;
		testList = new TaskList();
		// add 5 floatingTask
		for (String str: testStr){
			Task testTask = new FloatingTask(str);
			testList.addTask(testTask);
		}
		Task newTask = new FloatingTask();
		testList.complete(2);
		testList.editTask(2,newTask);
		//add 3 deadlineTask, 3 timedTask
		for (int i=0;i<testDates.length-1;i++){
			DeadlineTask testTask1 = new DeadlineTask(testStr[0],testDates[i]);
			DeadlineTask testTask2 = new TimedTask(testStr[2],testDates[i],testDates[i+1]);
			testList.addTask(testTask1);
			testList.addTask(testTask2);
		}
		DateTime now = DateTime.now();
		Task anotherTask = new DeadlineTask("edited Task",now);
		outputStr.append("4. " + LogicFacade.DATE_FORMAT.print(now)+ ", edited Task"+newLine);
		testList.complete(2);
		testList.editTask(3, anotherTask);
		testList.complete(8);
		testList.editTask(8,newTask);
		
	}

	@Test
	public void testSaveAndRead() {
		TaskList list1 = new TaskList();
		testList.writeXMLDocument("testStorage1");
		list1.readFromXML("testStorage1");
		assertEquals(list1.viewFloatingTask(),testList.viewFloatingTask());
		assertEquals(list1.viewDeadlineTask(LogicFacade.DATE_FORMAT),testList.viewDeadlineTask(LogicFacade.DATE_FORMAT));
		assertEquals(list1.viewAll(LogicFacade.DATE_FORMAT),testList.viewAll(LogicFacade.DATE_FORMAT));
	}
	
	@Test
	public void testDeadlineTask(){
		outputStr.append("Floating tasks are:"+newLine
				+ "1. lecture on Monday"+newLine
				+ "2. default task."+newLine
				+ "3. something happens"+newLine
				+ "4. default task."+newLine
				+ "5. buy a fish"+newLine);
		assertEquals(testList.viewAll(LogicFacade.DATE_FORMAT),outputStr.toString());
	}

}
