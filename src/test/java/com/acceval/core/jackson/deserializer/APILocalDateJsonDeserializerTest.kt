package com.acceval.core.jackson.deserializer

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext
import org.junit.jupiter.api.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class APILocalDateJsonDeserializerTest {

    private val out = APILocalDateJsonDeserializer.INSTANCE
    private val factory = JsonFactory()
    private val ctx = DefaultDeserializationContext.Impl(BeanDeserializerFactory.instance)

    @Test
    fun deserializeSuccessfully() {
        val tests = listOf(
                """{"year":1989,"month":8,"day":1}""" to LocalDate.of(1989, 8, 1),
                """{"year":2008,"month":8,"day":8}""" to LocalDate.of(2008, 8, 8),
                """{"year":1999,"month":12,"day":31}""" to LocalDate.of(1999, 12, 31)
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
}