package com.example.dsf.gdb;

import org.eclipse.cdt.dsf.concurrent.ThreadSafe;
import org.eclipse.cdt.dsf.gdb.internal.ui.GdbAdapterFactory;
import org.eclipse.cdt.dsf.gdb.internal.ui.GdbSessionAdapters;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.debug.core.ILaunch;

@SuppressWarnings("restriction")
@ThreadSafe
public class GdbExtendedAdapterFactory extends GdbAdapterFactory {
	@Override
	protected GdbSessionAdapters createGdbSessionAdapters(ILaunch launch, DsfSession session) {
		return new GdbExtendedSessionAdapters(launch, session, getAdapterList());
	}
}
