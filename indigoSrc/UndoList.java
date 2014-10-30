package indigoSrc;
/** This class is used for undo and redo operations.
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
	private static LinkedList<Parser> undoList;
	private static int currentPos;
	private static LinkedList<Parser> redoList;
	
	public UndoList(){
		undoList = new LinkedList<Parser>();
		redoList = new LinkedList<Parser>();
		currentPos = -1;
	}
	
	public void push(Parser newParser, Parser oldParser){
		clear();
		undoList.add(newParser);
		redoList.add(oldParser);
		currentPos++;
	}
	
	public void clear(){
		int i = currentPos+1;
		while (i<undoList.size()){
			undoList.remove(i);
			redoList.remove(i);
		}
	}
	
	public Parser undo(){
		currentPos--;
		return undoList.get(currentPos + 1);
	}
	
	public Parser redo(){
		currentPos++;
		return redoList.get(currentPos);
	}
	
	public boolean isUndoAble(){
		return currentPos >= 0;
	}
	
	public boolean isRedoAble(){
		return currentPos >= 0;
	}
}