package logic;

import indigoSrc.Parser;
import indigoSrc.TaskList;
/**This is the Command abstract class used in the Logic component implementing the 
 * Command of users.
 * @author KenHua
 */

public abstract class CommandClass {
	protected Parser parserVar; 
	protected static TaskList taskListVar = new TaskList();
	public boolean isValid = true;
	
	public abstract String execute();
	public abstract String undo();
	
}