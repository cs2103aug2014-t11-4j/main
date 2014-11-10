package test;

import static org.junit.Assert.assertEquals;
import indigoSrc.LogicFacade;
import org.junit.BeforeClass;
import org.junit.Test;
//@author A0116678U
public class testSystem {
	private static String[] testInputsBasic;
	private static String[] testInputAdvanced;
	private int numOfAllTask;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String[] inputs = {"undo",
				"add some task",
				"add 2 another task",
				"add 300 valid task",
				"add time task today",
				"add another time task tomorrow",
				"add a third time task from 11am to 2pm yesterday",
				"add a 4th time task from 3pm to 11am next Monday",
				"delete 1",
				"delete 5",
				"delete 7",
				"delete 0",
				"delete -100",
				"add 0 a suspicious task",
				"edit 1 next friday edited time task",
				"edit 100 wrong edited task",
				"edit 7 last task",
				"edit a edited time task on 2014-10-30",
				"add a random task"};
		testInputsBasic = inputs;
		
		String[] moreInputs = new String[]{};
		testInputAdvanced = moreInputs;
	}

	@Test
	public void testBasic() {
		LogicFacade test = new LogicFacade();
		int sizeOfFloatingList = test.getTasks().getFloatingList().size();
		int sizeOfTimedList = test.getTasks().getTimedList().size();
		numOfAllTask = sizeOfFloatingList + sizeOfTimedList;
		for (String str: testInputsBasic){
			LogicFacade lc = new LogicFacade(str);
			if (str.contains("add") && !str.contains("wrong")){
				numOfAllTask ++;
			} if (str.contains("delete") && !lc.feedback.contains("Invalid")){
				numOfAllTask --;
			}
		}
		test = new LogicFacade();
		int expectedSizeOfFloatingList = test.getTasks().getFloatingList().size();
		int expectedSizeOfTimedList = test.getTasks().getTimedList().size();
		assertEquals(expectedSizeOfFloatingList+expectedSizeOfTimedList, numOfAllTask);
	}
	
	@Test
	public void testAdvance(){
		System.out.println(testInputAdvanced);
		//TODO
	}

}
