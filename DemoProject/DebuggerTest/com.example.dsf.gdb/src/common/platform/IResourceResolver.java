package common.platform;

public interface IResourceResolver {
	String getAlgorithmLibraryLog(String library, boolean isCpp);
	
	String getAlgorithmLibraryOpt(String library, boolean isCpp);
	
	String getAlgorithmLibraryExe(String library, boolean isCpp);
	
	String getFramework(String fwk);
	
	// Full path without suffix needs to be specified through the RUN command
	String getFrameworkLog(String frameworkFile);
	
	// Full path without suffix needs to be specified through the RUN command
	String getTestPlanConstant(String testPlanConstantFile);
	
	// Full path without suffix needs to be specified through the RUN command
	String getTestPlanConstantLog(String testPlanConstantFile);
	
	// Full path without suffix needs to be specified through the RUN command
	String getLimit(String limitFile);
	
	// Full path without suffix needs to be specified through the RUN command
	String getLimitLog(String limitFile);
}
