package indigoSrc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

public class TimeParser {
	private String userCommand; // Raw command from user.
	private String sortedUserCommand; // Commands follows a format without a
										// Date: e.g.add go to school
	private DateTime endTime;
	private DateTime startTime;
	private List<Date> datesStart;
	private List<Date> datesEnd;
	static List<DateGroup> parser;
	static List<DateGroup> parserNewTimedTask;
	static List<DateGroup> parserNewDeadLineTask;
	boolean hasFilteredTimedTask = false;
	boolean hasFilteredDeadLineTask = false;
	ArrayList<String> filterWords = new ArrayList<String>();

	private final static Logger LOGGER = Logger.getLogger(TimeParser.class
			.getName());

	// private int numDate = -1;
	private static Scanner sc;

	public TimeParser(String someCommand) {
		userCommand = someCommand.trim();
		assert userCommand != null;

		// Loop deals with cases such as op2 and cs2103 and prevent identifiers
		// from removing them.
		for (int k = 0; k < userCommand.length(); k++) {
			if (Character.isDigit(userCommand.charAt(k))) {
				if (k == 0) {
					break;
				} else if (Character.isLetter(userCommand.charAt(k - 1))) {
					int digitInt = 0;
					char digitChar = userCommand.charAt(k);
					digitInt = Character.getNumericValue(digitChar);
					ArrayList<Integer> digits = new ArrayList<Integer>();
					digits.add(digitInt);
					int length = userCommand.length();
					userCommand = userCommand.substring(0, k) + "DIGIT"
							+ userCommand.substring(k + 1, length);
				}
			}
		}
		parser = new PrettyTimeParser().parseSyntax(userCommand);
		filterParser();
		filterTimedTask();
		filterDeadLineTask();

		if (parser == null) {
			LOGGER.log(Level.FINE, "Floating task detected.");
			sortedUserCommand = userCommand + "";
			assert isDateFree();
		}

		if (hasFilteredTimedTask == true) {
			parseTime();
		}

		if (hasFilteredDeadLineTask == true) {
			parseTime();
		}

		else {
			switch (parser.size()) {
			case 0:
				assert isDateFree();
				break;
			case 1:
			case 2:
				parseTime();
				break;
			default:
				LOGGER.log(Level.FINE, "More than 2 dateGroups detected.");
				LOGGER.log(Level.FINE, listAllDates(parser));
				parseTime();
			}
		}
		assert sortedUserCommand != null;
	}

	// Deals with 2 identifiers namely, "thursday" and "8pm-10pm" but refers to
	// the same day.
	private void filterTimedTask() {
		String[] descriptions;

		if (parser.size() == 2) {
			String first = parser.get(0).getText();
			String second = parser.get(1).getText();

			if (second.contains("to")) {
				hasFilteredTimedTask = true;
				descriptions = parser.get(1).getText().split("to");
				String date = parser.get(0).getText();
				userCommand = userCommand.replace(date, date + " "
						+ descriptions[0]);
				userCommand = userCommand.replace(second, date + " "
						+ descriptions[1]);
				userCommand = userCommand.replaceAll("( )+", " ");
				parserNewTimedTask = new PrettyTimeParser()
						.parseSyntax(userCommand);
			}

			else if (first.contains("to")) {
				hasFilteredTimedTask = true;
				descriptions = parser.get(0).getText().split("to");
				String date = parser.get(1).getText();
				userCommand = userCommand.replace(date, date + " "
						+ descriptions[1]);
				userCommand = userCommand.replace(first, date + " "
						+ descriptions[0]);
				userCommand = userCommand.replaceAll("( )+", " ");
				parserNewTimedTask = new PrettyTimeParser()
						.parseSyntax(userCommand);
			}
		}
	}

	// Deals with 2 identifiers namely, "monday" and "9pm" but they are
	// separated.
	private void filterDeadLineTask() {
		if (parser.size() == 2) {
			String start = parser.get(0).getText();
			String end = parser.get(1).getText();
			// Contains (5pm)and(12 dec 2015).
			if ((!(start.contains(" "))) || (!(end.contains(" ")))) {
				hasFilteredDeadLineTask = true;
				userCommand = userCommand.replace(start, start + " " + end);
				parserNewDeadLineTask = new PrettyTimeParser()
						.parseSyntax(userCommand);
			}
		}
	}

