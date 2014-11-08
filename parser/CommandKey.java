package parser;

/**This is the enum class together with the identifier method of commandKey.
 * This class identifies the possible words for commands and return a commandKey type
 * to the class which invoked it.
 * @author KenHua
 *
 */
public enum CommandKey {
	CREATE, READ, UPDATE, DELETE, UNDO, COMPLETE, UNCOMPLETE, REDO, SEARCH, CLEAR, INVALID;

	public static CommandKey identifyKey(String keyCommand){
		switch(keyCommand){
			case "add":		//default command
			case "addd":	//Spelling mistake
			case "create":	//alternate name
			case "-a":		//Pro short key
				return CommandKey.CREATE;
			case "view":	//default command
			case "-v":		//pro short key
			case "display": //Alternate name
			case "veiw":	//spelling mistake
				return CommandKey.READ;
			case "edit":	//default command
			case "update": 	//alternate name
			case "-e":		//Pro short key
				return CommandKey.UPDATE;
			case "delete":	//default command
			case "remove": 	//alternate name
			case "del": 	//user short key
			case "-d":		//pro short key
				return CommandKey.DELETE;
			case "undo":	//default command
			case "-u":		//pro short key
				return CommandKey.UNDO;
			case "redo":	//default command
			case "-r" :		//pro short key
				return CommandKey.REDO;
			case "complete"://default command
			case "did":		//alternate name
			case "com":		//user short key
			case "-c":		//pro short key
				return CommandKey.COMPLETE;
			case "uncomplete"://default command
			case "undid":	//alternate name
			case "uncom":	//user short key
			case "-uc":		//pro short key
				return CommandKey.UNCOMPLETE;
			case "search":	//default command
			case "-s" :		//pro short key
				return CommandKey.SEARCH;
			case "clear":	//default command
			case "-z":		//pro short key
			case "clr":		//user short key
				return CommandKey.CLEAR; //Very specific. Must delete all.
			default:
				return CommandKey.INVALID;
		}
	}
	
	//List down all the valid commands which can stand alone.
	public boolean checkValidAlone(){
		if(this.equals(READ) || this.equals(CLEAR) || this.equals(UNDO) || this.equals(REDO)){
			return true;
		}
		return false;
	}	
}
