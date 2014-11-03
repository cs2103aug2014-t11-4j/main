package parser;
public enum CommandKey {
	CREATE, READ, UPDATE, DELETE, UNDO, COMPLETE, UNCOMPLETE, REDO, SEARCH, CLEAR, INVALID;

	public static CommandKey identifyKey(String keyCommand){
		switch(keyCommand){
			case "add":		//default command
			case "addd":	//Spelling mistake
			case "-a":		//Pro shortkey
				return CommandKey.CREATE;
			case "view":	//default command
			case "-v":		//pro shortkey
			case "display": //Alternate name
			case "veiw":	//spelling mistake
				return CommandKey.READ;
			case "edit":	//default command
			case "-e":		//Pro shortkey
				return CommandKey.UPDATE;
			case "delete":	//default command
			case "del": 	//user shortkey
			case "-d":		//pro shortkey
				return CommandKey.DELETE;
			case "undo":	//default command
			case "-u":		//pro shortkey
				return CommandKey.UNDO;
			case "redo":	//default command
			case "-r" :		//pro shortkey
				return CommandKey.REDO;
			case "complete"://default command
			case "done":	//alternate name
			case "-c":		//pro shortkey
				return CommandKey.COMPLETE;
			case "uncomplete"://default command
			case "undone":	//alternate name
			case "-uc":		//pro shortkey
				return CommandKey.UNCOMPLETE;
			case "search":	//default command
			case "-s" :		//pro shortkey
				return CommandKey.SEARCH;
			case "clear":	//default command
			case "-z":		//pro shortkey
				return CommandKey.CLEAR;
			default:
				return CommandKey.INVALID;
		}
	}
	
	
}
