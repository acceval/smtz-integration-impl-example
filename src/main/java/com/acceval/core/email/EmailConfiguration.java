package com.acceval.core.email;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.acceval.core.util.NumberUtil;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.CompositeTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

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
		loader.setPrefix("/emailTemplates");
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
		
		Handlebars handlebars = new Handlebars(loader);
		handlebars.registerHelper("format2Decimal", new Helper<Double>() {
            @Override
            public Object apply(Double context, Options options) throws IOException {
                return context == null? "0.00" : NumberUtil.formatNumber(context, 2);
            }
        });
		
		return handlebars;
	}
}
