package indigoSrc;
/** This class is used for undo operations.
 *  It store all the parser/Commands into a list with a pointer indicating the current position.
 *  A parser needs to be added for any normal command executed other than redo/undo.
 *  The class requires the support from Parser.
 *  
 *  API:
 *  ParserList()
 *  Parser undo()
 *  Parser redo()
 * 
 * 	todo:
 * 	perform safety check if there are more undo/redo operations than the list size
 *  Limit the size of list to save space
 */

import java.util.LinkedList;

public class UndoList {
	private static LinkedList<Parser> uList;
	private static int currentPos;
	private static LinkedList<Parser> redoList;
	private static int currentRedoPos;
	
	public UndoList(){
		uList = new LinkedList<Parser>();
		redoList = new LinkedList<Parser>();
		currentPos = -1;
		currentRedoPos = -1;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UndoList myList= new UndoList();
		myList.push(new Parser("add catch a dog"));
		myList.push(new Parser("add catch a cat"));
		myList.push(new Parser("add catch a fish"));
		System.out.println(myList.undo().getCommand());
		System.out.println(myList.undo().getCommand());
		System.out.println(myList.redo().getCommand());
	}
	
	public void push(Parser newParser){
		clear(uList);
		uList.add(newParser);
		currentPos++;
	}
	
	public void pushRedo(Parser newParser){
		clear(redoList);
		redoList.add(newParser);
		currentPos++;
	}
	
/*	public void push(COMMAND_KEY key, int editIndex, FloatingTask toDo){
		clear();
		tasks.add(toDo);
		switch (key){
			case CREATE:
				
			case UPDATE:
			case DELETE:
				Delete
			case COMPLETE:
			case UNCOMPLETE:
			default:
				
		}
	}
*/
	public void clear(LinkedList<Parser> list){
		int i = currentPos+1;
		while (i<list.size()){
			list.remove(i);
		}
	}
	
	public Parser undo(){
		currentPos--;
		return uList.get(currentPos + 1);
	}
	
	public Parser redo(){
		currentRedoPos--;
		currentPos++;
		return redoList.get(currentPos + 1);
	}
	
	public boolean isUndoAble(){
		return currentPos >= 0;
	}
	
	public boolean isRedoAble(){
		return currentRedoPos >= 0;
	}
}