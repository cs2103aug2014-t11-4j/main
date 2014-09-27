/** This a main program of Indigo.
 *  Indigo is a software that can store, process and display tasks on the desktop as a task manager.
 *  Indigo also supports audio remainder, auto-correction, and color-coded highlighting.
 *  Indigo takes input from keyboard only.
 *  Indigo stores data on a local disk.
 * @author jjlu
 *
 */
public class IndigoMain {

	public static void main(String[] args) {
		/* outline:
		 * 1.welcome message
		 * 2.check if there is local disk storage
		 * -yes load data
		 * -no display beginner tutorial message
		 * 3.asks for user command
		 * 4.process user command
		 * 5.repeating 3-4 until exit
		 */

		displayWelcomeMessage();
		
		if (isDataPresent()){
			loadData();
		} else {
			displayTutorialMessage();
		}
		
		while (true){
			readCommand();
		} 
	}

	private static void readCommand() {
		/* TODO read a command from user and process it
		 * parser
		 * execute Command
		 */		
	}

	private static void displayTutorialMessage() {
		// TODO ask user if want to enter tutorial mode
		
	}

	private static void loadData() {
		// TODO load data from the local disk into memory
		
	}

	private static boolean isDataPresent() {
		// TODO check if there is data on the disk
		return false;
	}

	private static void displayWelcomeMessage() {
		// TODO display welcomeMessage
		
	}

}
