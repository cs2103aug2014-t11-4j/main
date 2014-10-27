package indigoSrc;
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
			case "add":
				return COMMAND_KEY.CREATE;
			case "view":
				return COMMAND_KEY.READ;
			case "edit":
				return COMMAND_KEY.UPDATE;
			case "delete":
				return COMMAND_KEY.DELETE;
			case "undo":
				return COMMAND_KEY.UNDO;
			case "redo":
				return COMMAND_KEY.REDO;
			case "complete":
				return COMMAND_KEY.COMPLETE;
			case "uncomplete":
				return COMMAND_KEY.UNCOMPLETE;
			default:
				return COMMAND_KEY.READ;
		}
	}
	
	public COMMAND_KEY getKey(){
		return this.key;
	}
}
