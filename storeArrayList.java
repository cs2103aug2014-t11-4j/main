import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;




/** this class is used to store a arraylist<Task> on local disk, as a text file.
 * 
 * NO extra libraries are needed.
 * Currently only the floating task is supported.
 * 
 * API:
 * String store.saveFile(ArrayList<Task>, String fileName) save the arraylist into myData.txt
 * String store.readFile(ArrayList<Task>, String fileName) retrieve the arraylist from myData.txt
 * @return system message
 * 
 * @author jjlu
 * 
 * Instructions for testing:
 * run main method to test.
 *
 */

public class storeArrayList {
	
	public static void main(String[] Args){
		ArrayList<Task> testList= new ArrayList<Task>();
		testList.add(new Task());
		testList.add(new Task());
		testList.add(new Task());
		System.out.println(saveFile(testList, "savedData"));
		
		ArrayList<Task> testList2 = new ArrayList<Task>();
		System.out.println(readFile(testList2, "savedData"));
		System.out.println(saveFile(testList2, "savedData2"));
		
		System.out.println("This is end of program.");
	}
	
	/** this method takes in an tasklist and fileName
	 *  it will overwrite the file if the file already exists
	 * 
	 * @param taskList
	 * @param fileName
	 * @return file is saved or list is empty
	 */	
	public static String saveFile (ArrayList<Task> taskList, String fileName){
		if (taskList.size() > 0){
			String fileContent = "";
			for (int i=0; i<taskList.size();i++){
				fileContent = fileContent + Integer.toString(i+1) + ". " + taskList.get(i).toString() + System.lineSeparator();
			}
			//System.out.print(fileContent);
			BufferedWriter writer = null;
			try
			{
			    writer = new BufferedWriter( new FileWriter(fileName));
			    writer.write(fileContent);

			}
			catch ( IOException e)
			{
			}
			finally
			{
			    try
			    {
			        if ( writer != null)
			        writer.close( );
			    }
			    catch ( IOException e)
			    {
			    	return "IOException";
			    }
			}
			return (fileContent);
		}	else {
			return("Empty array.");
		}
		
	}
	
	public static String readFile (ArrayList<Task> taskList, String fileName){
		if (taskList == null){
			return "null pointer exception.";
		}
		//String existingFileContent = "";
		BufferedReader reader = null;
		try
		{
		    reader = new BufferedReader( new FileReader(fileName));
		    String line = reader.readLine();
		    while (line != null){
		    	//System.out.print(line);
		    	//existingFileContent = existingFileContent + line.substring(3) + System.lineSeparator();
				//System.out.print(existingFileContent);
		    	taskList.add(new Task(line.substring(3)));
		    	line = reader.readLine();
		    }

		}
		catch ( IOException e)
		{
			return fileName + "is not found.";
		}
		finally
		{
		    try
		    {
		        if ( reader != null)
		        reader.close( );
		    }
		    catch ( IOException e)
		    {
		    	return "IOException";
		    }
		}
		return fileName + " is loaded.";
	}
	
}
