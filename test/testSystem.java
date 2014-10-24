package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import indigoSrc.IndigoLogic;

import org.junit.BeforeClass;
import org.junit.Test;

public class testSystem {
	private static String[] testInputs;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String[] strs = {"add some task",
				"add 2 another task",
				"add 300 impossible task",
				"add time task today",
				"add another time task tomorrow",
				"add a third time task from 11am - 2pm yesterday",
				"add last time task from 3pm-11am next Monday",
				"delete 1",
				"undo",
				"delete 5",
				"undo",
				"delete 7",
				"undo",
				"delete 0",
				"delete -100",
				"add 0 a suspicious task",
				"edit 1 next friday edited task",
				"edit 100 wrong edited task",
				"edit 7 last task",
				"undo",
				"undo"};
		testInputs = strs;
	}

	@Test
	public void test() {
		ArrayList<String> feedback = new ArrayList<String>();
		for (String str: testInputs){
			IndigoLogic lc = new IndigoLogic(str);
			feedback.add(lc.feedback);
		}
	}

}
