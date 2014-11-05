package parser;
public enum CommandKey {
	CREATE, READ, UPDATE, DELETE, UNDO, COMPLETE, UNCOMPLETE, REDO, SEARCH, CLEAR, INVALID;

	public static CommandKey identifyKey(String keyCommand){
		switch(keyCommand){
			case "add":		//default command
			case "addd":	//Spelling mistake
			case "create":	//default
			case "-a":		//Pro shortkey
				return CommandKey.CREATE;
			case "view":	//default command
			case "-v":		//pro shortkey
			case "display": //Alternate name
			case "veiw":	//spelling mistake
				return CommandKey.READ;
			case "edit":	//default command
			case "update": 	//
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
			case "did":	//alternate name
			case "com":	//user shortkey
			case "-c":		//pro shortkey
				return CommandKey.COMPLETE;
			case "uncomplete"://default command
			case "undid":	//alternate name
			case "uncom":	//user shortkey
			case "-uc":		//pro shortkey
				return CommandKey.UNCOMPLETE;
			case "search":	//default command
			case "-s" :		//pro shortkey
				return CommandKey.SEARCH;
			case "clear":	//default command
			case "-z":		//pro shortkey
			case "clr":		//user shortkey
				return CommandKey.CLEAR; //Very specific. Must delete all.
			default:
				return CommandKey.INVALID;
		}
	}
	
	//List down all the valid commands which can stand alone.
	public boolean checkValidAlone(){
		if(this.equals(READ) || this.equals(CLEAR) || this.equals(UNDO) || this.equals(REDO) /*||
			this.equals(DELETE) || this.equals(COMPLETE) || this.equals(UNCOMPLETE)*/){
			return true;
		}
		return false;
	}	
}
