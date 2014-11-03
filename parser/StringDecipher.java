package parser;

import java.util.ArrayList;
import java.util.Arrays;

public class StringDecipher {
	private String[] remaining;
	private int wordsLeft;
	
	public StringDecipher(String[] sentence){
		remaining = sentence;
		wordsLeft = sentence.length;
	}
	
	public String[] getRemaining(){
		return remaining;
	}
	
	public int getWordsLeft(){
		return wordsLeft;
	}
	
	public CommandKey getKey(){
		ArrayList<String> sentence = new ArrayList<String>(Arrays.asList(remaining));
		int arraySize = sentence.size();
		CommandKey key = CommandKey.identifyKey(sentence.get(0));
		if(key.equals(CommandKey.INVALID)){
			key = CommandKey.identifyKey(sentence.get(arraySize-1));
			if(key.equals(CommandKey.INVALID)){
				key = CommandKey.CREATE;
			} else {
				sentence.remove(arraySize-1);
			}
		} else {
			sentence.remove(0);
		}
		
		remaining = sentence.toArray(new String[sentence.size()]);
		wordsLeft = remaining.length;
		return key;
	}

}
