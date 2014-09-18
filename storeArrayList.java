import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/** this class is used to store a tasklist on local disk.
 * 
 * It uses the build-in serillization of arrayList as an object on disk.
 * NO extra libraries are needed.
 * 
 * To use the data storage - copy and paste the two method into our Indigo Program.
 * 
 * @author jjlu
 *
 */

public class storeArrayList {
	private static final String FILE_NAME = "myData";
	
	private static ArrayList<String> tasks = new ArrayList<String>();
	
	public static void main(String[] Args){
		serializeArrayList (tasks);
		deserializeArrayList();
		System.out.println("This is end of program.");
	}
	
	public static void serializeArrayList (ArrayList<String> data){
		data.add("aaa");
		data.add("bbb");
		data.add("ccc");
		try {
			FileOutputStream fos = new FileOutputStream(FILE_NAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(data);
			oos.close();
			fos.close();
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public static void deserializeArrayList (){
		ArrayList<String> taskList = new ArrayList<String>();
		try{
			FileInputStream fis = new FileInputStream(FILE_NAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			if (obj instanceof ArrayList<?>){
				ArrayList<?> al = (ArrayList<?>) obj;
			if (al.size() > 0){
				for (int i = 0; i < al.size(); i++){
					Object o = al.get(i);
					if (o instanceof String){
						taskList.add((String) o);
					}
				}
			
			}
			ois.close();
			fis.close();
			}
		} catch (IOException ioe){
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c){
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}
		for (String tmp: taskList){
			System.out.println(tmp);
		}
	
	}
}
