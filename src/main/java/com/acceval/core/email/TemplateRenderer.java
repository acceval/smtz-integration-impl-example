package com.acceval.core.email;

import java.io.IOException;

/**
 * @author Julian
 */
public interface TemplateRenderer {
	String render(String templateFile, Object context) throws IOException;
}
