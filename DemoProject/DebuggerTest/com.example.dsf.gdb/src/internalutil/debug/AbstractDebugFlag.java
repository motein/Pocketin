package internalutil.debug;

public abstract class AbstractDebugFlag {

	private boolean enabled = false;

	public AbstractDebugFlag() {
		this.enabled = false;
	}

	/**
	 * flagに値をセットする
	 * 
	 * @param value
	 *            セットする値
	 * @return 以前の値
	 */
	public boolean setEnabled(boolean value) {
		boolean previous = this.enabled;
		this.enabled = value;

		return previous;
	}

	/**
	 * flagの値を返す
	 * 
	 * @return flagの値
	 */
	public boolean isEnabled() {
		return this.enabled;
	}
}
