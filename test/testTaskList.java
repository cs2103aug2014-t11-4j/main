package test;

import static org.junit.Assert.*;
import indigoSrc.DeadlineTask;
import indigoSrc.FloatingTask;
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
		String[] strs = {"",
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
		//add 3 deadlineTask, 3 timedTask
		for (int i=0;i<testDates.length-1;i++){
			DeadlineTask testTask1 = new DeadlineTask(testStr[0],testDates[i]);
			DeadlineTask testTask2 = new TimedTask(testStr[0],testDates[i],testDates[i+1]);
			testList.addTask(testTask1);
			testList.addTask(testTask2);
		}
		
	}

	@Test
	public void test() {
		TaskList list1 = new TaskList();
		testList.writeXMLDocument("testStorage1");
		list1.readFromXML("testStroage");
		for (int i=0;i<list1.getList().size();i++){
			assertEquals(list1.get(i),testList.get(i));
		}
	}

}
