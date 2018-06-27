package com.micro;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.micro.cors.CorsFilter;
import com.micro.resource.BookResource;
import com.micro.resource.SayHiResource;

@Component
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(SayHiResource.class);
		register(BookResource.class);
		register(CorsFilter.class);
	}
}
