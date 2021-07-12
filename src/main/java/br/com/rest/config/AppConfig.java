package br.com.rest.config;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

@ApplicationPath("")
public class AppConfig extends Application{

	private Set<Class<?>> resources = new HashSet<Class<?>>();
	
	@SuppressWarnings("unchecked")
	public AppConfig() {
		
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());
		
		Reflections reflections = new Reflections(new ConfigurationBuilder()
			    .setScanners(new SubTypesScanner(false), new ResourcesScanner())
			    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
			    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("br.com.rest.api"))));

		Set<Class<? extends Object>> allClasses = 
		     reflections.getSubTypesOf(Object.class);
		 
		for(Object o : allClasses) {
			resources.add((Class<Object>) o);
		}
		resources.add(JacksonFeature.class);

		
		System.out.println("AppConfig Criado!");
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		// TODO Auto-generated method stub
		return resources;
	}
		

}