	// //Removes "6" or "wed" but found in "wedding" and deals with "3 a" and
	// "3 p" etc.
	private void filterParser() {
		filterWords();
		String regex1 = "\bmon\b";
		String regex2 = "\btue\b";
		String regex3 = "\bwed\b";
		String regex4 = "\bthu\b";
		String regex5 = "\bfri\b";
		String regex6 = "\bjan\b";
		String regex7 = "\bfeb\b";
		String regex8 = "\bmar\b";
		String regex9 = "\bapr\b";
		String regex10 = "\bmay\b";
		String regex11 = "\bjun\b";
		String regex12 = "\bjul\b";
		String regex13 = "\baug\b";
		String regex14 = "\bsep\b";
		String regex15 = "\boct\b";
		String regex16 = "\bnov\b";
		String regex17 = "\bdec\b";
		String regex18 = "\beve\b";

		for (int j = 0; j < parser.size(); j++) {
			String identified = parser.get(j).getText();
			// Removes stand-alone integers like "6" if identified.
			if (isInteger(parser.get(j).getText())) {
				parser.remove(j);
			}
			// If "3 a" identified, remove it.
			if ((identified.length() == 3) && (identified.charAt(2) == 'a')) {
				parser.remove(j);
			}
			// If "3 p" identified, remove it.
			if ((identified.length() == 3) && (identified.charAt(2) == 'p')) {
				parser.remove(j);
			}
			// Remove "wed" if it is found in "wedding".
			if (filterWords.contains(identified)) {
				if (!parser.get(j).getText().equals(regex1))
					if (!parser.get(j).getText().equals(regex2))
						if (!parser.get(j).getText().equals(regex3))
							if (!parser.get(j).getText().equals(regex4))
								if (!parser.get(j).getText().equals(regex5))
									if (!parser.get(j).getText().equals(regex6))
										if (!parser.get(j).getText()
												.equals(regex7))
											if (!parser.get(j).getText()
													.equals(regex8))
												if (!parser.get(j).getText()
														.equals(regex9))
													if (!parser.get(j)
															.getText()
															.equals(regex10))
														if (!parser
																.get(j)
																.getText()
																.equals(regex11))
															if (!parser
																	.get(j)
																	.getText()
																	.equals(regex12))
																if (!parser
																		.get(j)
																		.getText()
																		.equals(regex13))
																	if (!parser
																			.get(j)
																			.getText()
																			.equals(regex14))
																		if (!parser
																				.get(j)
																				.getText()
																				.equals(regex15))
																			if (!parser
																					.get(j)
																					.getText()
																					.equals(regex16))
																				if (!parser
																						.get(j)
																						.getText()
																						.equals(regex17))
																					if (!parser
																							.get(j)
																							.getText()
																							.equals(regex18)) {
																						parser.remove(j);
																					}
			}
		}
	}

	// Adds word to the ArrayList to be filtered out.
	public void filterWords() {
		filterWords.add("mon");
		filterWords.add("tue");
		filterWords.add("wed");
		filterWords.add("thu");
		filterWords.add("fri");
		filterWords.add("jan");
		filterWords.add("feb");
		filterWords.add("mar");
		filterWords.add("apr");
		filterWords.add("may");
		filterWords.add("june");
		filterWords.add("jul");
		filterWords.add("aug");
		filterWords.add("sep");
		filterWords.add("oct");
		filterWords.add("nov");
		filterWords.add("dec");
		filterWords.add("eve");
	}

