package com.example.dsf.gdb.event;

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.dsf.datamodel.IDMEvent;
import org.eclipse.cdt.dsf.debug.service.IRunControl.ISuspendedDMEvent;
import org.eclipse.cdt.dsf.mi.service.command.events.IMIDMEvent;
import org.eclipse.cdt.dsf.mi.service.command.events.MIStoppedEvent;
import org.eclipse.cdt.dsf.mi.service.command.output.MIFrame;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;

import main.Activator;

/**
 * We listen for the target to stop at the main breakpoint. This listener is
 * installed when the session is created and we uninstall ourselves when we
 * get to the breakpoint state, as we have no further need to monitor events
 * beyond that point.
 */
public class SessionEventListener {

	private DsfSession fSession;
	
	/** The MI event associated with the breakpoint at main() */
	private MIStoppedEvent fInitialStoppedEvent;
	
	/** Event semaphore we set when the target has reached the breakpoint at main() */
	final private Object fTargetSuspendedSem = new Object(); // just used as a semaphore
	
	/** Flag we set to true when the target has reached the breakpoint at main() */
	private boolean fTargetSuspended;
	
	private ILaunchConfiguration fLaunchConfiguration;
	
	public SessionEventListener(ILaunchConfiguration launchConfiguration) {
		fLaunchConfiguration  = launchConfiguration;
	}
	
	public void setSession(DsfSession session) {
		fSession = session;
		assert fSession != null;
	}
	
	public void eventDispatched(IDMEvent<?> event) {
		Assert.isNotNull(fSession);
		
		if (event instanceof ISuspendedDMEvent) {
			IMIDMEvent iMIEvent = (IMIDMEvent) event;
			Object miEvent = iMIEvent.getMIEvent();
			
			if (miEvent instanceof MIStoppedEvent) {
				// Store the corresponding MI *stopped event
				fInitialStoppedEvent = (MIStoppedEvent) miEvent;
				
				// Check the content of the frame for the method we
				// should stop at
				String stopAt = null;
				try {
					stopAt = fLaunchConfiguration.getAttribute(
							ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN_SYMBOL, "main");
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (stopAt == null)
					stopAt = "main";
				
				MIFrame frame = fInitialStoppedEvent.getFrame();
				if (frame != null && frame.getFunction() != null && frame.getFunction().indexOf(stopAt) != -1) {
					// Set the event semaphore that will allow the test
					// to proceed
					synchronized (fTargetSuspendedSem) {
						fTargetSuspended = true;
						fTargetSuspendedSem.notify();
					}

					// We found our event, no further need for this
					// listener
					fSession.removeServiceEventListener(this);
				}
				
			}
		}
	}
	
	public void waitUntilTargetSuspended() throws InterruptedException {
		if (!fTargetSuspended) {
			synchronized (fTargetSuspendedSem) {
				fTargetSuspendedSem.wait(2000);
				Assert.isTrue(fTargetSuspended);
			}
		}
	}
	
	public MIStoppedEvent getInitialStoppedEvent() {
		return fInitialStoppedEvent;
	}
	
}
