package logic;


public class Command{
	private COMMAND_KEY key;
	
	public Command(){
		this("view");
	}
	
	public Command(String userInput){
		key = identifyKey(userInput);
	}
	
	public COMMAND_KEY identifyKey(String keyCommand){
		switch(keyCommand){
			case "add":		//default command
			case "addd":	//Spelling mistake
			case "-a":		//Pro shortkey
				return COMMAND_KEY.CREATE;
			case "view":	//default command
			case "-v":		//pro shortkey
			case "display": //Alternate name
			case "veiw":	//spelling mistake
				return COMMAND_KEY.READ;
			case "edit":	//default command
			case "-e":		//Pro shortkey
				return COMMAND_KEY.UPDATE;
			case "delete":	//default command
			case "del": 	//user shortkey
			case "-d":		//pro shortkey
				return COMMAND_KEY.DELETE;
			case "undo":	//default command
			case "-u":		//pro shortkey
				return COMMAND_KEY.UNDO;
			case "redo":	//default command
			case "-r" :		//pro shortkey
				return COMMAND_KEY.REDO;
			case "complete"://default command
			case "done":	//alternate name
			case "-c":		//pro shortkey
				return COMMAND_KEY.COMPLETE;
			case "uncomplete"://default command
			case "undone":	//alternate name
			case "-uc":		//pro shortkey
				return COMMAND_KEY.UNCOMPLETE;
			case "search":	//default command
			case "-s" :		//pro shortkey
				return COMMAND_KEY.SEARCH;
			case "clear":	//default command
			case "-z":		//pro shortkey
				return COMMAND_KEY.CLEAR;
			default:
				return COMMAND_KEY.CREATE;
		}
	}
	
	public COMMAND_KEY getKey(){
		return this.key;
	}
}
