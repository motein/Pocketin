package common.platform;

/**
 * This is for platform RHEL5
 *
 */
public class RHEL5Platform extends LinuxPlatform {

	@Override
	public String getCLevelName() {		
		return "gnu.c.optimization.level.optimize"; // -O1
	}

	@Override
	public String getCppLevelName() {
		return "gnu.cpp.compiler.optimization.level.optimize"; // -O1
	}
}
