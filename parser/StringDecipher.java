package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

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
	
	public String remainingToString(){
		String printup = "";
		for(int i=0; i<wordsLeft; i++){
			printup += remaining.get(i) + " ";
		}
		return printup;
	}

	public Stack<Integer> extractIndices() {
		Stack<Integer> indices = new Stack<Integer>();
		if(getWordsLeft()==0){
			return null;
		}else{
			int instances = getWordsLeft();
			int i = 0;
			int count=0;
			while(i < instances){
				try{
					indices.push(Integer.parseInt(remaining.get(i)));
					count++;
				}catch(Exception err){
				}
				i++;
			}
			if(count!=instances){ //That means not all the input is number.
				return null;
			}
		}
		remaining.clear();
		wordsLeft = 0;
		return indices;
	}

}
