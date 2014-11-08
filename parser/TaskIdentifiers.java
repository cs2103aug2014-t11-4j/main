package parser;

/**This is the enum class together with the identifier method of TaskIdentifiers.
 * Task Identifiers are the type of keywords important for certain command class.
 * eg. Delete class needs most of the identifiers except Today, tomorrow and ... (time based)
 * 	   Read class will use all of the identifiers.
 * This class identifies the possible words for TaskIdentifiers and return the result
 * to the class which invoked it.
 * 
 * @author KenHua
 *
 */
public enum TaskIdentifiers {
	ALL, OVERDUE, FLOATING, DEADLINE, COMPLETED, UNCOMPLETED, INVALID,
	TODAY, THIS_WEEK, NEXT_WEEK, THIS_MONTH, TOMORROW;
	
	public static TaskIdentifiers indentifyWords(String usedWords){
		usedWords = usedWords.trim();
		switch(usedWords){
			case "all": 	//default word
			case "a":		//short key
				return TaskIdentifiers.ALL;
			case "overdue":	//default word
			case "over":	//alternate word
			case "-o":		//short key
				return TaskIdentifiers.OVERDUE;
			case "floating"://default word	
			case "flaoting"://spelling mistake
			case "float":	//alternate word
			case "flaot":	//alternate spelling mistake
			case "-f":		//short key
				return TaskIdentifiers.FLOATING;
			case "deadline"://default word
			case "dl":		//shortened word
			case "-dl":		//short key
				return TaskIdentifiers.DEADLINE;
			case "completed"://default word (notice the past tense)
			case "done":	//alternate word
			case "checked":	//alternate word
				return TaskIdentifiers.COMPLETED;
			case "notcompleted"://default long word
			case "undone":	//alternate word
			case "unchecked"://alternate word
				return TaskIdentifiers.UNCOMPLETED;	
			case "today":	//default word
			case "-t":		//short key
				return TaskIdentifiers.TODAY;
			case "tomorrow"://default word
			case "-tom":	//short key
				return TaskIdentifiers.TOMORROW;
			case "thisweek"://default long word
			case "week":	//default word (if only week is input)
			case "-w":		//short key
				return TaskIdentifiers.THIS_WEEK;
			case "nextweek"://default long word
			case "next":
			case "-nw":		//short key
				return TaskIdentifiers.NEXT_WEEK;
			case "thismonth"://default long word
			case "month":	//default word (if only month is input)
			case "-m":		//short key
				return TaskIdentifiers.THIS_MONTH;
			default:
				return TaskIdentifiers.INVALID;
		}
	}
}
