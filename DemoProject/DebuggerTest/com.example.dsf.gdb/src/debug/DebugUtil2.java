package debug;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.dsf.gdb.IGDBLaunchConfigurationConstants;
import org.eclipse.cdt.dsf.gdb.internal.GdbDebugOptions;
import org.eclipse.cdt.dsf.gdb.internal.GdbPlugin;
import org.eclipse.cdt.dsf.gdb.launching.GdbLaunch;
import org.eclipse.cdt.dsf.gdb.service.command.IGDBControl;
import org.eclipse.cdt.dsf.mi.service.command.events.MIStoppedEvent;
import org.eclipse.cdt.dsf.service.DsfServicesTracker;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.cdt.dsf.service.DsfSession.SessionStartedListener;
import org.eclipse.cdt.utils.spawner.ProcessFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.util.Assert;
import org.tests.dsf.gdb.framework.ServiceFactoriesManager;

import com.example.dsf.gdb.event.SessionEventListener;
import com.example.dsf.gdb.tests.ITestConstants;

import main.Activator;

public class DebugUtil2 {
	
	/*
	 * Path to executable
	 */
	protected static final String EXEC_PATH = "D:/Workspace/runtime-EclipseApplication/TestDll/Debug";
	
	public static final String ATTR_DEBUG_SERVER_NAME = Activator.PLUGIN_ID + ".DEBUG_SERVER_NAME";
	private static final String DEFAULT_EXEC_NAME = "libTestDll.dll";
	
	private static GdbLaunch fLaunch;
	
	// The set of attributes used for the launch of a single test.
	private Map<String, Object> launchAttributes;
	
	// The launch configuration generated from the launch attributes
	private ILaunchConfiguration fLaunchConfiguration;
	
	// A set of global launch attributes which are not
	// reset when we load a new class of tests.
	// This allows a SuiteGdb to set an attribute
	// The suite is responsible for clearing those attributes
	// once it is finished
	private static Map<String, Object> globalLaunchAttributes = new HashMap<String, Object>();
	private static Process gdbserverProc;
	
	/** The MI event associated with the breakpoint at main() */
	private MIStoppedEvent fInitialStoppedEvent;
	
	// Provides the possibility to override the Debug Services factory and
	// override specific service(s)
	private static ServiceFactoriesManager fTestDebugServiceFactoriesMgr = new ServiceFactoriesManager();
		
	protected void setLaunchAttributes() {
    	// Clear all launch attributes before starting a new test
    	launchAttributes = new HashMap<String, Object>();

   		launchAttributes.put(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, EXEC_PATH + DEFAULT_EXEC_NAME);

		launchAttributes.put(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN, true);
		launchAttributes.put(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN_SYMBOL, ICDTLaunchConfigurationConstants.DEBUGGER_STOP_AT_MAIN_SYMBOL_DEFAULT);
		launchAttributes.put(IGDBLaunchConfigurationConstants.ATTR_GDB_INIT, ".gdbinit");

    	
    	launchAttributes.put(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE, ICDTLaunchConfigurationConstants.DEBUGGER_MODE_RUN );
    	

		// Set these up in case we will be running Remote tests.  They will be ignored if we don't
    	launchAttributes.put(ATTR_DEBUG_SERVER_NAME, "gdbserver");
    	launchAttributes.put(IGDBLaunchConfigurationConstants.ATTR_REMOTE_TCP, true);
    	launchAttributes.put(IGDBLaunchConfigurationConstants.ATTR_HOST, "localhost");
    	launchAttributes.put(IGDBLaunchConfigurationConstants.ATTR_PORT, "9999");
    	launchAttributes.put(ITestConstants.LAUNCH_GDB_SERVER, true);

    	initializeLaunchAttributes();
 
    	// Set the global launch attributes
    	launchAttributes.putAll(globalLaunchAttributes);
    }
	
	/**
	 * Override this method to initialize test specific launch attributes.
	 * Use {@link #setLaunchAttribute(String, Object)} method to set them.
	 * Don't need to clean it up its local to a specific test method.
	 * Note that global attributes will override these values.
	 * If it is undesired override {@link #setLaunchAttributes()} method instead
	 */
	protected void initializeLaunchAttributes() {
	   	setGdbVersion();
	}
	
 	protected void setGdbVersion() {
 		// Leave empty for the base class
 	}
 	
	public void doBeforeTest() throws Exception {
		setLaunchAttributes();
		doLaunch();
	}
	
	public void doAfterTest() throws Exception {
		if (fLaunch != null) {
			fLaunch.terminate();
			assertLaunchTerminates();
			fLaunch = null;
		}
		removeAllPlatformBreakpoints();
	}
	
