package internalutil.debug;

public class DebugFlag {

	/**
	 * The instance
	 */
	private static DebugFlag instance = new DebugFlag();

	/**
	 * Spec Provider関連のdebugをおこなうかどうかのflag
	 */
	private SpecProviderDebugFlag specProvider = new SpecProviderDebugFlag();

	/**
	 * Listenerのadd, remove関連のdebugをおこなうかどうかのflag
	 */
	private ListenerDebugFlag listener = new ListenerDebugFlag();

	/**
	 * AlgorithmSpecのcache関連
	 */
	private AlgorithmViewCacheDebugFlag algviewcache = new AlgorithmViewCacheDebugFlag();

	/**
	 * Algorithm List関連
	 */
	private AlgorithmListDebugFlag alglist = new AlgorithmListDebugFlag();

	/**
	 * TableNavigatorのcache関連
	 */
	private TableNavigatorCacheDebugFlag tableNavigatorCache = new TableNavigatorCacheDebugFlag();

	/**
	 * Test Plan Debugger用のdebug flag
	 */
	private TestplanDebuggerDebugFlag testplanDebugger = new TestplanDebuggerDebugFlag();

	/**
	 * Expression関連
	 */
	private ExpressionDebugFlag expression = new ExpressionDebugFlag();

	/**
	 * Undo用debug flag
	 */
	private UndoDebugFlag undo = new UndoDebugFlag();

	/**
	 * Resource Monitor用debug flag
	 */
	private ResourceMonitorDebugFlag resourcemonitor = new ResourceMonitorDebugFlag();

	/**
	 * Execution用debug flag
	 */
	private ExecutionDebugFlag execution = new ExecutionDebugFlag();

	/**
	 * Test Result用debug flag
	 */
	private TestResultDebugFlag testresult = new TestResultDebugFlag();

	/**
	 * QuickChart用debug flag
	 */
	private QuickChartDebugFlag quickchart = new QuickChartDebugFlag();

	/**
	 * Template handling用debug flag
	 */
	private TemplateDebugFlag template = new TemplateDebugFlag();

	/**
	 * Code Analysis用debug flag
	 */
	private CodanDebugFlag codan = new CodanDebugFlag();

	/**
	 * SpecProvider用 debug flagを返す
	 * 
	 * @return
	 */
	public static AbstractDebugFlag getSpecProviderFlag() {
		return instance.specProvider;
	}

	/**
	 * Listener用debug flagを返す
	 * 
	 * @return
	 */
	public static AbstractDebugFlag getListenerFlag() {
		return instance.listener;
	}

	/**
	 * AlgorithmViewのcache用debug flagを返す
	 * 
	 * @return
	 */
	public static AbstractDebugFlag getAlgorithmViewCacheFlag() {
		return instance.algviewcache;
	}

	/**
	 * Algorithm List用debug flagを返す
	 * 
	 * @return
	 */
	public static AbstractDebugFlag getAlgorithmListFlag() {
		return instance.alglist;
	}

	/**
	 * TableNavigatorのcache用debug flagを返す
	 * 
	 * @return
	 */
	public static AbstractDebugFlag getTableNavigatorCacheFlag() {
		return instance.tableNavigatorCache;
	}

	/**
	 * Test Plan Debugger関連のdebug flagを返す
	 * 
	 * @return
	 */
	public static AbstractDebugFlag getTestplanDebuggerFlag() {
		return instance.testplanDebugger;
	}

	/**
	 * Expression用debug flagを返す
	 * 
	 * @return
	 */
	public static AbstractDebugFlag getExpressionFlag() {
		return instance.expression;
	}

	/**
	 * Undo用debug flagを返す
	 * 
	 * @return
	 */
	public static AbstractDebugFlag getUndoFlag() {
		return instance.undo;
	}

	/**
	 * ResourceMonitor用debug flagを返す
	 * 
	 * @return
	 */
	public static AbstractDebugFlag getResourceMonitorFlag() {
		return instance.resourcemonitor;
	}

	/**
	 * Execution用debug flagを返す
	 * 
	 * @return
	 */
	public static AbstractDebugFlag getExecutionFlag() {
		return instance.execution;
	}

	/**
	 * TestResult用debug flagを返す
	 * 
	 * @return
	 */
	public static AbstractDebugFlag getTestResuFlag() {
		return instance.testresult;
	}

	/**
	 * QuickChart用debug flagを返す
	 * 
	 * @return
	 */
	public static AbstractDebugFlag getQuickChartFlag() {
		return instance.quickchart;
	}

	/**
	 * Template用debug flagを返す
	 * 
	 * @return
	 */
	public static AbstractDebugFlag getTemplateDebugFlag() {
		return instance.template;
	}

	/**
	 * Code Analysis用debug flagを返す
	 * 
	 * @return
	 */
	public static AbstractDebugFlag getCodanDebugFlag() {
		return instance.codan;
	}

	/**
	 * SpecProvider関連のdebug flagがセットされていたらtrueを返す
	 * 
	 * @return
	 */
	public static boolean isSpecProviderEnabled() {
		return instance.specProvider.isEnabled();
	}

	/**
	 * Listener関連のdebug flagがセットされていたらtrueを返す
	 * 
	 * @return
	 */
	public static boolean isListenerEnabled() {
		return instance.listener.isEnabled();
	}

	/**
	 * Private constructor
	 */
	private DebugFlag() {
	}
}