	// Determine is a string contains purely integers.
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// Only got here if we didn't return false.
		return true;
	}

	private String listAllDates(List<DateGroup> ParserTest) {
		StringBuilder result = new StringBuilder("Dates are:\n");
		for (int i = 0; i < ParserTest.size(); i++) {
			result.append("DateGroup " + i + " " + ParserTest.get(i).getText()
					+ ":\n");
			for (int j = 0; j < ParserTest.get(i).getDates().size(); j++) {
				result.append(ParserTest.get(i).getDates().get(j).toString());
			}
		}
		return result.toString();
	}

	private void parseTime() {
		LOGGER.log(Level.FINE, "Time String detected:"
				+ parser.get(0).getText());
		if (parser.size() == 2) {
			LOGGER.log(Level.FINE, "Time String detected:"
					+ parser.get(1).getText());
		}
		datesStart = parser.get(0).getDates();

		if (parser.size() == 2) {
			datesEnd = parser.get(1).getDates();
		}
		// "thursday" and "5pm to 7pm" as separate time instances.
		if (hasFilteredTimedTask == true) {
			parseTimedTask3();
			// "thursday" and "5pm" as separate time instances.
		} else if (hasFilteredDeadLineTask == true) {
			parseDeadLineTask2();
			// "thursday 5pm to friday 7pm" together.
		} else if (datesStart.size() == 2) {
			parseTimedTask1();
			// "thursday 5pm" and "friday 7pm" as separate time instances.
		} else if (parser.size() == 2) {
			parseTimedTask2();
			// "thursday 5pm" together.
		} else if (datesStart.size() == 1) {
			parseDeadLineTask1();
		}
	}

	private void parseTimedTask1() {
		// TODO Auto-generated method stub
		LOGGER.log(Level.FINE, "Timed task1 detected.");
		startTime = new DateTime(datesStart.get(0));
		LOGGER.log(Level.FINE, "startTime: " + startTime.toString());
		endTime = new DateTime(datesStart.get(1));
		LOGGER.log(Level.FINE, "endTime: " + endTime.toString());
		assert isDateFree();
		assert startTime != null;
		assert endTime != null;
	}

	private void parseTimedTask2() {
		// TODO Auto-generated method stub
		LOGGER.log(Level.FINE, "Timed task2 detected.");
		startTime = new DateTime(datesStart.get(0));
		LOGGER.log(Level.FINE, "startTime: " + startTime.toString());
		endTime = new DateTime(datesEnd.get(0));
		LOGGER.log(Level.FINE, "endTime: " + endTime.toString());
		assert isDateFree();
		assert startTime != null;
		assert endTime != null;
	}

	private void parseTimedTask3() {
		// TODO Auto-generated method stub
		LOGGER.log(Level.FINE, "Timed task3 detected.");
		startTime = new DateTime(parserNewTimedTask.get(0).getDates().get(0));
		LOGGER.log(Level.FINE, "startTime: " + startTime.toString());
		endTime = new DateTime(parserNewTimedTask.get(1).getDates().get(0));
		LOGGER.log(Level.FINE, "endTime: " + endTime.toString());
		assert isDateFree();
		assert startTime != null;
		assert endTime != null;
	}

	private void parseDeadLineTask1() {
		// TODO Auto-generated method stub
		LOGGER.log(Level.FINE, "DeadLine task1 detected.");
		endTime = new DateTime(datesStart.get(0));
		LOGGER.log(Level.FINE, "endTime: " + endTime.toString());
		assert isDateFree();
		assert endTime != null;
	}

	private void parseDeadLineTask2() {
		// TODO Auto-generated method stub
		LOGGER.log(Level.FINE, "DeadLine task2 detected.");
		endTime = new DateTime(parserNewDeadLineTask.get(0).getDates().get(0));
		LOGGER.log(Level.FINE, "endTime: " + endTime.toString());
		assert isDateFree();
		assert endTime != null;
	}

	public boolean isDateFree() {
		List<DateGroup> parse = new PrettyTimeParser().parseSyntax(userCommand);
		int numDate = parse.get(0).getDates().size();

		if (numDate == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] argv) {
		ConsoleHandler handler = new ConsoleHandler();
		LOGGER.setLevel(Level.FINER);
		handler.setLevel(Level.FINER);
		LOGGER.addHandler(handler);

		sc = new Scanner(System.in);
		while (true) {
			TimeParser mp = new TimeParser(sc.nextLine());
			mp.isDateFree();
		}
	}

	public String getSortedCommand() {
		return sortedUserCommand;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public boolean isFloatingTask() {
		if (parser.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDeadLineTask() {
		if (endTime != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isTimedTask() {
		if (startTime != null && endTime != null) {
			return true;
		} else {
			return false;
		}
	}
}
/*
 * getDate: SimpleDateFormat sdf = new
 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); output =
 * sdf.format(mp.dateGroup.getDates().get(0));
 */
// search for key from the userCommand