	/**
	 * Make sure we are starting with a clean/known state. That means no
	 * platform breakpoints that will be automatically installed.
	 */
	public void removeAllPlatformBreakpoints() throws CoreException {
		IBreakpointManager manager = DebugPlugin.getDefault().getBreakpointManager();
		IBreakpoint[] breakpoints = manager.getBreakpoints();
		manager.removeBreakpoints(breakpoints, true);
	}

 	
 	/**
     * Launch GDB.  The launch attributes must have been set already.
     */
 	protected void doLaunch() throws Exception {
 		boolean remote = isRemoteSession();
 		
    	if (GdbDebugOptions.DEBUG) {
    		GdbDebugOptions.trace("===============================================================================================\n");
    	}
    	
    	GdbDebugOptions.trace(String.format("%s \"%s\" requesting %s%s",
    			GdbPlugin.getDebugTime(), "say_Hello", launchAttributes.get(IGDBLaunchConfigurationConstants.ATTR_DEBUG_NAME), remote ? " with gdbserver." : ".")
    			, -1);
    	
    	if (GdbDebugOptions.DEBUG) {
    		GdbDebugOptions.trace("\n===============================================================================================\n");
    	}
    	
    	launchGdbServer();
    	
    	ILaunchManager launchMgr = DebugPlugin.getDefault().getLaunchManager();
    	ILaunchConfigurationType lcType = launchMgr.getLaunchConfigurationType("org.tests.dsf.gdb.TestLaunch");
    	assert lcType != null;
    	
    	ILaunchConfigurationWorkingCopy lcWorkingCopy = lcType.newInstance(
 				null,
 				launchMgr.generateLaunchConfigurationName("TestDll")); //$NON-NLS-1$
    	
    	assert lcWorkingCopy != null;
    	lcWorkingCopy.setAttributes(launchAttributes);
    	fLaunchConfiguration = lcWorkingCopy.doSave();
    	fLaunch = doLaunchInner();
    	
    	validateGdbVersion(fLaunch);
    	
    	// If we started a gdbserver add it to the launch to make sure it is killed at the end
 		if (gdbserverProc != null) {
            DebugPlugin.newProcess(fLaunch, gdbserverProc, "gdbserver");
 		}

 		// Now initialize our SyncUtility, since we have the launcher
 		SyncUtil.initialize(fLaunch.getSession());
 	}
 	
 	public boolean isRemoteSession() {
		return launchAttributes.get(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE)
	              .equals(IGDBLaunchConfigurationConstants.DEBUGGER_MODE_REMOTE);
    }
 	
 	/**
 	 * This method start gdbserver on the localhost.
 	 * If the user specified a different host, things won't work.
 	 */
 	private void launchGdbServer() {
 		// First check if we should not launch gdbserver even for a remote session
 		if (launchAttributes.get(ITestConstants.LAUNCH_GDB_SERVER).equals(false)) {
 			if (GdbDebugOptions.DEBUG) GdbDebugOptions.trace("Forcing to not start gdbserver for this test\n");
 			return;
 		}
 		
 		if (isRemoteSession()) {
 			if (launchAttributes.get(IGDBLaunchConfigurationConstants.ATTR_REMOTE_TCP).equals(Boolean.TRUE)) {
 				String server = (String)launchAttributes.get(ATTR_DEBUG_SERVER_NAME);
 				String port = (String)launchAttributes.get(IGDBLaunchConfigurationConstants.ATTR_PORT);
 				String program = (String)launchAttributes.get(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME);
 				String commandLine = server + " :" + port + " " + program;
 				
 				try {
 					if (GdbDebugOptions.DEBUG)
 						GdbDebugOptions.trace("Starting gdbserver with command: " + commandLine + "\n");

 					gdbserverProc = ProcessFactory.getFactory().exec(commandLine);
                    Reader r = new InputStreamReader(gdbserverProc.getErrorStream());
                    BufferedReader reader = new BufferedReader(r);
                    String line;
                    while ((line = reader.readLine()) != null) {
                    	if(GdbDebugOptions.DEBUG) GdbDebugOptions.trace(line + "\n");
                        line = line.trim();
                        if (line.startsWith("Listening on port")) {
                            break;
                        }
                    }
 				} catch (Exception e) {
 					GdbDebugOptions.trace("Error while launching command: " + commandLine + "\n");
 					e.printStackTrace();
 					assert false;
 				}
 			}
 		}
 	}

