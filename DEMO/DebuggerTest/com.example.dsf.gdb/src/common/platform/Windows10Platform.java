package common.platform;

/**
 * This is for platform Windows10
 *
 */
public class Windows10Platform implements IPlatform {
	private IResourceResolver resourceResolver = null;
	
	@Override
	public String getCLevelName() {
		// TODO Auto-generated method stub
		return "gnu.c.optimization.level.none";
	}

	@Override
	public String getCppLevelName() {
		// TODO Auto-generated method stub
		return "gnu.cpp.compiler.optimization.level.none";
	}

	@Override
	public synchronized IResourceResolver getCurrentResourceResolver() {
		if (resourceResolver == null)
			resourceResolver = new WindowsResourceResolver();
		
		return resourceResolver;
	}
}
