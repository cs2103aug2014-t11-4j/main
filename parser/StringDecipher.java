package parser;
/**This class takes in a string array and decipher it. It will identify the key command words,
 * the indices of input and the task indentifiers.
 * This class is made of the remaining words in the array and the number of words left.
 * 
 * @author KenHua
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class StringDecipher {
	private ArrayList<String> remaining;
	private int wordsLeft;
	
	public StringDecipher(String[] sentence){
		remaining = new ArrayList<String>(Arrays.asList(sentence));
		wordsLeft = sentence.length;
	}
	
	public String[] getRemaining(){
		String[] sentence = remaining.toArray(new String[wordsLeft]);
		return sentence;
	}
	
	public int getWordsLeft(){
		return wordsLeft;
	}
	
	//Gets the command key word. It extracts either the first or last word depending on 
	//the command ket word's position.
	public CommandKey getKey(){
		int arraySize = wordsLeft;
		CommandKey key = CommandKey.identifyKey(remaining.get(0).toLowerCase());
		if(key.equals(CommandKey.INVALID)){
			key = CommandKey.identifyKey(remaining.get(arraySize-1).toLowerCase());
			if(key.equals(CommandKey.INVALID)){
				key = CommandKey.CREATE;
			} else {
				remaining.remove(arraySize-1);
			}
		} else {
			remaining.remove(0);
		}
		
		wordsLeft = remaining.size();
		return key;
	}
	
	//Gets the index from the sentence. It takes the second position if the key
	//command is at the first position or the first position if the key command
	//is at the last position.
	public int getIndex(){
		int index = -1;
		try{
			 index = Integer.parseInt(remaining.get(0));
			 remaining.remove(0);
		} catch(NumberFormatException er){
		}
		this.wordsLeft = remaining.size();
		return index;
	}
	
	//Identifies the key word is the following commands are identified:
	//Delete, complete, uncomplete, undo, redo, and view.
	public TaskIdentifiers checkTaskWords(CommandKey keyWord) {
		TaskIdentifiers taskWord = TaskIdentifiers.INVALID;
		assert this.getWordsLeft() >=0 && this.getWordsLeft() <=2;
		if(this.getWordsLeft() == 0){
			if(keyWord.equals(CommandKey.READ)){
				taskWord = TaskIdentifiers.ALL;
			}
		} else if(this.getWordsLeft() == 1){
			String word = remainingToString().toLowerCase().trim();
			taskWord = TaskIdentifiers.indentifyWords(word);
		} else if(this.getWordsLeft() == 2){
			TaskIdentifiers all = TaskIdentifiers.indentifyWords(remaining.get(0).toLowerCase().trim());
			if(all.equals(TaskIdentifiers.ALL)){
				taskWord = TaskIdentifiers.indentifyWords(remaining.get(1).toLowerCase().trim());
			}
		}
		if(!taskWord.equals(TaskIdentifiers.INVALID)){
			remaining.clear();
			wordsLeft = 0;
		}
		return taskWord;
	}
	
	//Returns the remaining words after deciphering.
	public String remainingToString(){
		String printup = "";
		for(int i=0; i<wordsLeft; i++){
			printup += remaining.get(i) + " ";
		}
		return printup;
	}

	//Find indices input by user. This method is invoked by command keys:
	//Delete, complete and uncomplete.
	public ArrayList<Integer> extractIndices() {
		ArrayList<Integer> indices = new ArrayList<Integer>();
		if(getWordsLeft()==0){
			return null;
		}else{
			int instances = getWordsLeft();
			int i = 0;
			int count=0;
			while(i < instances){
				try{
					indices.add(Integer.parseInt(remaining.get(i)));
					count++;
				}catch(Exception err){
				}
				i++;
			}
			if(count!=instances){ //That means not all the input is number.
				return null;
			}
		}
		Collections.sort(indices);
		remaining.clear();
		wordsLeft = 0;
		return indices;
	}

}
