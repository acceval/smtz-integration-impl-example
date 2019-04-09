package com.acceval.core.email;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.CompositeTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * Configuration for email client.
 *
 * @author Julian
 */
@Lazy
@Configuration
public class EmailConfiguration {
	@Bean
	EmailSender emailSender(TemplateRenderer renderer) {
		return new DefaultEmailSender(renderer, emailQueueSender());
	}

	@Bean
	TemplateRenderer handlebarsRenderer(Handlebars handlebars) {
		return new HandlebarsTemplateRenderer(handlebars);
	}

	@Bean
	EmailQueueSender emailQueueSender() {
		return new EmailQueueSender();
	}


	// handlebars
	@Bean
	TemplateLoader rootClasspathTemplateLoader() {
		TemplateLoader loader = new ClassPathTemplateLoader();
		loader.setPrefix("");
		loader.setSuffix(".html");

		return loader;
	}

	@Bean
	Handlebars handlebars(List<TemplateLoader> templateLoaders) {
		TemplateLoader loader;
		if (templateLoaders.size() == 1) {
			loader = templateLoaders.get(0);
		} else {
			loader = new CompositeTemplateLoader(templateLoaders.toArray(new TemplateLoader[0]));
		}
		return new Handlebars(loader);
	}
}
