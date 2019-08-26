package com.acceval.core.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import com.acceval.core.model.OwnerEntity;
import com.acceval.core.model.OwnerModel;

public class TemplateUtil {
	
	public static String getEntityCode(Class<?> entityClass) {
		
		StringBuilder builder = new StringBuilder(entityClass.getSimpleName());
		
		for (int i = 1; i < builder.length() - 1; i++) {
			if (isUnderscoreRequired(builder.charAt(i - 1), builder.charAt(i), builder.charAt(i + 1))) {
				builder.insert(i++, '_');
			}
		}
		return builder.toString().toLowerCase(); 
		
	}
	
	private static boolean isUnderscoreRequired(char before, char current, char after) {
		
		return (Character.isLowerCase(before) && Character.isUpperCase(current)
				&& Character.isLowerCase(after));
	}	

	public static List<Class<?>> getDocumentClassesFromPackage(String packageName) 
			throws ClassNotFoundException, IOException, URISyntaxException {


		Reflections reflections = new Reflections(packageName);
	    final Set<Class<?>> documentClasses = reflections.getTypesAnnotatedWith(Document.class);
	    	    
	    return new ArrayList<Class<?>>(documentClasses);

	}
	
	public static List<Class<?>> getEntityClassesFromPackage(String packageName) 
			throws ClassNotFoundException, IOException, URISyntaxException {

		Reflections reflections = new Reflections(packageName);
	    final Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);
	    	    
	    return new ArrayList<Class<?>>(entityClasses);
	}
	
	public static List<Class<?>> getOwnerClassesFromPackage(String packageName) 
			throws ClassNotFoundException, IOException, URISyntaxException {

		Reflections reflections = new Reflections(packageName);

		final Set<Class<? extends OwnerModel>> ownerModelClasses = reflections.getSubTypesOf(OwnerModel.class);
		final Set<Class<? extends OwnerEntity>> ownerEntityClasses = reflections.getSubTypesOf(OwnerEntity.class);
						
	    List<Class<?>> ownerClasses = new ArrayList<Class<?>>();
	    
	    ownerClasses.addAll(ownerModelClasses);
	    ownerClasses.addAll(ownerEntityClasses);

	    return ownerClasses;
	}
	
	public static Set<Class<?>> getClassesFromPackage(String packageName) {
		 
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		/* don't exclude Object.class */
		Reflections reflections = new Reflections(new ConfigurationBuilder()
		    .setScanners(new SubTypesScanner(false), new ResourcesScanner())
		    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
		    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName))));
	    				

		Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);
				
		return allClasses;
	}
	
}
