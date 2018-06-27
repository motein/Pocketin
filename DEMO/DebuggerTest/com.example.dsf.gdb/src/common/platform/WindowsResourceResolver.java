package common.platform;

import java.io.File;

public class WindowsResourceResolver implements IResourceResolver {
	private final static String USR_FOLDER = "C:/ProgramData/SPECSX/usr/";
	
	public String getAlgorithmLibraryLog(String library, boolean isCpp) {
		String path = isCpp ? USR_FOLDER + "cppalg/" : USR_FOLDER + "calg/";
		String algorithmLibrary = "";
		if (library != null && library.length() != 0) {
			if (library.charAt(1) == ':') {
				algorithmLibrary = library;
			} else {
				algorithmLibrary = path + library;
			}
		}
		return algorithmLibrary;
	}
	
	public String getAlgorithmLibraryOpt(String library, boolean isCpp) {
		String path = isCpp ? USR_FOLDER + "cppalg/" : USR_FOLDER + "calg/";
		String algorithmLibrary = "";
		if (library != null && library.length() != 0) {
			if (library.charAt(1) == ':') {
				String[] tokens = library.split("/");
				String lib = "";
				if (tokens.length > 0) {
					lib = tokens[tokens.length - 1];
				}
				File f = new File(library);
				if (f.isDirectory()) {
					algorithmLibrary = library + "/Debug/" + lib;
				} else {
					algorithmLibrary = library;
				}
			} else {
				String[] tokens = library.split("/");
				String lib = "";
				if (tokens.length > 0) {
					lib = tokens[tokens.length - 1];
				}
				File f = new File(path + library);
				if (f.isDirectory()) {
					if (isCpp) {
						algorithmLibrary = path + library + "/Debug/" + lib;
					} else {
						algorithmLibrary = library + "/Debug/" + lib;
					}
				} else {
					if (isCpp) {
						algorithmLibrary = path + library;
					} else {
						algorithmLibrary = library;
					}
				}
			}
		}
		return algorithmLibrary;
	}
	
	public String getAlgorithmLibraryExe(String library, boolean isCpp) {
		String path = isCpp ? USR_FOLDER + "cppalg/" : USR_FOLDER + "calg/";
		
		String algorithmLibrary = "";
		if (library != null && library.length() != 0) {
			if (library.charAt(1) == ':') {
				String[] tokens = library.split("/");
				String lib = "";
				if (tokens.length > 0) {
					lib = tokens[tokens.length - 1];
				}
				
				File f = new File(library);
				if (f.isDirectory()) {
					algorithmLibrary = library + "/Debug/" + lib;
				} else {
					algorithmLibrary = library;
				}
			} else {
				String[] tokens = library.split("/");
				String lib = "";
				if (tokens.length > 0) {
					lib = tokens[tokens.length - 1];
				}
				File f = new File(path + library);
				if (f.isDirectory()) {
					algorithmLibrary = path + library + "/Debug/" + lib;
				} else {
					algorithmLibrary = path + library;
				}
			}
		}
		
		return algorithmLibrary;
	}
	
	public String getFramework(String fwk) {
		String path = USR_FOLDER + "fwk/";
		String framework = "";
		if (fwk != null && fwk.length() != 0) {
			if (fwk.charAt(1) == ':') {
				framework = fwk;
			} else {
				framework = path + fwk;
			}
		}
		return framework;
	}
	
	public String getFrameworkLog(String frameworkFile) {
		String path = USR_FOLDER + "fwk/";
		String framework = "";
		if (frameworkFile.length() >= 4 && frameworkFile.endsWith(".fwk")) {
			frameworkFile = frameworkFile.substring(0,
					frameworkFile.length() - 4);
		}
		if (frameworkFile != null && frameworkFile.length() != 0) {
			if (frameworkFile.charAt(1) == ':') {
				framework = frameworkFile;
			} else {
				framework = path + frameworkFile;
			}
		}
		return framework;
	}
	
	public String getTestPlanConstant(String testPlanConstantFile) {
		String path = USR_FOLDER + "tco/";
		String testPlanConstant = "";
		if (testPlanConstantFile != null && testPlanConstantFile.length() != 0) {
			if (testPlanConstantFile.length() < 4
					|| !testPlanConstantFile.endsWith(".tco")) {
				testPlanConstantFile = testPlanConstantFile + ".tco";
			}
			if (testPlanConstantFile.charAt(1) == ':') {
				testPlanConstant = testPlanConstantFile;
			} else {
				testPlanConstant = path + testPlanConstantFile;
			}
		}
		return testPlanConstant;
	}
	
	public String getTestPlanConstantLog(String testPlanConstantFile) {
		String path = USR_FOLDER + "tco/";
		String testPlanConstant = "";
		if (testPlanConstantFile != null && testPlanConstantFile.length() != 0) {
			if (testPlanConstantFile.length() < 4
					|| !testPlanConstantFile.endsWith(".tco")) {
				testPlanConstantFile = testPlanConstantFile + ".tco";
			}
			if (testPlanConstantFile.charAt(1) == ':') {
				testPlanConstant = testPlanConstantFile;
			} else {
				testPlanConstant = path + testPlanConstantFile;
			}
		}
		if (testPlanConstant.length() >= 4 && testPlanConstant.endsWith(".tco")) {
			testPlanConstant = testPlanConstant.substring(0,
					testPlanConstant.length() - 4);
		}
		return testPlanConstant;
	}
	
	public String getLimit(String limitFile) {
		String path = USR_FOLDER + "lim/";
		String limit = "";
		if (limitFile != null && limitFile.length() != 0) {
			if (limitFile.length() < 5 || !limitFile.endsWith(".limx")) {
				limitFile = limitFile + ".limx";
			}
			if (limitFile.charAt(1) == ':') {
				limit = limitFile;
			} else {
				limit = path + limitFile;
			}
		}
		return limit;
	}
	
	public String getLimitLog(String limitFile) {
		String path = USR_FOLDER + "lim/";
		String limit = "";
		if (limitFile != null && limitFile.length() != 0) {
			if (limitFile.length() < 5 || !limitFile.endsWith(".limx")) {
				limitFile = limitFile + ".limx";
			}
			if (limitFile.charAt(1) == ':') {
				limit = limitFile;
			} else {
				limit = path + limitFile;
			}
		}
		if (limit.length() >= 5 && limit.endsWith(".limx")) {
			limit = limit.substring(0, limit.length() - 5);
		}
		return limit;
	}
}
