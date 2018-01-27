package common.platform;


/**
 * All platform related interfaces will be here
 * @author mingfang
 *
 */
public interface IPlatform {

	/**
	 * 
	 * @return a string like "gnu.c.optimization.level.optimize"
	 */
	String getCLevelName();
	
	
	/**
	 * 
	 * @return a string like "gnu.cpp.compiler.optimization.level.optimize"
	 */
	String getCppLevelName();
	
	/**
	 * 
	 * @return a reference to IResourceResolver implementation class object
	 */
	IResourceResolver getCurrentResourceResolver();
}
