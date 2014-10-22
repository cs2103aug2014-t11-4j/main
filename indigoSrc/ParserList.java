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


public class ParserList {
	private static LinkedList<Parser> parserList;
	private static int currentPos;
	
	public ParserList(){
		parserList = new LinkedList<Parser>();
		currentPos = -1;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ParserList myList= new ParserList();
		myList.push(new Parser("add catch a dog"));
		myList.push(new Parser("add catch a cat"));
		myList.push(new Parser("add catch a fish"));
		System.out.println(myList.undo().getCommand());
		System.out.println(myList.undo().getCommand());
		System.out.println(myList.redo().getCommand());
	}
	
	public void push(Parser newParser){
		clear();
		parserList.add(newParser);
		currentPos++;
	}

	public void clear(){
		int i = currentPos+1;
		while (i<parserList.size()){
			parserList.remove(i);
		}
	}
	
	public Parser undo(){
		currentPos--;
		return parserList.get(currentPos+1);
	}
	
	public Parser redo(){
		currentPos++;
		return parserList.get(currentPos);
	}
	
	public boolean isUndoAble(){
		return currentPos >= 0;
	}
}
