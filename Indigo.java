import java.util.*;

public class Indigo{
	
    public final static String WELCOME_MESSAGE = new String("Welcome to Indigo,"
    		+ " where you get tasks from to-do to done!");     
    public static Scanner input = new Scanner(System.in);
    
    public static void main(String[]args) {         
    	System.out.println(WELCOME_MESSAGE);     
    	System.out.println("To test you are not a robot: What is 1 + 1 in binary?");
    	if(input.nextInt() == 10){
    		System.out.println("Yes! You are human! (Or not)");
    	} else {
    		System.out.println("No, you are a robot! GoodBye!");
    	}
    	
    } 
}
