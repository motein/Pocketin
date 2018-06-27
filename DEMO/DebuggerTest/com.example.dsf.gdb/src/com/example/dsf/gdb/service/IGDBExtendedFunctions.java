package com.example.dsf.gdb.service;

import org.eclipse.cdt.dsf.concurrent.DataRequestMonitor;
import org.eclipse.cdt.dsf.concurrent.RequestMonitor;
import org.eclipse.cdt.dsf.debug.service.command.ICommandControlService.ICommandControlDMContext;
import org.eclipse.cdt.dsf.service.IDsfService;

public interface IGDBExtendedFunctions extends IDsfService {
	/**
	 * Request a notification to the user
	 */
	void notify(ICommandControlDMContext ctx, String str, RequestMonitor rm);
	
	/**
	 * Get the version of the debugger
	 */
	void getVersion(ICommandControlDMContext ctx, DataRequestMonitor<String> rm);

	/**
	 * Can get the version of the debugger
	 */
	void canGetVersion(ICommandControlDMContext ctx, DataRequestMonitor<Boolean> rm);

}