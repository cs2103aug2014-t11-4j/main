package parser;

public enum TaskIdentifiers {
	ALL, OVERDUE, FLOATING, DEADLINE, TIMED, INVALID;
	
	public TaskIdentifiers indentifyWords(String usedWords){
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
}
