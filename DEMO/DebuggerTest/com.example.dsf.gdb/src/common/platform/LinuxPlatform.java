package common.platform;

public abstract class LinuxPlatform implements IPlatform {
	private static IResourceResolver resourceResolver = null;
	@Override
	public synchronized IResourceResolver getCurrentResourceResolver() {
		if(resourceResolver == null)
			resourceResolver =  new LinuxResourceResolver();
		
		return resourceResolver;
	}
}
