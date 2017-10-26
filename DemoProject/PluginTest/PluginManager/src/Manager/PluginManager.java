package Manager;

import java.util.Dictionary;
import java.util.Hashtable;

import CLI.Plugin;

public class PluginManager {
	private volatile static PluginManager manager;
	private PluginClassLoader pluginClassLoader;
	private volatile boolean init;

	private PluginManager()
	{
	}
	
	public static PluginManager getManager() {
		if (manager == null) {
			synchronized (PluginManager.class) {
				if (manager == null)
					manager = new PluginManager();
			}
		}
		
		return manager;
	}
	
//	public <T> T getPlugin(String className, Class<T> required) {
//		Class<?> cls = null;
////		try {
////			cls = pluginClassLoader.loadClass(className);
////		}
//	}
}
