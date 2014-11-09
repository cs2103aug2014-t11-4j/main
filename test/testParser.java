package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import parser.Parser;

public class testParser {

	@Test
	public void testFloatingTask() {
		String[] strs = { "Meet huimin to study", "Buy groceries",
				"Pass by yunting house", "Go on a holiday", "Buy 7 oranges" };
		for (int i = 0; i < strs.length; i++) {
			Parser test = new Parser(strs[i]);
			assertTrue(test.isFloatingTask());
		}
	}

	@Test
	public void testDeadLineTask() {
		String[] strs = { "engin project due next tuesday",
				"Buy groceries tomorrow",
				"Pass by yunting house on 12 dec 2015",
				"Go on a holiday with dad next wednesday",
				"Buy 7 oranges by 8pm" };
		for (int k = 0; k < strs.length; k++) {
			Parser test = new Parser(strs[k]);
			assertTrue(test.isDeadlineTask());
		}
	}

	@Test
	public void testTimedTask() {
		String[] strs = { "from 7pm to 8pm meet huimin to study on thursday",
				"camp from 7pm sunday to 10pm monday" };
		for (int j = 0; j < strs.length; j++) {
			Parser test = new Parser(strs[j]);
			assertTrue(test.isTimedTask());
		}
	}
}