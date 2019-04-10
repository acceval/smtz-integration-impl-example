package com.acceval.core.email;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

import java.io.IOException;

/**
 * Renders using handlebars
 *
 * @author Julian
 */
public class HandlebarsTemplateRenderer implements TemplateRenderer {
    private final Handlebars handlebars;

    public HandlebarsTemplateRenderer(Handlebars handlebars) {
        this.handlebars = handlebars;
    }

    public String render(String templateFile, EmailContentData data) throws IOException {
        Template tmpl = handlebars.compile(templateFile);

        return tmpl.apply(data);
    }
}
