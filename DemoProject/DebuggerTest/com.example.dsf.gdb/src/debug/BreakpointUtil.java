package debug;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.cdt.debug.core.CDIDebugModel;
import org.eclipse.cdt.debug.core.model.ICBreakpoint;
import org.eclipse.cdt.debug.core.model.ICLineBreakpoint;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;

import tpldebugger.TestplanBreakpoint;

public class BreakpointUtil {
	
	private static final String MARKER_TYPE = "com.agilent.p9000.specs.tde.tplBreakpointMarker";

	private static final String BREAKPOINT_ID = "com.agilent.p9000.specs.tde.tplBreakpoint";
	
	public static ILineBreakpoint toggleBreakpoint(IResource resource,
			int linenum) throws CoreException {
		Assert.isNotNull(resource);
		Assert.isTrue(linenum >= 0);

		ILineBreakpoint previous = findBreakpoint(resource, linenum);

		if (previous != null) {
			removeBreakpoint(previous);
		} else {
			addBreakpoint(resource, linenum);
		}

		return previous;
	}
	
	public static ILineBreakpoint findBreakpoint(IResource resource,
			int linenum) throws CoreException {

		IBreakpointManager manager = DebugPlugin.getDefault()
				.getBreakpointManager();

		if (manager != null) {
			IBreakpoint[] breakpoints = manager.getBreakpoints(BREAKPOINT_ID);
			for (IBreakpoint bp : breakpoints) {
				if (bp instanceof ILineBreakpoint) {
					if (isSame(resource, linenum, (ILineBreakpoint) bp)) {
						return (ILineBreakpoint) bp;
					}
				}
			}
		}

		return null;
	}

	public static synchronized boolean isActiveTestplanBreakpoint(
			IResource resource, int[] lines) throws CoreException {

		Assert.isNotNull(lines);
		for (int linenum : lines) {
			if (isActiveTestplanBreakpoint(resource, linenum)) {
				return true;
			}
		}

		return false;
	}
	
	public static synchronized void createFunctionBreakpoint(String algname,
			IResource cppResource) throws CoreException {
			String sourceHandle = cppResource.getLocation().toString();
			CDIDebugModel.createFunctionBreakpoint(sourceHandle, cppResource, 0, algname, -1, -1, -1, true, 0, "", true);
			
//			Target target = DebugContext.INSTANCE.getTarget();
//			if (target != null) {
//				ICDIFunctionLocation location = target
//						.createFunctionLocation(sourceHandle, algname);
//				ICDICondition condition = target.createCondition(0, "");
//				waitForTargetRunning(target);
//
//				target.setFunctionBreakpoint(ICBreakpointType.TEMPORARY, location,
//						condition, true, true);
//			}
	}

	public static synchronized Map<ICBreakpoint, Boolean> setAllLineBreakpointsEnabled(
			IResource resource, Boolean enabled)
			throws CoreException {

		Map<ICBreakpoint, Boolean> previous = new HashMap<ICBreakpoint, Boolean>();
		
		IBreakpointManager bm = DebugPlugin.getDefault().getBreakpointManager();
		if (bm != null) {
			for(IBreakpoint bp : bm.getBreakpoints()) {
				if (bp instanceof ICLineBreakpoint) {
					if (isSame(resource, (ICLineBreakpoint) bp)) {
						previous.put((ICBreakpoint)bp, bp.isEnabled());
						bp.setEnabled(enabled);
						bm.fireBreakpointChanged(bp);
					}
				}
					
			}
		}
		
//		IDebugTarget target = DebugContext.INSTANCE.getTarget();
//		if (target != null) {
//			IThread a;
//			ICBreakpoint[] breakpoints = target.getThreads()[0].getBreakpoints();
//			if (breakpoints != null) {
//				for (ICBreakpoint bp : breakpoints) {
//					if (bp instanceof LineBreakpoint) {
//						if (isSame(resource, (LineBreakpoint) bp)) {
//							previous.put(bp, bp.isEnabled());
//							waitForTargetRunning(target);
//							bp.setEnabled(enabled);
//							if (bp instanceof IBreakpoint) {
//								DebugPlugin.getDefault().getBreakpointManager()
//										.fireBreakpointChanged(
//												(IBreakpoint) bp);
//							}
//						}
//					}
//				}
//			}
//		}

		return previous;
	}

