package org.eclipse.cdt.tests.dsf.gdb.framework;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.cdt.debug.core.CDIDebugModel;
import org.eclipse.cdt.debug.core.model.ICBreakpointType;
import org.eclipse.cdt.debug.core.model.ICFunctionBreakpoint;
import org.eclipse.cdt.debug.core.model.ICLineBreakpoint;
import org.eclipse.cdt.debug.internal.core.breakpoints.CFunctionBreakpoint;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;

@SuppressWarnings("restriction")
public class DebugUtil {

	public static ICFunctionBreakpoint createFunctionBreakpoint(String filename, String function) throws CoreException {
		return CDIDebugModel.createFunctionBreakpoint(filename, ResourcesPlugin.getWorkspace().getRoot(), 0,
				function, -1, -1, -1, true, 0, "", true);
	}
	
	public static ICLineBreakpoint createLineBreakpoint(String filename, int linenum) throws CoreException {
		return CDIDebugModel.createLineBreakpoint(
				filename, ResourcesPlugin.getWorkspace().getRoot(),
				ICBreakpointType.REGULAR, linenum, true, 0, "", true);
	}
	
	private static List<IBreakpoint> getPlatformBreakpoints(Predicate<IBreakpoint> predicate) {
		return Arrays.asList(DebugPlugin.getDefault().getBreakpointManager().getBreakpoints()).stream()
				.filter(predicate).collect(Collectors.toList());
	}
	
	public static List<IBreakpoint> getPlatformFunctionBreakpoints() {
		return getPlatformBreakpoints(CFunctionBreakpoint.class::isInstance);
	}
	
	
  	public static void deleteAllPlatformBreakpoints() throws Exception {
  		IBreakpointManager bm = DebugPlugin.getDefault().getBreakpointManager();
  		for (IBreakpoint b : bm.getBreakpoints()) {
  			bm.removeBreakpoint(b, true);
  		}
  	}
}