 	/**
 	 * Perform the actual launch. This is normally called by {@link #doLaunch()}, however
 	 * it can be called repeatedly after an initial doLaunch sets up the environment. Doing
 	 * so allows multiple launches on the same launch configuration to be created. When this
 	 * method is called directly, the returned launch is not tracked and it is up to the
 	 * individual test to cleanup the launch. If the launch is not cleaned up, subsequent
 	 * tests will fail due to checks in {@link #doBeforeTest()} that verify state is clean
 	 * and no launches are currently running.
 	 * 
 	 * This method is blocking until the breakpoint at main in the program is reached.
 	 * 
 	 * @return the new launch created
 	 */
 	protected GdbLaunch doLaunchInner() throws Exception {
 		
 		boolean postMortemLaunch = launchAttributes.get(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE)
                .equals(ICDTLaunchConfigurationConstants.DEBUGGER_MODE_CORE);
 		
 		SessionEventListener sessionEventListener = new SessionEventListener(fLaunchConfiguration);
 		SessionStartedListener sessionStartedListener = new SessionStartedListener() {
			@Override
			public void sessionStarted(DsfSession session) {
				sessionEventListener.setSession(session);
				session.addServiceEventListener(sessionEventListener, null);
			}
		};
		
		// Launch the debug session. The session-started listener will be called
		// before the launch() call returns (unless, of course, there was a
		// problem launching and no session is created).
 		DsfSession.addSessionStartedListener(sessionStartedListener);
 		GdbLaunch launch = (GdbLaunch)fLaunchConfiguration.launch(ILaunchManager.DEBUG_MODE, new NullProgressMonitor());
 		if (!GdbDebugOptions.DEBUG) {
 			// Now that we have started the launch we can print the real GDB version
 			// but not if DEBUG is on since we get the version anyway in that case.
 			GdbDebugOptions.trace(String.format(" Launched gdb %s.\n", launch.getGDBVersion()));
 		}
 		
 		DsfSession.removeSessionStartedListener(sessionStartedListener);
 		
 		
 		try {

 	 		// If we haven't hit main() yet,
 	 		// wait for the program to hit the breakpoint at main() before
 			// proceeding. All tests assume that stable initial state. Two
 			// seconds is plenty; we typically get to that state in a few
 			// hundred milliseconds with the tiny test programs we use.
 			if (!postMortemLaunch) {
 				sessionEventListener.waitUntilTargetSuspended();
 	 		}

 	 		// This should be a given if the above check passes
 	 		if (!postMortemLaunch) {
 	 			synchronized(this) {
 	 				MIStoppedEvent initialStoppedEvent = sessionEventListener.getInitialStoppedEvent();
 	 				assert initialStoppedEvent != null;
 	 				
					if (fInitialStoppedEvent == null) {
						// On the very first launch we do, save the initial stopped event
						// XXX: If someone writes a test with an additional launch
						// that needs this info, they should resolve this return value then
						fInitialStoppedEvent = initialStoppedEvent;
					}
 	 			}
 	 		}
 	 		
 		} catch (Exception e) {
 			try {
 				launch.terminate();
 				assertLaunchTerminates(launch);
 			} catch (Exception inner) {
 				e.addSuppressed(inner);
 			}
 			throw e;
 		}
		
		return launch;
 	}
 	
	/**
	 * Assert that the launch terminates. Callers should have already
	 * terminated the launch in some way.
	 */
	protected void assertLaunchTerminates() throws Exception {
		GdbLaunch launch = fLaunch;
		assertLaunchTerminates(launch);
	}
 	
	protected void assertLaunchTerminates(GdbLaunch launch) throws InterruptedException {
		if (launch != null) {
			// Give a few seconds to allow the launch to terminate
			int waitCount = 100;
			while (!launch.isTerminated() && !launch.getDsfExecutor().isShutdown() && --waitCount > 0) {
				//Thread.sleep(TestsPlugin.massageTimeout(100));
				Thread.sleep(100);
			}
			
			Assert.isNotNull(launch.isTerminated(), "Launch failed to terminate before timeout");
		}
	}
	
	/**
	 * Validate that the gdb version launched is the one that was targeted.
	 * Will fail the test if the versions don't match.
	 * 
	 * @param launch The launch in which we can find the gdb version
	 */
	protected void validateGdbVersion(GdbLaunch launch) throws Exception {};
	
	/**
	 * @return A Test Debug Service Factories manager which allow individual tests to register
	 * a specific service factory which can then provide mocked/extended instances of Test Services
	 */
	public static ServiceFactoriesManager getServiceFactoriesManager() {
		return fTestDebugServiceFactoriesMgr;
	}
}
