package common.platform;

import tools.StringTools;

/**
 * This class use singleton pattern to provide the interface of current platform to user. 
 *
 */
public final class PlatformManager {
	
	private static IPlatform currentPlatform;
	
	private PlatformManager() {}
	
	public static synchronized IPlatform getCurrentPlatform()
	{
		if(currentPlatform == null)
		{
			setCurrentPlatform(StringTools.getOsVersion());
		}		
		return currentPlatform;
	}
	
	private static void setCurrentPlatform(String os)
	{
		try
		{
			SupportedPlatformEnum current = SupportedPlatformEnum.valueOf(os);
			switch(current){
				case RHEL5:
					currentPlatform = new RHEL5Platform();
					break;
				case RHEL7:
					currentPlatform = new RHEL7Platform();
					break;
				case WINDOWS10:
					currentPlatform = new Windows10Platform();
					break;
				default:						
					currentPlatform = new DefaultPlatform();
			}		
		}
		catch(IllegalArgumentException ex)
		{
			currentPlatform = new DefaultPlatform();
		}	
	}
}
	