package debug;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.model.ICBreakpoint;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import internalutil.debug.DebugFlag;
import rundebugconfig.DebugConfig;
import rundebugconfig.DebugConfig.BreakPointMode;
import rundebugconfig.DebugConfig.DebugMode;
import tools.ResourceTools;
import tools.StringTools;
import tools.TdeResourceUtil;

public class DebugUtil {

	// private static final String DISPATCHER_PATH =
		// "/opt/SPECS/sys/bin/dispatcher";
		private static final String DISPATCHER_PATH = "C:/ProgramData/SPECSX/sys/bin/dispatcher";

		/**
		 * 指定されたテストプラン用のdebug sessionを開始する
		 * 
		 * @param programPath
		 *            実行ファイルのパス
		 * @param processId
		 *            プロセスID
		 */
		public static void launchDebugger(String programPath, int processId) {
			Assert.isTrue(StringTools.isSetAny(programPath));
			Assert.isTrue(processId >= 0);

			IProject project = ResourceTools
					.getProjectFromPath(new Path(programPath));

			if (project != null && project.isOpen()) {
				try {
					ILaunchManager manager = DebugPlugin.getDefault()
							.getLaunchManager();
					ILaunchConfigurationType type = manager
							.getLaunchConfigurationType(
									"com.agilent.p9000.specs.tde.TdeAttatchLaunch");
					ILaunchConfigurationWorkingCopy wc = type.newInstance(null,
							"SPECS");
					wc.setAttribute(
							ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME,
							DISPATCHER_PATH);
					wc.setAttribute(
							ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE,
							ICDTLaunchConfigurationConstants.DEBUGGER_MODE_ATTACH);
					wc.setAttribute(
							ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ID,
							"com.agilent.p9000.specs.tde.ExampleDebugger");
					wc.setAttribute(
							ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME,
							project.getName());
					wc.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PLATFORM,
							"*");
					wc.setAttribute(
							ICDTLaunchConfigurationConstants.ATTR_ATTACH_PROCESS_ID,
							processId);
					ILaunchConfiguration config = wc.doSave();

					ILaunch launch = config.launch(ILaunchManager.DEBUG_MODE, null);
					IDebugTarget debugtarget = launch.getDebugTarget();

//					if (debugtarget instanceof CDebugTarget) {
//						CDebugTarget ctarget = (CDebugTarget) debugtarget;
//						ICDITarget cditarget = ctarget.getCDITarget();
//						if (cditarget instanceof Target) {
//							Target target = (Target) cditarget;
//							target.resume();
//							DebugContext.INSTANCE.setTarget(target);
//						}
//					}

					showDebugPerspective();
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * Algorithmの実行コンテキストによってbreak pointのenable/disableをセットする
		 * <p>
		 * 最初の実装として、function breakpointの制御のみをおこなう。 実行箇所のAlgorithm関数のfunction
		 * breakpointが セットされていればenableする セットされていなければなにもしない
		 * 
		 * 
		 * @param testSpecName
		 * @param algorithmName
		 * @param algBodyFile
		 *            Algorithm body定義.cppファイル
		 * @param isDispatcher
		 * @param lines
		 * @throws CoreException
		 * @throws CDIException
		 */
		public static synchronized Map<ICBreakpoint, Boolean> updateBreakpointState(
				IResource testSpec, String algorithmName, IResource algBodyFile,
				boolean isDispatcher, int[] lines)
				throws CoreException {

			Map<ICBreakpoint, Boolean> previous = new HashMap<ICBreakpoint, Boolean>();

			DebugMode debugmode = DebugConfig.INSTANCE.getDebugMode();
			if (DebugFlag.getTestplanDebuggerFlag().isEnabled()) {
				System.out.println("DebugMode: " + debugmode);
			}

			if (lines != null) {
				if (activateTestplanBp(isDispatcher)) {
					switch (debugmode) {
					case DEBUG_MODE1:
						// Stop at every Test Spec Line
						BreakpointUtil.createFunctionBreakpoint(algorithmName,
								algBodyFile);
						break;
					case DEBUG_MODE2:
						// Stop if Test Spec breakpoint is set
						if (BreakpointUtil.isActiveTestplanBreakpoint(testSpec,
								lines)) {
							BreakpointUtil.createFunctionBreakpoint(algorithmName,
									algBodyFile);
						}
						break;
					case DEBUG_MODE3:
						previous = null;
						break;
					case DEBUG_MODE4:
						if (!BreakpointUtil.isActiveTestplanBreakpoint(testSpec,
								lines)) {
							// only disable if active Test Plan break point is
							// not set
							previous = BreakpointUtil.setAllLineBreakpointsEnabled(
									algBodyFile, false);
						} else {
							previous = null;
						}
						break;
					}
				} else {
					previous = BreakpointUtil
							.setAllLineBreakpointsEnabled(algBodyFile, false);
				}
			}

			return previous;
		}

		/**
		 * Debug用perspectiveを表示する
		 * 
		 * @throws WorkbenchException
		 */
		private static void showDebugPerspective() throws WorkbenchException {
			TdeResourceUtil.getDisplay().asyncExec(new Runnable() {
				public void run() {
					IWorkbench workbench = PlatformUI.getWorkbench();
					if (workbench != null) {
						IPerspectiveRegistry preg = workbench
								.getPerspectiveRegistry();
						IPerspectiveDescriptor pd = preg.findPerspectiveWithId(
								"org.eclipse.debug.ui.DebugPerspective");
						if (pd != null) {

							IWorkbenchWindow window = workbench
									.getActiveWorkbenchWindow();
							try {
								workbench.showPerspective(pd.getId(), window);
							} catch (WorkbenchException e) {
								e.printStackTrace();
							}
						}
					}
				}
			});
		}

		private static boolean activateTestplanBp(boolean isDispatcher) {
			boolean activate = true;

			BreakPointMode bpmode = DebugConfig.INSTANCE.getBreakpointMode();
			switch (bpmode) {
			case ALGORITHM:
				activate = !isDispatcher;
				break;
			case DISPATCHER:
				activate = isDispatcher;
				break;
			case ALGORITHM_DISPATCHER:
				activate = true;
				break;
			}

			return activate;
		}
}
