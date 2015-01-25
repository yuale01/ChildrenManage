package com.yuale01.mis.resources;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class APIApplication extends ResourceConfig
{
	public APIApplication ()
	{
		register(ChildrenResource.class);
		
		register(JacksonJsonProvider.class);
		
		register(LoggingFilter.class);
	}

}
