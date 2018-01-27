package debug;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.cdt.dsf.service.DsfServiceEventHandler;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.core.runtime.Platform;

import main.Activator;

public class ServiceEventWaitor<V> {
	/*
	 *  Indicates we will wait forever. Otherwise the time specified
	 *  is in milliseconds.
	 */
	public final static int WAIT_FOREVER = 0 ;

	/* The type of event to wait for */
	private Class<V> fEventTypeClass;
	private DsfSession fSession;
    
	// Queue of events.  This allows to receive multiple events and keep them.
    private List<V> fEventQueue = Collections.synchronizedList(new LinkedList<V>());
    
    /**
     * Trace option for wait metrics
     */
    private static final boolean LOG = Activator.DEBUG && Boolean.parseBoolean(Platform.getDebugOption("org.eclipse.cdt.tests.dsf.gdb/debug/waitMetrics"));  //$NON-NLS-1$

	/**
	 * Constructor
	 * 
	 * @param session
	 *            the DSF session we'll wait for an event to happen on
	 * @param eventClass
	 *            the event to expect
	 */
	public ServiceEventWaitor(DsfSession session, Class<V> eventClass)	{
		assert eventClass != null;
		fSession = session;
		fEventTypeClass = eventClass;
        Runnable runnable = new Runnable() {
            @Override
			public void run() {
            	fSession.addServiceEventListener(ServiceEventWaitor.this, null);
            }
        };
        try {
			fSession.getExecutor().submit(runnable).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (fEventTypeClass != null) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					fSession.removeServiceEventListener(ServiceEventWaitor.this);
				}
			};
			fSession.getExecutor().submit(runnable).get();
		}
	}

	/**
	 * Wait for events of V type for the specified amount of time
	 */
	public synchronized List<V> waitForEvents(int period) {
		long startMs = System.currentTimeMillis();
		List<V> events = new ArrayList<V>();
		
		//Timeout exception will exit the loop and return the resulting list of events
		while (true) {
			int timeRemaining = (int) (period - (System.currentTimeMillis() - startMs));
			if (timeRemaining > 0) {
				V sevent;
				try {
					sevent = waitForEvent(timeRemaining);
					if (sevent != null) {
						events.add(sevent);
					} 
				} catch (Exception e) {
					break;
				}
			} else {
				break;
			}
		}
		
		return events;
	}
	
	
	/*
	 * Block until 'timeout' or the expected event occurs. The expected event is
	 * specified at construction time.
	 * 
	 * @param timeout the maximum time to wait in milliseconds.
	 */
	public synchronized V waitForEvent(int timeout) throws Exception {
		if (fEventTypeClass == null) {
			throw new Exception("Event to wait for has not been specified!");
		}
		
		long startMs = System.currentTimeMillis();
		
		if (fEventQueue.isEmpty()) {
			wait(timeout);
			if (fEventQueue.isEmpty()) {
				throw new Exception("Timed out waiting for ServiceEvent: " + fEventTypeClass.getName());
			}
		}

		long stopMs = System.currentTimeMillis();
		
		// Turning on trace during development gives you the following  
		// helpful analysis, which you can use to establish reasonable timeouts,
		// and detect poorly configured ones. The best way to use this it to 
		// set breakpoints on the WARNING println calls.
		if (LOG) {
			final long duration = stopMs - startMs;
			System.out.println("The following caller waited for " + (duration) + " milliseconds");
			boolean print = false;
			for (StackTraceElement frame : Thread.currentThread().getStackTrace()) {
				if (frame.toString().startsWith("sun.reflect.NativeMethodAccessorImpl")) {
					// ignore anything once we get into the reflection/junit portion of the stack
					System.out.println("\t... (junit)");
					break;
				}
				if (print) {
					System.out.println("\t" + frame);
				}
				if (!print && frame.toString().contains("ServiceEventWaitor.waitForEvent")) {
					// we're only interested in the call stack up to (and including) our caller					
					print = true;
				}
			}
			
			if (timeout != WAIT_FOREVER) {
				if (duration == 0) {
					if (timeout > 1000) {
						System.out.println("WARNING: Caller specified a timeout over a second but the operation was instantenous. The timeout is probably too loose.");
					}
					else if (timeout < 100) {
						System.out.println("WARNING: Caller specified a timeout less than 100 milliseconds. Even though the operation completed instantaneously, the timeout is probably too tight.");
					}
				}
				else {
					if (timeout/duration > 7.0 && timeout > 2000) {
						// don't bother for timeouts less than 2 seconds
						System.out.println("WARNING: Caller specified a timeout that was more than 7X what was necessary. The timeout is probably too loose.");
					}
					else if ((((float)(timeout - duration))/(float)duration) < 0.20) {
						System.out.println("WARNING: Caller specified a timeout that was less than 20% above actual time. The timeout is probably too tight.");
					}
				}
			}
			else {
				System.out.println("WARNING: Caller requested to wait forever. It should probably specify some reasonable value.");
			}
		}
		
		V vevent = fEventQueue.remove(0);

		
		return vevent;
	}

	/*
	 * Listen to all possible events by having the base class be the parameter.
	 * and then figure out if that event is the one we were waiting for.
	 */
	@DsfServiceEventHandler 
	public void eventDispatched(V event) {
		if (fEventTypeClass.isAssignableFrom(event.getClass())) {
			synchronized(this) {
				fEventQueue.add(event);
				notifyAll();
			}
		}
	}
}

