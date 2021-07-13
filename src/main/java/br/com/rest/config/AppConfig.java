package br.com.rest.config;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig {

	public AppConfig() {

		packages("br.com.rest");

		register(MultiPartFeature.class);

		System.out.println("AppConfig Criado!");
	}

}
