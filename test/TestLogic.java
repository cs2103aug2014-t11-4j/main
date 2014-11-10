package test;

import static org.junit.Assert.assertEquals;
import indigoSrc.LogicFacade;

import org.junit.BeforeClass;
import org.junit.Test;
//@author A0112230H
public class TestLogic {

	private static String[] testInputsBasic;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String[] inputs = {
				"write diary entry",
				"go to developer fest on 5 March 2015",
				"interview for backend programming job 12 Dec 2pm to 4pm",
				"-a attend N-house workshop next sat 7pm",
				"submit assignment by 2359pm addd",
				"yesterday celebrate high school reunion",
				"buy 30 cartons of Milo for welfare event on Monday",
				"del 2",
				"del 3 1 4",
				"undo",
				"undo 3",
				"undo -11",
				"-3 1 del",
				"redo all",
				"-e 2 6pm next week",
				"go to karaoke -e",
				"-c 2 4 6",
				"-uc 2 -1",
				"undo -1",
				"view -f",
				"view next week",
				"-v completed",
				"undone -v",
				"go to friday night party at 2300pm",
				"del1",
				"del 42",
				};
		testInputsBasic = inputs;
		
	}

	@Test
	public void testBasic() {
		LogicFacade test = new LogicFacade();
		for (String str: testInputsBasic){
			LogicFacade lc = new LogicFacade(str);
			System.out.println(lc.feedback);
		}
		test = new LogicFacade();
		int expectedSizeOfFloatingList = test.getTasks().getFloatingList().size();
		int expectedSizeOfTimedList = test.getTasks().getTimedList().size();
		assertEquals(expectedSizeOfFloatingList+expectedSizeOfTimedList, 5);
		test = new LogicFacade("-z");
	}

}
