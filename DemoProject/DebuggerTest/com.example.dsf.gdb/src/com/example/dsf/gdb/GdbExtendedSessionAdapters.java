package com.example.dsf.gdb;

import java.util.List;

import org.eclipse.cdt.dsf.gdb.internal.ui.GdbSessionAdapters;
import org.eclipse.cdt.dsf.service.DsfSession;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.commands.ITerminateHandler;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IViewerInputProvider;

import com.example.dsf.gdb.actions.DsfExtendedTerminateCommand;
import com.example.dsf.gdb.actions.GdbShowVersionHandler;
import com.example.dsf.gdb.commands.IShowVersionHandler;
import com.example.dsf.gdb.viewmodel.GdbExtendedViewModelAdapter;

@SuppressWarnings("restriction")
public class GdbExtendedSessionAdapters extends GdbSessionAdapters {
    
	public GdbExtendedSessionAdapters(ILaunch launch, DsfSession session, Class<?>[] launchAdapterTypes) {
		super(launch, session, launchAdapterTypes);
	}
    
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T createModelAdapter(Class<T> adapterType, ILaunch launch, DsfSession session) {
		if (ITerminateHandler.class.equals(adapterType)) { 
			return (T)new DsfExtendedTerminateCommand(session);
		}
		if (IViewerInputProvider.class.equals(adapterType)) {
			return (T)new GdbExtendedViewModelAdapter(session, getSteppingController());
		}
		if (IShowVersionHandler.class.equals(adapterType)) {
			return (T)new GdbShowVersionHandler(session);
		}

		return super.createModelAdapter(adapterType, launch, session);
	}

	@Override
	protected List<Class<?>> getModelAdapters() {
		List<Class<?>> modelAdapters = super.getModelAdapters();
		modelAdapters.add(IShowVersionHandler.class);
		return modelAdapters;
	}
}
