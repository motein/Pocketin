package mainLoader;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import printlnInterface.MyActionInterface;

public class MyActionMain {
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		System.out.println(MyActionMain.class.getResource("myActionPlug-in.jar"));
		System.out.println(MyActionMain.class.getResource("/"));
		
		 URL url = MyActionMain.class.getResource("myActionPlug-in.jar");
		 URLClassLoader loader = new URLClassLoader(new URL[] { url });
		 
		 Class<?> clazz = loader.loadClass("myActionPlug.MyAction");
		 if (clazz == null) {
			 loader.close();
			 return;
		 }
		 
		 Object obj = clazz.newInstance();
		 MyActionInterface mai = (MyActionInterface) obj;
		 mai.print();
		 
		 loader.close();
	 }
}
