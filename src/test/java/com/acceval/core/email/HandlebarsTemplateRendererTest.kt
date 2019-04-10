package com.acceval.core.email

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.io.ClassPathTemplateLoader
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.io.IOException

import org.junit.jupiter.api.Assertions.*
import java.io.File
import java.nio.charset.Charset
import java.util.HashMap

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

        val data = EmailContentData()
        var context = HashMap<String, Any>()
        data.text = "my custom content"
        data.title = "my title"
        data.context = context

        var param2Object = Param2Class()
        param2Object.name = "Param 2 name"

        context.put("PARAM1", "Param 1 value")
        context.put("PARAM2", param2Object)

		val value = renderer.render("default", data)

		val expected = javaClass.getResourceAsStream("/golden/default.html.golden").use {
			it.readBytes().toString(Charset.defaultCharset())
		}

		assertEquals(expected, value)
	}

    @Test
    @Throws(IOException::class)
    fun renderInheritance() {

        val data = EmailContentData()
        var context = HashMap<String, Any>()
        data.text = "my custom content"
        data.title = "my title"
        data.context = context

        var param2Object = Param2Class()
        param2Object.name = "Param 2 name"

        context.put("PARAM1", "Param 1 value")
        context.put("PARAM2", param2Object)

        val value = renderer.render("template1", data)

        val expected = javaClass.getResourceAsStream("/golden/template1.html.golden").use {
            it.readBytes().toString(Charset.defaultCharset())
        }

        assertEquals(expected, value)
    }
}

class Param2Class {
    var name: String = ""
}