	public static synchronized void restoreBreakpointsEnabled(
			Map<ICBreakpoint, Boolean> map) throws CoreException {
		Assert.isNotNull(map);

		Set<ICBreakpoint> keys = map.keySet();
		IBreakpointManager bm = DebugPlugin.getDefault().getBreakpointManager();
		if (keys != null && bm != null) {
			for (ICBreakpoint bp : keys) {
				if (bp != null) {
					Boolean enabled = map.get(bp);
					if (enabled != null) {
						bp.setEnabled(enabled);
						bm.fireBreakpointChanged(bp);
					}
				}
			}
		}
		
//		Target target = DebugContext.INSTANCE.getTarget();
//
//		if (keys != null && target != null) {
//			for (ICBreakpoint bp : keys) {
//				if (bp != null) {
//					Boolean enabled = map.get(bp);
//					if (enabled != null) {
//						waitForTargetRunning(target);
//						bp.setEnabled(enabled);
//						if (bp instanceof IBreakpoint) {
//							DebugPlugin.getDefault().getBreakpointManager()
//									.fireBreakpointChanged((IBreakpoint) bp);
//						}
//					}
//				}
//			}
//		}
	}

	/**
	 * 与えられたbreak pointが指定されたリソースについてのTest Plan Breakpointであればtrueを返す
	 * 
	 * @param bp
	 *            breakpoint
	 * @param resource
	 *            ブレークポイントが付加されているか調べる対象のリソース
	 * @return 与えられたブレークポイントが、与えられたリソースに付加されたTest Plan Breakpointであればtrueを返す
	 */
	public static boolean isTestplanBreakpoint(IBreakpoint bp,
			IResource resource) {
		if (bp instanceof TestplanBreakpoint) {
			IMarker marker = bp.getMarker();
			if (resource != null && marker != null) {
				IResource bpresource = marker.getResource();
				if (resource.equals(bpresource)) {
					return true;
				}
			}
		}
		return false;
	}
	
	static synchronized boolean isActiveTestplanBreakpoint(
			IResource resource, int linenum) throws CoreException {

		ILineBreakpoint bp = findBreakpoint(resource, linenum);

		return (bp != null && bp.isEnabled());
	}

//	private static synchronized void waitForTargetRunning(IDebugTarget target) {
//		Assert.isNotNull(target);
//		try {
//			int log = 0;
//			for (int i = 0; i < 10; i++) {
//				if (target.getMISession().getMIInferior().isRunning()) {
//					log = 1000 + i;
//					break;
//				}
//				if (target.getMISession().getMIInferior().isRunning()) {
//					log = 2000 + i;
//					break;
//				}
//				if (target.getMISession().getMIInferior().isRunning()) {
//					log = 3000 + i;
//					break;
//				}
//				Thread.sleep(2);
//			}
//
//			if (DebugFlag.getTestplanDebuggerFlag().isEnabled()) {
//				if (log == 0) {
//					System.out.println("timeout");
//				} else {
//					System.out.println("log: " + log);
//				}
//			}
//		} catch (NullPointerException e) {
//			Assert.isTrue(false);
//		} catch (InterruptedException e) {
//			if (DebugFlag.getTestplanDebuggerFlag().isEnabled()) {
//				System.out.println("Interrupted");
//				e.printStackTrace();
//			}
//		}
//	}

	private static void addBreakpoint(IResource resource, int linenum) {
		Map<String, Object> attributes = new HashMap<String, Object>(10);
		fillAttributes(attributes, linenum);
		try {
			TestplanBreakpoint breakpoint = new TestplanBreakpoint();
			breakpoint.addBreakpoint(resource, MARKER_TYPE, attributes);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private static void removeBreakpoint(IBreakpoint breakpoint)
			throws CoreException {
		if (breakpoint != null) {
			IBreakpointManager manager = DebugPlugin.getDefault()
					.getBreakpointManager();

			if (manager != null) {
				manager.removeBreakpoint(breakpoint, true);
			}
		}
	}

	private static void fillAttributes(Map<String, Object> attributes,
			int linenum) {
		attributes.put(IBreakpoint.ID, BREAKPOINT_ID);
		attributes.put(IMarker.LINE_NUMBER, Integer.valueOf(linenum));
		attributes.put(IBreakpoint.ENABLED, true);
		attributes.put(IMarker.MESSAGE, "test message");
	}

	private static boolean isSame(IResource resource, int line,
			ILineBreakpoint bp) throws CoreException {
		if (resource != null && bp != null && line >= 0) {
			IMarker marker = bp.getMarker();
			if (marker != null) {
				IResource bpresource = marker.getResource();
				if (bpresource != null) {
					if (bp.getLineNumber() == line
							&& bpresource.equals(resource)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private static boolean isSame(IResource resource, ICLineBreakpoint bp)
			throws CoreException {
		if (resource != null && bp != null) {
			String name = resource.getLocation().toString();
			String fileName = bp.getFileName();
			if (name != null && name.equals(fileName)) {
				return true;
			}
		}

		return false;
	}

}
