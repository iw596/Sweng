import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Read_txt {
	
	// holds whats in the doc.txt your reading from
	private String doc;
	// the file path + name 
	private File file;
	
	public Read_txt(File file) {
		
		// set the file path + name
		this.file = file;
		
		try {
			// read from the file and place content in to "doc" string
			setDoc(read());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String read() throws FileNotFoundException{
		
		// get file content and place in BufferedReader class
		BufferedReader br = new BufferedReader(new FileReader(this.file));
		
		// local variables
		// the string of the currant line
		String st;
		// the whole content of the file in a string with \n to separate each line
		String out = "";
		
		try {
			// read while there is still something there
			while((st = br.readLine()) != null) {
				// add the read line to the whole string with \n to separate each line in the string
				out = out + st + "\n"; 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TEST //
		// print what has been read from the file
		// System.out.println(out);
		
		return out;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}
}
