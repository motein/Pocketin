package debug;

import org.eclipse.cdt.debug.core.model.ICDebugTargetGroup;
import org.eclipse.core.resources.IFile;
import org.eclipse.launchbar.core.target.TargetStatus;

/**
 * 
 * debug対象となるプロセスIDを管理する
 * 
 * @author tokuzono
 * 
 */
public enum DebugContext {

	INSTANCE;

	/**
	 * 
	 */
	private IFile testplan = null;

	/**
	 * process id
	 */
	private int processId = -1;

	/**
	 * プロジェクト名 (e.g. Cproject)
	 */
	private String projectName = "";

	/**
	 * 実行プログラムのパス
	 */
	private String programPath = "";

	/**
	 * Target
	 */
	private ICDebugTargetGroup target = null;

	/**
	 * @return the target
	 */
	public ICDebugTargetGroup getTarget() {
		return target;
	}

	/**
	 * @return the testplan
	 */
	public IFile getTestplan() {
		return testplan;
	}

	/**
	 * @return the programName
	 */
	public String getProgramPath() {
		return programPath;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @return the ProcessID
	 */
	public int getProcessId() {
		return processId;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(ICDebugTargetGroup target) {
		this.target = target;
	}

	/**
	 * @param testplan
	 *            the testplan to set
	 */
	public void setTestplan(IFile testplan) {
		this.testplan = testplan;
	}

	/**
	 * @param programName
	 *            the programName to set
	 */
	public void setProgramPath(String programName) {
		this.programPath = programName;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @param processid
	 *            the ProcessID to set
	 */
	public void setProcessId(int processid) {
		this.processId = processid;
	}

	/**
	 * 値を無効化する
	 */
	public void clear() {
		testplan = null;
		processId = -1;
		projectName = "";
		programPath = "";
		target = null;
	}
}
