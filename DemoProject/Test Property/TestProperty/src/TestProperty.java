import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class TestProperty {

	public static void main(String[] args) throws IOException {
		Properties prop = new Properties();
//		InputStream in = new BufferedInputStream (new FileInputStream("a.properties"));
//		prop.load(in);
//		Iterator<String> it=prop.stringPropertyNames().iterator();
//		while(it.hasNext()){
//			String key=it.next();
//			System.out.println(key+":"+prop.getProperty(key));
//		}
//		
//		in.close();
		
		FileOutputStream oFile = new FileOutputStream("b.properties", true);
		prop.setProperty("hello", "1");
		prop.setProperty("world", "2");
		prop.store(oFile, "The New properties file");
		 oFile.close();
	}
}
