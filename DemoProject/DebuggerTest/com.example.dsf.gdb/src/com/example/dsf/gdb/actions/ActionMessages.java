package com.example.dsf.gdb.actions;

import org.eclipse.osgi.util.NLS;

public class ActionMessages extends NLS {
	static {
		// initialize resource bundle
		NLS.initializeMessages(ActionMessages.class.getName(), ActionMessages.class);
	}

	private ActionMessages() {
	}

	public static String DsfExtendedTerminateCommand_Confirm_Termination;
	public static String DsfExtendedTerminateCommand_Terminate_the_session;
}
