package test;

import static org.junit.Assert.*;
import indigoSrc.DeadlineTask;
import indigoSrc.FloatingTask;
import indigoSrc.TaskList;

import org.joda.time.DateTime;
import org.junit.Test;

public class TestView {
	TaskList testList = new TaskList();
	FloatingTask task1 = new DeadlineTask("buy book", new DateTime(2014,10,23,11,00,00));
	DeadlineTask time1 = new DeadlineTask("read book", new DateTime(2014,10,24,19,15,00));
	DeadlineTask time2 = new DeadlineTask("buy bread", new DateTime(2014,10,22,19,15,00));
	DeadlineTask time3 = new DeadlineTask("eat bread", new DateTime(2013,5,22,19,15,00));
	DeadlineTask time4 = new DeadlineTask("buy movie ticket", new DateTime(2013,11,21,19,15,00));
	DeadlineTask time5 = new DeadlineTask("watch movie", new DateTime(2015,1,22,19,15,00));
	DeadlineTask time6 = new DeadlineTask("go shopping", new DateTime(2015,10,23,19,15,00));
	testList.addTask(task1);
	testList.addTask(task1);
	testList.addTask(time1);
	testList.addTask(time2);
	testList.addTask(time3);
	testList.addTask(time4);
	testList.addTask(time5);
	testList.addTask(time6);
	
	//Test the viewToday method. Only "buy book" should appear.
	System.out.println(viewToday(test));
	//Test the viewThisWeek method. "buy book" and "read book" only should appear.
	System.out.println(viewThisWeek(test));
	//Test overdue view. "buy bread", "eat bread", "buy movie ticket" should appear.
	System.out.println(viewOverDue(test));

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
