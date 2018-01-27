package com.example.dsf.gdb.commands;

import org.eclipse.debug.ui.actions.DebugCommandHandler;

public class ShowVersionHandler extends DebugCommandHandler {

	@Override
	protected Class<IShowVersionHandler> getCommandType() {
		return IShowVersionHandler.class;
	}
}
