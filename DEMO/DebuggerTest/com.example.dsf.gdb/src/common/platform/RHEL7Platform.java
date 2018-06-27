package common.platform;

/**
 * This is for platform RHEL7
 *
 */
public class RHEL7Platform extends LinuxPlatform {

	@Override
	public String getCLevelName() {
		return "gnu.c.optimization.level.none"; // -O0
	}

	@Override
	public String getCppLevelName() {		
		return "gnu.cpp.compiler.optimization.level.none";// -O0
	}
}
