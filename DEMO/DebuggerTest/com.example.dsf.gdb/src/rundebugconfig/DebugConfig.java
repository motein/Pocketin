package rundebugconfig;

import main.Activator;

import org.eclipse.core.runtime.Assert;
import tools.StringTools;

public enum DebugConfig {

	INSTANCE;

	/**
	 * Debug Modeを表す
	 * 
	 * @author tokuzono
	 * 
	 */
	public enum DebugMode {
		/**
		 * すべてのアルゴリズム関数の入り口およびアルゴリズムのline breakpointで止まる
		 */
		DEBUG_MODE1,

		/**
		 * Test Spec Breakpointがセットされているアルゴリズム関数の入り口で止まる
		 */
		DEBUG_MODE2,

		/**
		 * アルゴリズム関数のline break pointで止まる
		 */
		DEBUG_MODE3,

		/**
		 * Test Spec Breakpointがセットされているアルゴリズム関数のline break pointで止まる
		 */
		DEBUG_MODE4
	};

	/**
	 * 止める対象を表す
	 * 
	 * @author tokuzono
	 * 
	 */
	public enum BreakPointMode {
		/**
		 * Algorithm実行の際に止まる
		 */
		ALGORITHM,

		/**
		 * Dispatcher実行の際に止まる
		 */
		DISPATCHER,

		/**
		 * AlgorithmとDispatcher実行の際に止まる
		 */
		ALGORITHM_DISPATCHER
	};

	/**
	 * DebugModeのデフォルト値
	 */
	public final DebugMode DBG_MODE_DEFAULT = DebugMode.DEBUG_MODE2;

	/**
	 * BreakPointModeのデフォルト値
	 */
	public final BreakPointMode BRP_MODE_DEFAULT = BreakPointMode.ALGORITHM;

	// preference keys for debug configuration
	private static final String KEY_DEBUGMODE = ".dbgMode";
	private static final String KEY_BPMODE = ".brpMode";

	/**
	 * Debug Mode用Preference Storeにアクセスするためのkey
	 */
	private final String dbgModeKey = "";

	/**
	 * Breakpoint Mode用Preference Storeにアクセスするためのkey
	 */
	private final String brpModeKey = "";

	/**
	 * Debug Mode
	 */
	private DebugMode dbgmode = DebugMode.DEBUG_MODE2;

	/**
	 * Breakpoint Mode
	 */
	private BreakPointMode bpmode = BreakPointMode.ALGORITHM;

	/**
	 * DebugMode返す
	 * 
	 * @return
	 */
	public synchronized DebugMode getDebugMode() {
		return dbgmode;
	}

	/**
	 * BreakPointModeを返す
	 * 
	 * @return
	 */
	public synchronized BreakPointMode getBreakpointMode() {
		return bpmode;
	}

	/**
	 * DebugModeをセットする
	 * 
	 * @param mode
	 */
	public synchronized void setDebugMode(DebugMode mode) {
		Assert.isNotNull(mode);
		if (dbgmode != mode) {
			dbgmode = mode;

			int modenum = getDebugModeNum(dbgmode);
			Activator.getDefault().getPreferenceStore()
					.setValue(dbgModeKey, modenum);

		}
	}

	/**
	 * BreakPointModeをセットする
	 * 
	 * @param mode
	 */
	public synchronized void setBreakpointMode(BreakPointMode mode) {
		Assert.isNotNull(mode);
		if (bpmode != mode) {
			bpmode = mode;

			int modenum = getBreakpointModeNum(bpmode);
			Activator.getDefault().getPreferenceStore()
					.setValue(brpModeKey, modenum);
		}
	}

	/**
	 * Preference Storeから値を読み込み値を保持する
	 */
	private synchronized void update() {
		Assert.isTrue(StringTools.isSetAny(dbgModeKey));

		int dbgmodenum = Activator.getDefault().getPreferenceStore()
				.getInt(dbgModeKey);
		if (dbgmodenum > 0) {
			dbgmode = getDebugMode(dbgmodenum);
		} else {
			dbgmode = DBG_MODE_DEFAULT;
		}

		int brpmodenum = Activator.getDefault().getPreferenceStore()
				.getInt(brpModeKey);
		if (brpmodenum > 0) {
			bpmode = getBreakpointMode(brpmodenum);
		} else {
			bpmode = BRP_MODE_DEFAULT;
		}
	}

	/**
	 * DebugModeを返す
	 * 
	 * @param modenum
	 *            debug modeの数値(1から4までの値)
	 * @return　数値に対応するDebugMode
	 */
	private DebugMode getDebugMode(int modenum) {

		DebugMode mode;

		switch (modenum) {
		case 1:
			mode = DebugMode.DEBUG_MODE1;
			break;
		case 2:
			mode = DebugMode.DEBUG_MODE2;
			break;
		case 3:
			mode = DebugMode.DEBUG_MODE3;
			break;
		case 4:
			mode = DebugMode.DEBUG_MODE4;
			break;
		default:
			mode = DBG_MODE_DEFAULT;
			Assert.isTrue(false);
			break;
		}

		return mode;
	}

	/**
	 * BreakPointModeを返す
	 * 
	 * @param modenum
	 *            beak point modeの数値
	 * @return　数値に対するBreakPointMode
	 */
	private BreakPointMode getBreakpointMode(int modenum) {
		BreakPointMode mode;

		switch (modenum) {
		case 1:
			mode = BreakPointMode.ALGORITHM;
			break;
		case 2:
			mode = BreakPointMode.DISPATCHER;
			break;
		case 3:
			mode = BreakPointMode.ALGORITHM_DISPATCHER;
			break;
		default:
			mode = BRP_MODE_DEFAULT;
			Assert.isTrue(false);
			break;
		}

		return mode;
	}

	/**
	 * 与えられたDebugModeに対応する数値を返す
	 * 
	 * @param mode
	 *            DebugMode
	 * @return　1から4までの数値
	 */
	private int getDebugModeNum(DebugMode mode) {
		Assert.isNotNull(mode);

		int modenum;

		switch (mode) {
		case DEBUG_MODE2:
			modenum = 2;
			break;
		case DEBUG_MODE3:
			modenum = 3;
			break;
		case DEBUG_MODE4:
			modenum = 4;
			break;
		case DEBUG_MODE1:
		default:
			modenum = 1;
			break;
		}

		return modenum;
	}

	/**
	 * 与えられたBreakPointModeに対応する数値を返す
	 * 
	 * @param mode
	 *            BreakPointMode
	 * @return
	 */
	private int getBreakpointModeNum(BreakPointMode mode) {
		Assert.isNotNull(mode);

		int modenum;

		switch (mode) {
		case DISPATCHER:
			modenum = 2;
			break;
		case ALGORITHM_DISPATCHER:
			modenum = 3;
			break;
		case ALGORITHM:
		default:
			modenum = 1;
			break;
		}

		return modenum;
	}

//	/**
//	 * コンストラクタ
//	 */
//	private DebugConfig() {
//		String prefkeyPrefix = TdeKeyProvider.getKeyOf(this);
//		dbgModeKey = prefkeyPrefix + KEY_DEBUGMODE;
//		brpModeKey = prefkeyPrefix + KEY_BPMODE;
//		update();
//	}
}

