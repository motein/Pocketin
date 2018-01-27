package com.example.dsf.gdb.service.command.commands;

import org.eclipse.cdt.dsf.debug.service.command.ICommandControlService.ICommandControlDMContext;
import org.eclipse.cdt.dsf.mi.service.command.commands.MIInterpreterExecConsole;
import org.eclipse.cdt.dsf.mi.service.command.output.MIGDBVersionInfo;
import org.eclipse.cdt.dsf.mi.service.command.output.MIInfo;
import org.eclipse.cdt.dsf.mi.service.command.output.MIOutput;

/**
 * We use -interpreter-exec console "show version" instead
 * of -gdb-version to avoid having the output automatically printed
 * to our console.
 * 
 */
public class CLIGDBVersion extends MIInterpreterExecConsole<MIGDBVersionInfo> {
	private static final String COMMAND = "show version"; //$NON-NLS-1$
	
	public CLIGDBVersion(ICommandControlDMContext ctx) {
        super(ctx, COMMAND);
    }

	@Override
	public MIInfo getResult(MIOutput out) {
		return new MIGDBVersionInfo(out);
	}
}