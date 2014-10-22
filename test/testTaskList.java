package test;

import static org.junit.Assert.*;
import indigoSrc.DeadlineTask;
import indigoSrc.FloatingTask;
import indigoSrc.IndigoLogic;
import indigoSrc.TaskList;
import indigoSrc.TimedTask;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

public class testTaskList {
	private static String[] testStr;
	private static DateTime[] testDates;
	private static TaskList testList;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String[] strs = {"kk",
				" pr         oje ct        meeti    ng",
				"p@@r$$$oj##e ct meetin#$g        ",
				"preject meeting",
				"project meeting"};
		testStr = strs;
		DateTime[] dates = {new DateTime(2010,10,10,18,0,0),
				new DateTime(2011,9,1,22,22,22),
				new DateTime(2015,3,4,5,8,9)};
		testDates = dates;
		testList = new TaskList();
		// add 5 floatingTask
		for (String str: testStr){
			FloatingTask testTask = new FloatingTask(str);
			testList.addTask(testTask);
		}
		testList.get(2).complete();
		//add 3 deadlineTask, 3 timedTask
		for (int i=0;i<testDates.length-1;i++){
			DeadlineTask testTask1 = new DeadlineTask(testStr[0],testDates[i]);
			DeadlineTask testTask2 = new TimedTask(testStr[0],testDates[i],testDates[i+1]);
			testList.addTask(testTask1);
			testList.addTask(testTask2);
		}
		testList.get(8).complete();
		
	}

	@Test
	public void test() {
		TaskList list1 = new TaskList();
		testList.writeXMLDocument("testStorage1");
		list1.readFromXML("testStorage1");
		list1.writeXMLDocument("testStorage2");
		for (int i=1;i<list1.getList().size()+1;i++){
			assertEquals(list1.viewFloatingTask(),testList.viewFloatingTask());
			assertEquals(list1.viewDeadlineTask(IndigoLogic.DATE_FORMAT),testList.viewDeadlineTask(IndigoLogic.DATE_FORMAT));
			assertEquals(list1.viewTimedTask(IndigoLogic.DATE_FORMAT),testList.viewTimedTask(IndigoLogic.DATE_FORMAT));
			assertEquals(list1.viewNormal(IndigoLogic.DATE_FORMAT),testList.viewNormal(IndigoLogic.DATE_FORMAT));
		}
	}

}
