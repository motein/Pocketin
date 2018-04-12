package web.configuration;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import web.exceptions.FileUploadExceptionMapper;
import web.resource.FileUploadResource;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {

        // Register the Resource:
        register(FileUploadResource.class);

        // Register the Feature for Multipart Uploads (File Upload):
        register(MultiPartFeature.class);

        // Register Exception Mappers for returning API Errors:
        register(FileUploadExceptionMapper.class);

        // Uncomment to disable WADL Generation:
        //property("jersey.config.server.wadl.disableWadl", true);

        // Uncomment to add Request Tracing:
        //property("jersey.config.server.tracing.type", "ALL");
        //property("jersey.config.server.tracing.threshold", "TRACE");
    }
}