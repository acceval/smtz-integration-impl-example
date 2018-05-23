package com.acceval.core.jackson.deserializer

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext
import com.fasterxml.jackson.databind.module.SimpleModule
import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class APILocalDateTimeJsonDeserializerTest {
    private val out = APILocalDateTimeJsonDeserializer.INSTANCE
    private val factory = JsonFactory()
    private val ctx = DefaultDeserializationContext.Impl(BeanDeserializerFactory.instance)

    @Test
    fun deserializeSuccessfully() {
        val tests = listOf(
                """{"year":1989,"month":8,"day":1,"hour":13,"minute":15,"second":33}""" to
                        LocalDateTime.of(1989, 8, 1, 13, 15, 33),
                """{"year":2008,"month":8,"day":8,"hour":22,"minute":31,"second":33}""" to
                        LocalDateTime.of(2008, 8, 8, 22, 31, 33),
                """{"year":1999,"month":12,"day":31,"hour":1,"minute":0,"second":0}""" to
                        LocalDateTime.of(1999, 12, 31, 1, 0, 0)
        )

        tests.forEach {
            val parser = factory.createParser(it.first)
            parser.nextToken()
            val dt = out.deserialize(parser, ctx)

            assertEquals(it.second, dt)
        }
    }

    @Test
    fun deserializeEmptyObjectNull() {
        val parser = factory.createParser("{}")
        parser.nextToken()
        val dt = out.deserialize(parser, ctx)

        assertNull(dt)
    }

    @Test
    fun deserializeNullObject() {
        val parser = factory.createParser("null")
        parser.nextToken()
        val dt = out.deserialize(parser, ctx)

        assertNull(dt)
    }

    @Test
    fun testDeserializeIntoObject() {
        val mapper = ObjectMapper(factory)
        val module = SimpleModule()
        module.addDeserializer(LocalDateTime::class.java, out)
        mapper.registerModule(module)

        val value = mapper.readValue(
                """{"id": 369,
                    |"dttm": {"year": 1989, "month": 8, "day": 1, "hour":1, "minute": 32, "second": 11},
                    |"str": "My string"}""".trimMargin(),
                LocalDateTimeDeserializeTester::class.java)

        assertEquals(369, value.id)
        assertEquals(LocalDateTime.of(1989, 8, 1, 1, 32, 11), value.dttm)
        assertEquals("My string", value.str)
    }
}

private data class LocalDateTimeDeserializeTester(
        val id: Long = 0,
        val dttm: LocalDateTime = LocalDateTime.now(),
        val str: String = ""
)
