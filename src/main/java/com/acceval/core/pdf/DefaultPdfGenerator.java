package com.acceval.core.pdf;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.context.Context;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.StringTemplateResolver;


public class DefaultPdfGenerator implements PdfGenerator {
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
		
	public void generatePdf(PdfGeneratorRequest request) {
				
		Map<String, Object> variableContext = request.getVariableContext();
		
		String htmlBody = this.generateHtmlWithContext(request.getBodyTemplate(), variableContext);
		String htmlHeader = this.generateHtmlWithContext(request.getHeaderTemplate(), variableContext);
		String htmlFooter = this.generateHtmlWithContext(request.getFooterTemplate(), variableContext);

		request.setHtmlBody(htmlBody);
		request.setHtmlFooter(htmlFooter);
		request.setHtmlHeader(htmlHeader);
	}
	

	private String generateHtmlWithContext(String html, Map<String, Object> context) {
				
		Context templateContext = new Context();
		templateContext.setVariables(context);

		String processedHtml = templateEngine.process(html, templateContext);

		return processedHtml;
	}

}
