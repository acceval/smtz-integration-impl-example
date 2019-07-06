package com.acceval.core.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
	    
		List<String> classNames = getClassNamesFromPackage(packageName);
	    List<Class<?>> classes = new ArrayList<Class<?>>();
	    
	    for (String className : classNames) {
	        Class<?> cls = Class.forName(packageName + "." + className);
	        Annotation[] annotations = cls.getAnnotations();

	        for (Annotation annotation : annotations) {
	            System.out.println(cls.getCanonicalName() + ": " + annotation.toString());
	            if (annotation instanceof org.springframework.data.mongodb.core.mapping.Document) {
	                classes.add(cls);
	            }
	        }
	    }

	    return classes;
	}
	
	public static List<Class<?>> getEntityClassesFromPackage(String packageName) 
			throws ClassNotFoundException, IOException, URISyntaxException {
	    
		List<String> classNames = getClassNamesFromPackage(packageName);
	    List<Class<?>> classes = new ArrayList<Class<?>>();
	    
	    for (String className : classNames) {
	        Class<?> cls = Class.forName(packageName + "." + className);
	        Annotation[] annotations = cls.getAnnotations();

	        for (Annotation annotation : annotations) {
	            System.out.println(cls.getCanonicalName() + ": " + annotation.toString());
	            if (annotation instanceof javax.persistence.Entity) {
	                classes.add(cls);
	            }
	        }
	    }

	    return classes;
	}
	
	public static List<String> getClassNamesFromPackage(String packageName) 
			throws IOException, URISyntaxException, ClassNotFoundException {
		
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    ArrayList<String> names = new ArrayList<String>();

	    packageName = packageName.replace(".", "/");
	    URL packageURL = classLoader.getResource(packageName);

	    URI uri = new URI(packageURL.toString());
	    File folder = new File(uri.getPath());
	    File[] files = folder.listFiles();
	    for (File file: files) {
	        String name = file.getName();
	        if (name.lastIndexOf('.') == -1) {
	        	continue;
	        }
	        name = name.substring(0, name.lastIndexOf('.'));
	        names.add(name);
	    }

	    return names;
	}

}
