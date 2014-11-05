package parser;

public enum TaskIdentifiers {
	ALL, OVERDUE, FLOATING, DEADLINE, COMPLETED, UNCOMPLETED, TIMED, INVALID,
	TODAY, THIS_WEEK, NEXT_WEEK, THIS_MONTH, TOMORROW;
	
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
			case "flaot":
			case "-f":
				return TaskIdentifiers.FLOATING;
			case "deadline":
			case "dl":
			case "-dl":
				return TaskIdentifiers.DEADLINE;
			case "completed":
			case "done":
			case "checked":
				return TaskIdentifiers.COMPLETED;
			case "notcompleted":
			case "undone":
			case "unchecked":
				return TaskIdentifiers.UNCOMPLETED;
			case "timed":
			case "time":
			case "period":
			case "-tm":
				return TaskIdentifiers.TIMED;		
			case "today":
			case "-t":
				return TaskIdentifiers.TODAY;
			case "tomorrow":
			case "-tom":
				return TaskIdentifiers.TOMORROW;
			case "thisweek":
			case "week":
			case "-w":
				return TaskIdentifiers.THIS_WEEK;
			case "nextweek":
			case "-nw":
				return TaskIdentifiers.NEXT_WEEK;
			case "thismonth":
			case "month":
			case "-m":
				return TaskIdentifiers.THIS_MONTH;
			default:
				return TaskIdentifiers.INVALID;
		}
	}
	
	public static String toString(TaskIdentifiers Ti){
		if(Ti==null){
			return "null";
		}
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
