package main;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "Testplan"; //$NON-NLS-1$
	/** Base tracing option for this plugin */
    public static final boolean DEBUG = Boolean.parseBoolean(Platform.getDebugOption("org.eclipse.cdt.tests.dsf.gdb/debug"));  //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	private static BundleContext fgBundleContext;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext )
	 */
	public void start(BundleContext context) throws Exception {
		fgBundleContext = context;
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext )
	 */
	public void stop(BundleContext context) throws Exception {
		try {
			// Stop Command Manager Job
			// CommandManagerJob.getInstance().cancel();
		} finally {
			plugin = null;
			super.stop(context);
			fgBundleContext = null;
		}
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public static BundleContext getBundleContext() {
		return fgBundleContext;
	}
	
	public static int massageTimeout(int timeoutMs) {
		String prop = System.getProperty("dsf.gdb.tests.timeout.multiplier");
		if (prop == null || prop.length() == 0) {
			return timeoutMs;
		}
		
		try {
			float multiplier = Float.valueOf(prop);
			return (int)(timeoutMs * multiplier);
		}
		catch (NumberFormatException exc) {
			return timeoutMs;
		}
	}
	
	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
    /**     
     * Convenience method which returns the unique identifier of this plugin.
     */    
    public static String getUniqueIdentifier() {
    	return getDefault().getBundle().getSymbolicName();    
    }
}

