package com.example.dsf.gdb.viewmodel;

import org.eclipse.osgi.util.NLS;

/**
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class GdbExtendedVMMessages extends NLS {
    public static String ThreadVMNode_No_columns__text_format;
    public static String ThreadVMNode_No_columns__Error__label;
    public static String ContainerVMNode_No_columns__text_format;
    public static String ContainerVMNode_No_columns__Error__label;
    /** since 2.3 */
    public static String ContainerVMNode_filtered_running_threads;
    
    static {
        // initialize resource bundle
        NLS.initializeMessages(GdbExtendedVMMessages.class.getName(), GdbExtendedVMMessages.class);
    }

    private GdbExtendedVMMessages() {
    }
}
