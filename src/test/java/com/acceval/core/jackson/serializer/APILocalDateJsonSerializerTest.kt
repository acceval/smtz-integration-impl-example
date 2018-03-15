package com.acceval.core.jackson.serializer

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider
import org.junit.jupiter.api.Test
import java.io.StringWriter
import java.time.LocalDate
import kotlin.test.assertEquals

internal class APILocalDateJsonSerializerTest {

    private val out = APILocalDateJsonSerializer.INSTANCE
    private val provider = DefaultSerializerProvider.Impl()


    @Test
    fun serializeSuccessfully() {
        val tests = listOf(
                LocalDate.of(1989, 8, 1) to """{"year":1989,"month":8,"day":1}""",
                LocalDate.of(2008, 8, 8) to """{"year":2008,"month":8,"day":8}""",
                LocalDate.of(1999, 12, 31) to """{"year":1999,"month":12,"day":31}"""
        )

        tests.forEach {
            val writer = StringWriter()
            val generator = JsonFactory().createGenerator(writer)

            out.serialize(it.first, generator, provider)
            generator.close()

            assertEquals(it.second, writer.toString())
        }
    }

    @Test
    fun serializeNull() {
        val writer = StringWriter()
        val generator = JsonFactory().createGenerator(writer)

        out.serialize(null, generator, provider)
        generator.close()

        assertEquals("null", writer.toString())
    }
}