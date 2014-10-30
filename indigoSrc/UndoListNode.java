package indigoSrc;

public class UndoListNode {
	private COMMAND_KEY key;
	private COMMAND_KEY invertKey;
	private FloatingTask task;
	
	public void setKey(COMMAND_KEY key){
		this.key = key;
	}
	
	public void setInvertKey(COMMAND_KEY key){
		invertKey = keyInverter(key);
	}

	public COMMAND_KEY keyInverter(COMMAND_KEY key) {
		switch(key){
			case CREATE:
				return COMMAND_KEY.DELETE;
			case DELETE:
				return COMMAND_KEY.CREATE;
			case UPDATE:
				return COMMAND_KEY.UPDATE;
			case COMPLETE:
				return COMMAND_KEY.UNCOMPLETE;
			case UNCOMPLETE:
				return COMMAND_KEY.COMPLETE;
			default:
				//This is a dummy. A functional program will not come here.
				return COMMAND_KEY.READ;
		}
	}
	
	public void setTask(FloatingTask task){
		this.task = task;
	}
	
	public UndoListNode(COMMAND_KEY key, FloatingTask task){
		setKey(key);
		setInvertKey(key);
		setTask(task);
	}
	
	public COMMAND_KEY getKey(){
		return key;
	}
	
	public COMMAND_KEY getInvertKey(){
		return invertKey;
	}
	
	public FloatingTask getTask(){
		return task;
	}
}
