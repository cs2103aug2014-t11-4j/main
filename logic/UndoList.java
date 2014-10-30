package logic;
/** This class is used for undo and redo operations.
 *  It store all the parser/Commands into a list with a pointer indicating the current position.
 *  A parser needs to be added for any normal command executed other than redo/undo.
 *  The class requires the support from Parser.

 * 	todo:
 * 	perform safety check if there are more undo/redo operations than the list size
 *  Limit the size of list to save space
 */

import java.util.LinkedList;

public class UndoList {
	private static int currentPos;
	private static LinkedList<CommandClass> list;

	public UndoList(){
		list = new LinkedList<CommandClass>();
		currentPos = -1;
	}

	public void push(CommandClass cc){
		clear();
		list.add(cc);
		currentPos++;
	}
	
	public void clear(){
		int i = currentPos+1;
		while (i < list.size()){
			list.remove(i);
		}
	}
	
	public CommandClass undo(){
		currentPos--;
		return list.get(currentPos + 1);
	}
	
	public CommandClass redo(){
		currentPos++;
		return list.get(currentPos);
	}
	
	public int size(){
		return list.size();
	}
	
	public boolean isUndoAble(){
		return currentPos >= 0;
	}
	
	public boolean isRedoAble(){
		return currentPos + 1 < size();
	}
}