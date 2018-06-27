package com.example.dsf.gdb.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ISourceLocator;

import com.example.dsf.gdb.service.GdbExtendedDebugServicesFactory;

import main.Activator;

import org.eclipse.cdt.dsf.concurrent.Sequence;
import org.eclipse.cdt.dsf.debug.service.IDsfDebugServicesFactory;
import org.eclipse.cdt.dsf.gdb.launching.GdbLaunch;
import org.eclipse.cdt.dsf.gdb.launching.GdbLaunchDelegate;
import org.eclipse.cdt.dsf.service.DsfSession;

public class GdbExtendedLaunchDelegate extends GdbLaunchDelegate {

	public GdbExtendedLaunchDelegate() {
		super();
	}

    @Override
	protected GdbLaunch createGdbLaunch(ILaunchConfiguration configuration, String mode, ISourceLocator locator) throws CoreException {
    	return new GdbExtendedLaunch(configuration, mode, locator);
    }

    @Override
    protected Sequence getServicesSequence(DsfSession session, ILaunch launch, IProgressMonitor rm) {
   		return new GdbExtendedServicesLaunchSequence(session, (GdbLaunch)launch, rm);
    }

    @Override
	protected IDsfDebugServicesFactory newServiceFactory(ILaunchConfiguration config, String version) {
		return new GdbExtendedDebugServicesFactory(version, config);
	}

	@Override
	protected String getPluginID() {
		return Activator.PLUGIN_ID;
	}
}
