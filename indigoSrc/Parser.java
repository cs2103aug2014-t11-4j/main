package indigoSrc;

/** This class is meant to read in the command line from the user in the form of a string
 * 	and parse it so that the logic can access it simply. 
 * @author Joanna
 * 
 * It can also parse index and return the type of tasks which needs to be invoked for commands
 * like read, delete, complete and undo
 * @author KenHua
 *
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;

import parser.CommandKey;
import parser.StringDecipher;
import parser.TaskIdentifiers;

public class Parser {
	private boolean isValid;
	private String message;
	private DateTime now;
	private DateTime TimeRef;
<<<<<<< HEAD
	public TaskIdentifiers taskWord;
	// private static Logger logger = Logger.getLogger("Parser");
	// private String sortedCommand;
	CommandKey keyWord;
	String toDo = "";
=======
	private TaskIdentifiers taskWord;
	private CommandKey keyWord		;//stores the key command "add"/"delete" to return to logic
	private String toDo               	= "";//stores the final command to return to logic
>>>>>>> a33626139068e79085a023c5372ba852a4a2d6fe
	private String rawCommand;
	private DateTime startTime;
	private DateTime endTime;
	private int editIndex;
	private boolean isFloatingTask;
	private boolean isDeadlineTask;
	private boolean isTimedTask;
	static TimeParser testParser;
	private String ignoreChar = "";
	private int ignoreStart = 0;
	private int ignoreEnd = 0;
	private boolean ifContainQuo = false;
	ArrayList<String> detailsList = new ArrayList<String>();
	private final static Logger LOGGER = Logger.getLogger(Parser.class
			.getName());
	private static Scanner sc;
	private ArrayList<Integer> multipleIndices = new ArrayList<Integer>();

	public static void main(String args[]) {
		/*
		 * ConsoleHandler handler = new ConsoleHandler();
		 * LOGGER.setLevel(Level.FINER); handler.setLevel(Level.FINER);
		 * LOGGER.addHandler(handler);
		 */
		String testInput;
		System.out.println("Enter command:");
		sc = new Scanner(System.in);
		testInput = sc.nextLine();
		Parser test = new Parser(testInput);
		testParser = new TimeParser(testInput);
		try {
			System.out.println(TimeParser.parser.get(0).getText());
		} catch (IndexOutOfBoundsException err) {
			System.out.println("There are no such thing as time!");
		}
		System.out.println("Command:" + test.getCommand());
	}

	// Command containing key command and its description.
	public String getRawCommand() {
		if (rawCommand != null) {
			return rawCommand.trim();
		} else {
			return "";
		}
	}

	// Command without key command and time identifiers.
	public String getCommand() {
		toDo = toDo.trim();
		ArrayList<String> prepWordsList = doPrepWordsList();
		ArrayList<String> monthsList = doMonthsList();

		String[] description;
		int size = 0;
		try {
			size = TimeParser.parser.size();
		} catch (Exception err) {

		}
		String[] identifers = new String[size];
		// Storing date identified into an array.
		for (int i = 0; i < size; i++) {
			identifers[i] = TimeParser.parser.get(i).getText();
		}

		// Removing instances of integer if identified.
		for (int k = 0; k < identifers.length; k++) {
			if (isInteger(identifers[k])) {
				identifers[k] = "";
			}
		}

		// Removing instances of months if identified.
		for (int j = 0; j < size; j++) {
			if (monthsList.contains(identifers[j])) {
				String regex1 = "\bjan\b";
				String regex2 = "\bfeb\b";
				String regex3 = "\bmar\b";
				String regex4 = "\bapr\b";
				String regex5 = "\bmay\b";
				String regex6 = "\bjun\b";
				String regex7 = "\bjul\b";
				String regex8 = "\baug\b";
				String regex9 = "\bsep\b";
				String regex10 = "\boct\b";
				String regex11 = "\bnov\b";
				String regex12 = "\bdec\b";
				String regex13 = "\bmon\b";
				String regex14 = "\bwed\b";
				String regex15 = "\beve\b";

				if (!identifers[j].equals(regex1))
					if (!identifers[j].equals(regex2))
						if (!identifers[j].equals(regex3))
							if (!identifers[j].equals(regex4))
								if (!identifers[j].equals(regex5))
									if (!identifers[j].equals(regex6))
										if (!identifers[j].equals(regex7))
											if (!identifers[j].equals(regex8))
												if (!identifers[j]
														.equals(regex9))
													if (!identifers[j]
															.equals(regex10))
														if (!identifers[j]
																.equals(regex11))
															if (!identifers[j]
																	.equals(regex12))
																if (!identifers[j]
																		.equals(regex13))
																	if (!identifers[j]
																			.equals(regex14))
																		if (!identifers[j]
																				.equals(regex15)) {
																			identifers[j] = "";
																		}
			}
		}

		// Replacing valid time and date instances with IDENTIFIER.
		for (int k = 0; k < size; k++) {
			toDo = toDo.replaceFirst(identifers[k], "IDENTIFIER");
			description = toDo.split(" ");
			for (int j = 0; j < description.length; j++) {
				if (description[j].contains("IDENTIFIER") && j > 0) {
					String prepWord = description[j - 1];
					removeIdentifierAndPrep(prepWordsList, description, j,
							prepWord);
				}
				removeIdentifier();
			}
		}
		// If command contains quotations, replace the original quotes back.
		if (ifContainQuo == true) {
			toDo = toDo.replace("QUOTATION", ignoreChar);
		}
		return toDo;
	}

	// If identifier does not have preposition words before it, simply remove
	// the identifier.
	public void removeIdentifier() {
		toDo = toDo.replace("IDENTIFIER", "");
		toDo = toDo.replaceAll("( )+", " ");
		toDo = toDo.trim();
	}

	// Removes identifier found and the preposition word that appears before it.
	public void removeIdentifierAndPrep(ArrayList<String> prepWordsList,
			String[] description, int j, String prepWord) {
		if (prepWordsList.contains(prepWord)
				&& description[j].equals("IDENTIFIER")) {
			String finaltoDo = "";
			description[j - 1] = "";
			for (int m = 0; m < description.length; m++) { // Removes the
															// preposition word.
				finaltoDo = finaltoDo + description[m] + " ";
			}
			toDo = finaltoDo;
			toDo = toDo.replace("IDENTIFIER", ""); // Removes the identifier.
			toDo = toDo.trim();
			toDo = toDo.replaceAll("( )+", " ");
		}
	}

	// Create an ArrayList consisting of preposition words.
	public ArrayList<String> doPrepWordsList() {
		ArrayList<String> prepWordsList = new ArrayList<String>();
		prepWordsList.add("on");
		prepWordsList.add("by");
		prepWordsList.add("at");
		prepWordsList.add("from");
		prepWordsList.add("in");
		prepWordsList.add("until");
		return prepWordsList;
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

	// Create an ArrayList consisting of months words.
	public ArrayList<String> doMonthsList() {
		ArrayList<String> monthsList = new ArrayList<String>();
		monthsList.add("jan");
		monthsList.add("feb");
		monthsList.add("mar");
		monthsList.add("apr");
		monthsList.add("may");
		monthsList.add("jun");
		monthsList.add("jul");
		monthsList.add("aug");
		monthsList.add("sep");
		monthsList.add("nov");
		monthsList.add("dec");
		monthsList.add("mon");
		monthsList.add("wed");
		monthsList.add("fri");
		return monthsList;
	}

	public Parser(String userCommand) {
		isValid = ifEmpty(userCommand);
		message = "Command is sucessfully processed.";
		editIndex = -1;

		String[] sentenceArray = userCommand.split("\\s+");
		if (sentenceArray.length > 1) {
			rawCommand = sentenceArray[1] + "";
		}
		StringDecipher sentenceString = new StringDecipher(sentenceArray);

		// Key word of the command must be either the first or last of the
		// sentence.
		keyWord = sentenceString.getKey();
		if (keyWord.equals(CommandKey.CLEAR)
				&& sentenceString.getWordsLeft() != 0) {
			keyWord = CommandKey.DELETE;
		}
		assert sentenceString.getWordsLeft() >= 0;

		if (sentenceString.getWordsLeft() == 0) {
			isValid = keyWord.checkValidAlone();
		}

		/*
		 * If only the index is stated (apart from key word), index can be first
		 * or last. Unless it's an add which makes sense if user wants to add a
		 * number to tasklist. Or edit, which doesn't make sense as to which
		 * task to edit. Clear too cannot have number as it by default clears
		 * all. editIndex by default is -1.
		 */
		if (sentenceString.getWordsLeft() == 1
				&& (!(keyWord.equals(CommandKey.CREATE) || keyWord
						.equals(CommandKey.UPDATE)))) {
			editIndex = sentenceString.getIndex();
		}
<<<<<<< HEAD
		/*
		 * If the command is one of the following (in the if statement),
		 * taskIdentifiers words can be used to execute the command.
		 */
		if (keyWord.equals(CommandKey.DELETE)
				|| keyWord.equals(CommandKey.COMPLETE)
				|| keyWord.equals(CommandKey.READ)
				|| keyWord.equals(CommandKey.UNCOMPLETE)
				|| keyWord.equals(CommandKey.UNDO)
				|| keyWord.equals(CommandKey.REDO)) {
=======
		
	/*	If the command is one of the following (in the if statement), taskIdentifiers 
	 * 	words can be used to execute the command. 
	 */
		if(keyWord.equals(CommandKey.DELETE) || keyWord.equals(CommandKey.COMPLETE) ||
		   keyWord.equals(CommandKey.READ) || keyWord.equals(CommandKey.UNCOMPLETE) ||
		   keyWord.equals(CommandKey.UNDO) || keyWord.equals(CommandKey.REDO)) {
>>>>>>> a33626139068e79085a023c5372ba852a4a2d6fe
			taskWord = sentenceString.checkTaskWords(keyWord);
		}

		/*
		 * If the command is one of the following (in the if statement),
		 * multiple index can be input by the user. Thus, this block is to check
		 * and extract the indices.
		 */
		if (keyWord.equals(CommandKey.DELETE)
				|| keyWord.equals(CommandKey.COMPLETE)
				|| keyWord.equals(CommandKey.UNCOMPLETE)) {
			multipleIndices = sentenceString.extractIndices();
		}
		/*
		 * === editIndex status === If the command word is the first word, index
		 * of editing must be stated after it. Else if command word is not the
		 * first word, index must be the first word. If it appears anywhere
		 * else, it is regarded as time. Only when adding a task, index will not
		 * be considered.
		 */
		if (sentenceString.getWordsLeft() >= 1) {
			if (!(keyWord.equals(CommandKey.CREATE))) {
				editIndex = sentenceString.getIndex();
			}
			toDo = sentenceString.remainingToString();

			LOGGER.log(Level.FINE, "toDo: " + toDo);
			LOGGER.log(Level.FINE, "editIndex " + editIndex);
		} else {
			assert sentenceString.getWordsLeft() == 0;
		}

		// Check for command with quotations before parsing.
		String tempCheck = toDo;
		ArrayList<Integer> quotations = new ArrayList<Integer>();

		// Check for the number of '"' in the string.
		for (int i = 0; i < tempCheck.length(); i++) {
			if (tempCheck.charAt(i) == '\"') {
				quotations.add(i);
			}
		}

		if (quotations.size() == 2) { // Quotations found.
			ignoreStart = quotations.get(0);
			ignoreEnd = quotations.get(1);
			ignoreChar = tempCheck.substring(ignoreStart - 1, ignoreEnd + 2);
			ignoreChar = ignoreChar.trim();
		}

		// Sieve out the words found inside the quotations.
		if (quotations.size() == 2) {
			ifContainQuo = true;
			toDo = toDo.replace(ignoreChar, "QUOTATION");
			toDo = toDo.trim();
			toDo = toDo.replaceAll("( )+", " ");
		}

		TimeParser timeParser = new TimeParser(toDo);
		// sortedCommand = timeParser.getSortedCommand() + "";

		if (timeParser.isTimedTask()) {
			// Start time is earlier than end time.
			if ((timeParser.getStartTime()).compareTo(timeParser.getEndTime()) < 0) {
				startTime = timeParser.getStartTime();
				endTime = timeParser.getEndTime();
			} else { // Start time is later than end time, so swap.
				endTime = timeParser.getStartTime();
				startTime = timeParser.getEndTime();
			}
			isTimedTask = true;
		} else if (timeParser.isDeadLineTask()) {
			endTime = timeParser.getEndTime();
			isDeadlineTask = true;
		} else {
			assert timeParser.isFloatingTask();
			isFloatingTask = true;
		}

		if (endTime != null) {
			smartParserCheck();
		}
		assert keyWord != null;
	}

	// Checks if the user has input nothing or just white spaces.
	public boolean ifEmpty(String userCommand) {
		userCommand.trim();
		if (userCommand.equals("")) {
			return false;
		} else {
			return true;
		}
	}

	public CommandKey getKeyCommand() {
		return keyWord;
	}

	// Edit index is tampered to return default 1.
	public int getEditIndex() {
		if (editIndex == -1) {
			return 1;
		}
		return editIndex;
	}

	public ArrayList<Integer> getMultipleIndices() {
		return multipleIndices;
	}

	// Get the raw edit index, which is not tampered to return
	// default 1.
	public int getRawEditIndex() {
		return editIndex;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public boolean isDeadlineTask() {
		return isDeadlineTask;
	}

	public boolean isFloatingTask() {
		return isFloatingTask;
	}

	public boolean isTimedTask() {
		return isTimedTask;
	}

	// Checks if the date and time entered by user is not a past time. If it is,
	// this method will auto correct the timing. If there is a start time (timed
	// task),
	// the start time will not change even is it has past.
	public void smartParserCheck() {
		now = new DateTime();
		TimeRef = now.plusMinutes(2);
		if (endTime.isBefore(now)) {
			if (endTime.getYear() == now.getYear()
					&& endTime.getDayOfYear() == now.getDayOfYear()) {
				if (endTime.isBefore(TimeRef)) {
					DateTime newDate = endTime.plusDays(1);
					endTime = newDate;
				}
			} else {
				int days = now.getDayOfYear() - endTime.getDayOfYear();
				if (now.getYear() > endTime.getYear()) {
					days += 365 * (now.getYear() - endTime.getYear());
				}
				DateTime newDate = endTime.plusDays(days);
				if (newDate.isBefore(TimeRef)) {
					newDate = newDate.plusDays(1);
				}
				endTime = newDate;
			}
		}
	}

	public boolean isValid() {
		return isValid;
	}

	public String getMessage() {
		return message;
	}
	
	public TaskIdentifiers getTaskWord(){
		return taskWord;
	}
}