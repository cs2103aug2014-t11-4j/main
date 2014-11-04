package parser;

public enum TaskIdentifiers {
	ALL, OVERDUE, FLOATING, DEADLINE, TIMED, INVALID;
	
	public static TaskIdentifiers indentifyWords(String usedWords){
		usedWords = usedWords.trim();
		switch(usedWords){
			case "all":
			case "a":
				return TaskIdentifiers.ALL;
			case "overdue":
			case "over":
			case "-o":
				return TaskIdentifiers.OVERDUE;
			case "floating":
			case "flaoting":
			case "float":
			case "-f":
				return TaskIdentifiers.FLOATING;
			case "deadline":
			case "dl":
			case "-dl":
				return TaskIdentifiers.DEADLINE;
			case "timed":
			case "time":
			case "period":
			case "-tm":
				return TaskIdentifiers.TIMED;			
			default:
				return TaskIdentifiers.INVALID;
		}
	}
	
	public static String toString(TaskIdentifiers Ti){
		switch(Ti){
			case ALL:
				return "all";
			case FLOATING:
				return "float";
			default:
				return "bla";
		}
	}
}
