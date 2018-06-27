package common.platform;

/**
 * This is for default platform
 *
 */
public class DefaultPlatform implements IPlatform {

	@Override
	public String getCLevelName() {
		return "gnu.c.optimization.level.none";
	}

	@Override
	public String getCppLevelName() {
		return "gnu.cpp.compiler.optimization.level.none";
	}

	@Override
	public IResourceResolver getCurrentResourceResolver() {
		// TODO Auto-generated method stub
		return null;
	}
}