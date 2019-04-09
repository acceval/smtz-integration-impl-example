package com.acceval.core.email

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.io.ClassPathTemplateLoader
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.io.IOException

import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.nio.charset.Charset

/**
 * @author Julian
 */
internal class HandlebarsTemplateRendererTest {

	private lateinit var renderer: HandlebarsTemplateRenderer

	@BeforeEach
	fun setUp() {
		val templateLoader = ClassPathTemplateLoader("/testTemplates", ".html")
		val handlebars = Handlebars(templateLoader)

		this.renderer = HandlebarsTemplateRenderer(handlebars)
	}

	@Test
	@Throws(IOException::class)
	fun render() {
		val value = renderer.render("default", "my custom content")

		val expected = javaClass.getResourceAsStream("/golden/default.html.golden").use {
			it.readBytes().toString(Charset.defaultCharset())
		}

		assertEquals(expected, value)
	}
}
