package tools;

import java.text.DecimalFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import common.platform.SupportedPlatformEnum;

public class StringTools {

	/**
	 * 後続のスペースを削除する
	 * 
	 * @param str
	 *            入力文字列
	 * @return 後続のスペースが削除された文字列
	 */
	public static String trimTrailingSpaces(String str) {
		if (StringTools.isSetAny(str) && str.matches(".*\\s+$")) {
			int length = str.length();
			int i;
			for (i = length - 1; i >= 0; i--) {
				char c = str.charAt(i);
				if (c != ' ' && c != '\t') {
					break;
				}
			}

			return str.substring(0, i + 1);
		} else {
			return str;
		}
	}

	/**
	 * クリップボード用のテキストにするために、文字列の最後のタブおよび"\r\n"を削除する
	 * 
	 * @param str
	 * @return
	 */
	public static String trimStringForClipboardText(String str) {
		if (StringTools.isSetAny(str)) {
			int length = str.length();
			int i;
			for (i = length - 1; i >= 0; i--) {
				char c = str.charAt(i);
				if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
					break;
				}
			}

			return str.substring(0, i + 1);
		} else {
			return str;
		}
	}

	/**
	 * 指定された数のスペースからなるStringを返す
	 * 
	 * @param num
	 *            スペースの個数
	 * @return num個の連続するスペースから成るString
	 */
	public static String getSpaces(int num) {
		return getChars(' ', num);
	}

	/**
	 * 指定された数の文字からなるStringを返す
	 * 
	 * @param c
	 *            文字
	 * @param num
	 *            文字個数
	 * @return num個の連続するスペースから成るString, numが0以下のときは空文字を返す
	 */
	public static String getChars(char c, int num) {
		String retstr = "";

		if (num > 0) {
			for (int i = 0; i < num; i++) {
				retstr += c;
			}
		}

		return retstr;
	}

	/**
	 * rvalueをlvalue[0]にセットする
	 * 
	 * @param lvalue
	 *            アサインされる文字列を入れた配列 (lvalue[0]の値が更新される)
	 * @param rvalue
	 *            アサインする文字列
	 * @return lvalue[0]の値が変化したらtrueを返す
	 */
	public static boolean setValue(String[] lvalue, String rvalue) {
		Assert.isNotNull(lvalue, "Argument #1 should not be null.");

		// この関数はこの関数は Table Sizeによっては数万回呼ばれるのでロジックを単純化してチューニング
		// hasValue = !(isNull | isEmpty)の関係が成り立つので hasValueのみで評価
		boolean hasValueL = hasValue(lvalue);
		boolean hasValueR = hasValue(rvalue);
		if (hasValueL) {
			if (hasValueR) {
				if (lvalue[0].compareTo(rvalue) == 0) {
					// 同じ文字列なので何もしない
					return false;
				} else {
					lvalue[0] = rvalue;
					return true;
				}
			} else {
				lvalue[0] = null;
				return true;
			}
		} else {
			if (hasValueR) {
				lvalue[0] = rvalue;
				return true;
			} else {
				lvalue[0] = null;
				return false;
			}
		}
	}

	/**
	 * 値を更新する必要があるかどうかを返す
	 * 
	 * @param current
	 *            更新される値
	 * @param newvalue
	 *            更新する値
	 * @return currentにnewvalueをセットする必要があればtrueを返す
	 */
	public static boolean needUpdate(String current, String newvalue) {
		if (StringTools.isSetAny(current)) {
			// 現在の値がセットされている
			if (StringTools.isSetAny(newvalue)) {
				// セットしようとする値が空でないので、現在の値と比較する
				return (current.compareTo(newvalue) != 0);
			} else {
				// セットされている値を消そうとしている
				return true;
			}
		} else {
			// 現在の値が空なので、セットしようとする値が空でなければtrueとなる
			return StringTools.isSetAny(newvalue);
		}
	}

	/**
	 * 与えられた文字列に何か値がセットされているかどうかをテストする
	 * 
	 * @param str
	 *            String テストされる文字列
	 * @return strがnullでなく、文字列がセットされていればtrueを返す
	 */
	public static boolean isSetAny(String str) {
		return (str != null && !str.isEmpty());
	}

	/**
	 * 拡張子を除いたファイル名を返す (e.g. file.ext -> file, file.ext1.ext2 -> file)
	 * 
	 * @param str
	 * @return
	 */
	public static String basename(final String str) {
		if (str != null) {
			String[] results = str.split("\\.");
			if (results.length > 0) {
				if (isSetAny(results[0])) {
					return results[0].trim();
				}
			}
		}

		return null;
	}

	/**
	 * 現在の日付を返す
	 * 
	 * @return (MM/DD/YYYY)形式の日付
	 */
	public static final String getCurrentDateString() {
		Calendar cal = Calendar.getInstance();
		DecimalFormat dec02 = new DecimalFormat("00");

		String datestr = dec02.format(cal.get(Calendar.MONTH) + 1) + "/"
				+ dec02.format(cal.get(Calendar.DATE)) + "/"
				+ cal.get(Calendar.YEAR);

		return datestr;
	}

	/**
	 * 現在時刻を返す
	 * 
	 * @return (HH:MM:SS)形式の時刻
	 */
	public static final String getCurrentTimeString() {
		Calendar cal = Calendar.getInstance();
		DecimalFormat dec02 = new DecimalFormat("00");

		String timestr = dec02.format(cal.get(Calendar.HOUR_OF_DAY)) + ":"
				+ dec02.format(cal.get(Calendar.MINUTE)) + ":"
				+ dec02.format(cal.get(Calendar.SECOND));

		return timestr;
	}

	/**
	 * ユーザー名を返す
	 * 
	 * @return
	 */
	public static final String getUserName() {
		return System.getProperty("user.name");
	}

	/**
	 * Get OS version (RHEL5/RHEL7)
	 * 
	 * @return OS version
	 */
	public  static final String getOsVersion() {
		String osName = System.getProperty("os.name");		
		String osVersion = System.getProperty("os.version");
		
		if (osVersion.contains("el5")) {
			return SupportedPlatformEnum.RHEL5.toString();
		} else if (osVersion.contains("el7")) {
			return SupportedPlatformEnum.RHEL7.toString();
		}		
		else if(osName.contains("Windows 10"))
		{
			return SupportedPlatformEnum.WINDOWS10.toString();
		}
		else {
			// Internal error
			final String message = "OS Version : " + osVersion + "\nThis version is not supported.\n"
					+ "\nIt is nessesary to detaemine the optimization level used in algorithm library compilation.\n"
					+ "Optimization level -O0 is used temporary.";
			IWorkbench workbench = PlatformUI.getWorkbench();
		    Display display = workbench.getDisplay();
		    if (display == null || display.isDisposed()) {
		    	System.err.println(message);
		    	return "";
		    }
		    //IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		    IWorkbenchWindow windows[] = workbench.getWorkbenchWindows();
		    IWorkbenchWindow window = windows[0];
		    if (window == null) {
		    	System.err.println(message);
		    	return "";
		    }
		    final Shell shell = window.getShell();
			if (display.getThread() == Thread.currentThread()) {
				MessageDialog.openError(window.getShell(), "Error", message);
			} else {
				display = PlatformUI.getWorkbench().getDisplay();
				display.syncExec(new Runnable() {
					public void run() {
						MessageDialog.openError(shell, "Error", message);
					}
				});
			}
			return "";
		}
	}
	
	/**
	 * 空の要素のみをカンマで区切っている場合、空文字列を返す。
	 * 
	 * @param str
	 * @return
	 */
	public static String clearEmptyCommas(String str) {
		boolean isEmpty = true;
		String[] results = str.split(",");
		if (results == null) {
			isEmpty = true;
		} else {
			for (int i = 0; i < results.length; i++) {
				if (isSetAny(results[i])) {
					isEmpty = false;
					break;
				}
			}
		}

		if (isEmpty) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * 与えられた配列宣言の文字列から、配列のサイズを返す
	 * 
	 * type -> dim1: null, dim2: null type[12] -> dim1: 12, dim2: null
	 * type[12][2] -> dim1: 2, dim2: 12
	 * 
	 * @param arraystr
	 *            配列宣言の文字列
	 * @return 必ずString[2]を返すが、次元がなければ、要素にnullが入る。
	 */
	public static String[] getArray2Dimensions(String arraystr) {
		String[] retval = new String[2];
		retval[0] = null;
		retval[1] = null;

		if (StringTools.isSetAny(arraystr)) {
			String trimmedtype = arraystr.trim();

			if (trimmedtype
					.matches(".*\\[[\\s]*[0-9]*[\\s]*\\][\\s]*\\[[\\s]*[0-9]*[\\s]*\\]")) {
				// 二次元配列
				String[] results = trimmedtype.split("\\[", -1);
				if (results.length > 2) {
					if (StringTools.isSetAny(results[1])) {
						int index1 = results[1].indexOf(']');
						int index2 = results[2].indexOf(']');
						if (index1 > 0 && index2 > 0) {
							retval[1] = results[1].substring(0, index1).trim();
							retval[0] = results[2].substring(0, index2).trim();
							return retval;
						}
					}
				}
			} else if (trimmedtype.matches(".*[\\s]*\\[[\\s]*[0-9]*[\\s]*]")) {
				// 一次元配列 (二次元配列をチェックした後にチェックする)
				String[] results = trimmedtype.split("\\[", -1);
				if (results.length > 0) {
					if (StringTools.isSetAny(results[1])) {
						int index = results[1].indexOf(']');
						if (index > 0) {
							retval[0] = results[1].substring(0, index).trim();
							return retval;
						}
					}
				}
			}
		}

		return retval;
	}

	/**
	 * カンマで区切られたx, yからxおよびyを取り出して返す
	 * 
	 * @param str
	 *            x,y
	 * 
	 * @return 必ずString[2]を返す。[0]: x, [1]: y
	 */
	public static String[] getXYfromXcommaY(String str) {
		String[] retval = new String[2];
		retval[0] = null;
		retval[1] = null;

		if (isSetAny(str)) {
			String[] results = str.split(",", -1);
			if (results.length > 1) {
				retval[0] = results[0].trim();
				retval[1] = results[1].trim();
			}
		}

		return retval;
	}

	/**
	 * 与えられた文字列がback quoteされていたら、それを取り除く
	 * 
	 * @param str
	 *            　back single quoteを前後に含む文字列
	 * 
	 * @return 前後のback single quoteを取り除いた文字列、back quote
	 *         されていない場合は、str.trim()が返される
	 */
	public static String trimBackQuotes(String str) {
		String retstr = null;
		if (str != null) {
			String tmpstr;
			tmpstr = str.trim();
			if (tmpstr != null) {
				int begin, end;
				begin = tmpstr.indexOf('`');
				end = tmpstr.lastIndexOf('`');
				if (begin < 0) {
					// 'は存在しない
					begin = -1;
				}
				if (end < 0) {
					// 'は存在しない
					end = tmpstr.length();
				}

				if (begin != end) {
					retstr = tmpstr.substring(begin + 1, end);
				} else {
					// back single quote が一つしかなない場合は、なにもしない
					retstr = tmpstr;
				}
			}
		}
		return retstr;
	}

	/**
	 * 文字列の前後にあるかっこ{}を取り除く
	 * 
	 * @param str
	 * @return
	 */
	public static String trimBraces(String str) {
		String retstr = null;

		if (str != null) {
			String tmpstr;
			tmpstr = str.trim();
			if (tmpstr != null) {
				int begin, end;
				begin = tmpstr.indexOf('{');
				end = tmpstr.lastIndexOf('}');
				if (begin < 0) {
					// {は存在しない
					begin = -1;
				}
				if (end < 0) {
					// }は存在しない
					end = tmpstr.length();
				}

				if (begin < end) {
					retstr = tmpstr.substring(begin + 1, end);
				} else {
					retstr = tmpstr;
				}
			}
		}

		return retstr;
	}

	/**
	 * strが{中括弧}でくくられていたらtrueを返す
	 * <p>
	 * <ul>
	 * <li>"{a,b,c}" -> true</li>
	 * <li>"{a,b,c},{d,e,f}" -> false</li>
	 * </ul>
	 * 
	 * @param str
	 *            調べる文字列
	 * @return 中括弧でくくられていたらtrue, さもなければfalse
	 */
	public static boolean isBraced(String str) {

		if (str != null) {
			String trimmed = str.trim();

			if (trimmed.matches("\\{.*\\}")) {
				int level = 0; // 見つけた{の数
				boolean isEscaped = false;
				boolean isQuoted = false; // double quotationの中だとtrueとなる
				boolean isSquoted = false; // single quotationの中だとtrueとなる

				for (int i = 0; i < trimmed.length(); i++) {
					char c = trimmed.charAt(i);

					switch (c) {
					case '"':
						if (isEscaped) {
							isEscaped = false;
						} else {
							if (!isSquoted) {
								if (isQuoted) {
									isQuoted = false;
								} else {
									isQuoted = true;
								}
							}
						}
						break;
					case '\'':
						if (isEscaped) {
							isEscaped = false;
						} else {
							if (!isQuoted) {
								if (isSquoted) {
									isSquoted = false;
								} else {
									isSquoted = true;
								}
							}
						}
						break;
					case '\\':
						if (isEscaped) {
							isEscaped = false;
						} else {
							isEscaped = true;
						}
						break;
					case '{':
						if (isEscaped) {
							isEscaped = false;
						} else {
							level++;
						}
						break;
					case '}':
						if (isEscaped) {
							isEscaped = false;
						} else {
							level--;
							if (level == 0) {
								if (i + 1 < trimmed.length()
										&& StringTools.isSetAny(trimmed
												.substring(i + 1))) {
									return false;
								} else {
									return true;
								}
							}
						}
						break;
					default:
						if (isEscaped) {
							isEscaped = false;
						}
						break;
					}
				}
			}
		}

		return false;
	}

	/**
	 * 前後の空白および括弧を削除した文字列を返す
	 * 
	 * @param name
	 * @return
	 */
	public static String getTrimmedParameter(String name) {
		if (name != null) {
			return StringTools.trimBraces(name.trim()).trim();
		} else {
			return name;
		}
	}

	/**
	 * Truncate string to fit into 1280x1024 displays and to improve rendering
	 * performance
	 * 
	 * @param string
	 * @return truncated string
	 */
	public static String getTruncatedString(String string) {
		if (string != null) {
			boolean needTruncate = false;
			String[] strings = string.split("\n");
			for (int i = 0; i < strings.length; ++i) {
				if (strings[i].length() > 96 + 19) {
					needTruncate = true;
					break;
				}
			}
			if (needTruncate) {
				int len = string.length();
				if (string.length() > 96 + 19) {
					string = string.substring(0, 64) + "...(" + (len - 96)
							+ " characters)..."
							+ string.substring(len - 32, len);
				}
			}
		}
		return string;
	}

	public static String getNonNullString(String string) {
		if (string == null) {
			return "";
		}
		return string;
	}

	/**
	 * 指定された範囲を数値の配列として返す
	 * 
	 * @param str
	 *            (num1~num2またはnum)
	 * @return int型の配列
	 */
	public static int[] expandRegion(String str) {
		int[] list;

		if (!StringTools.isSetAny(str)) {
			return new int[0];
		}

		try {
			if (str.trim().matches("[+-]?[0-9]+[\\s]*~[+-]?[\\s]*[0-9]+")) {
				String[] result = str.split("~", -1);
				Assert.isTrue(result.length > 1);

				int fromNum = Integer.valueOf(stripPlusBeforeNumber(result[0]
						.trim()));
				int toNum = Integer.valueOf(stripPlusBeforeNumber(result[1]
						.trim()));

				int numItems = Math.abs(toNum - fromNum) + 1;
				list = new int[numItems];

				if (fromNum < toNum) {
					for (int i = 0; i < numItems; i++) {
						list[i] = fromNum;
						fromNum++;
					}
				} else {
					for (int i = 0; i < numItems; i++) {
						list[i] = fromNum;
						fromNum--;
					}
				}
			} else {
				int num = Integer.valueOf(stripPlusBeforeNumber(str.trim()));
				list = new int[1];
				list[0] = num;
			}
		} catch (NumberFormatException e) {
			list = new int[0];
		}

		return list;
	}

	/**
	 * 文字列配列をカンマ区切りの文字列に変更する<br>
	 * 文字列に含まれるカンマ(,)はバックスラッシュでエスケープ(\,)される
	 * 
	 * @param list
	 *            文字列の配列
	 * @return カンマ区切りの文字列 (nullは返さない)
	 */
	public static String encodeToCsv(String[] list) {
		if (list == null) {
			return "";
		}

		String result = "";

		for (int i = 0; i < list.length; i++) {
			String str = list[i];
			if (str == null) {
				str = "";
			}
			if (i != 0) {
				result += ",";
			}
			result += str.replace(",", "\\,"); // カンマをエスケープしたものを追加する
		}

		return result;
	}

	/**
	 * カンマ区切りの文字列を配列に変換する<br>
	 * 与えられた文字列にエスケープされたカンマ(\,)が存在するときは、カンマ(,)に戻される
	 * 
	 * @param encoded
	 *            入力文字列
	 * @return 結果の配列 (nullは返さない(要素0の配列を返す))
	 */
	public static String[] decodeFromCsv(String encoded) {

		if (!StringTools.isSetAny(encoded)) {
			return new String[0];
		}

		List<String> result = new ArrayList<String>();
		String element = "";
		boolean readEscape = false;
		for (int i = 0; i < encoded.length(); i++) {
			char c = encoded.charAt(i);
			if (c == '\\') {
				readEscape = true;
			} else if (c == ',') {
				if (readEscape) {
					element += c;
				} else {
					// カンマを読み込んだ
					result.add(element);
					element = "";
				}
				readEscape = false;
			} else {
				if (readEscape) {
					readEscape = false;
				}
				element += c;
			}
		}
		result.add(element);

		if (result.size() > 0) {
			return result.toArray(new String[result.size()]);
		}

		return new String[0];
	}

	/**
	 * 比較用の文字列を返す
	 * 
	 * @param str
	 * @return trim()された文字列、または空文字 (nullは返さない)
	 */
	public static String getStringToCompare(String str) {
		String result = str == null ? "" : str.trim();
		return result;
	}

	/**
	 * 文字列に存在する'='を'\' + '='に変換する
	 * 
	 * @param 入力文字列
	 * @return 変換後の文字列
	 */
	public static String escapeEqual(String src) {
		if (StringTools.isSetAny(src)) {
			return src.replace("=", "\\=");
		}

		return src;
	}

	/**
	 * 文字列を=で分割する
	 * 
	 * (エスケープされた=は、=として扱わない)
	 * 
	 * @param src
	 *            入力文字列
	 * @return Entry<左側の文字列, 右側の文字列>またはnull
	 */
	public static Entry<String, String> splitWithEqualWithEscape(String src) {
		if (StringTools.isSetAny(src)) {
			String key;
			String value;
			for (int pos = 0; pos < src.length(); pos++) {
				char c = src.charAt(pos);
				if (c == '\\') {
					pos++;
					if (pos >= src.length()) {
						break;
					}
					continue;
				} else if (c == '=') {
					key = src.substring(0, pos);
					value = src.substring(pos + 1);

					return new AbstractMap.SimpleImmutableEntry<String, String>(
							key, value);
				}
			}
		}

		return null;
	}

	/**
	 * 文字列を=で分割する
	 * 
	 * @param src
	 *            入力文字列
	 * @return Entry<左側の文字列, 右側の文字列>またはnull
	 */
	public static Entry<String, String> splitWithEqual(String src) {
		if (StringTools.isSetAny(src)) {
			int pos = src.indexOf('=');
			if (pos >= 0) {
				String key = src.substring(0, pos);
				String value = src.substring(pos + 1);
				return new AbstractMap.SimpleImmutableEntry<String, String>(
						key, value);
			}
		}

		return null;
	}

	/**
	 * 与えられたmapをCSV形式に変換する
	 * 
	 * @param map
	 * @return CSV形式の文字列
	 */
	public static <V> String getCsvOf(Map<String, V> map) {
		Assert.isNotNull(map);
		List<String> stringEntries = new ArrayList<String>();
		Set<Entry<String, V>> entrySet = map.entrySet();
		for (Entry<String, V> entry : entrySet) {
			String key = StringTools.escapeEqual(entry.getKey());
			V value = entry.getValue();
			stringEntries.add(String.format("%1$s=%2$s", key, value));
		}
		String[] entries = stringEntries.toArray(new String[stringEntries
				.size()]);
		String csvstr = StringTools.encodeToCsv(entries);

		return csvstr;
	}

	/**
	 * 与えられたCSV形式文字列をmapにセットする
	 * 
	 * @param csvstr
	 * @param map
	 *            <String, Integer>のマップ
	 */
	public static void setDataFromCsv(String csvstr, Map<String, Integer> map) {
		Assert.isNotNull(map);

		String[] encoded = StringTools.decodeFromCsv(csvstr);
		if (encoded != null) {
			for (int i = 0; i < encoded.length; i++) {
				Entry<String, String> entry = StringTools
						.splitWithEqualWithEscape(encoded[i]);
				if (entry != null) {
					try {
						Integer value = Integer.valueOf(entry.getValue());
						map.put(entry.getKey(), value);
					} catch (NumberFormatException e) {
						// do nothing
					}
				}
			}
		}
	}

	/**
	 * 渡された文字列に値がセットされているかを調べる
	 * 
	 * @param str
	 *            調べる文字列を格納した大きさ1の配列
	 * @return str[0]がnullでなく、空文字でなければtrueを返す
	 */
	private static boolean hasValue(String[] str) {
		return (str[0] != null && !str[0].isEmpty());
	}

	/**
	 * 渡された文字列に値がセットされているかを調べる
	 * 
	 * @param str
	 *            調べる文字列
	 * @return strがnullでなく、空文字でなければtrueを返す
	 */
	private static boolean hasValue(String str) {
		return (str != null && !str.isEmpty());
	}

	/**
	 * 不要な+記号を削除する
	 * 
	 * @param str
	 * @return
	 */
	private static String stripPlusBeforeNumber(String str) {
		String instr = str.trim();

		if (isSetAny(instr)) {
			if (instr.matches("\\+[\\s]*[0-9]+")) {
				String[] results = instr.split("\\+", -1);
				if (results.length > 1) {
					return results[1];
				}
			}
		}

		return str;
	}

}
