package com.acceval.core.jackson.serializer

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider
import org.junit.Test
import java.io.StringWriter
import java.time.LocalDateTime
import kotlin.test.assertEquals

internal class APILocalDateTimeJsonSerializerTest {
    private val out = APILocalDateTimeJsonSerializer.INSTANCE
    private val provider = DefaultSerializerProvider.Impl()

    @Test
    fun serializeSuccessfully() {
        val tests = listOf(
                LocalDateTime.of(1989, 8, 1, 13, 15, 33) to
                        """{"year":1989,"month":8,"day":1,"hour":13,"minute":15,"second":33}""",
                LocalDateTime.of(2008, 8, 8, 22, 31, 33) to
                        """{"year":2008,"month":8,"day":8,"hour":22,"minute":31,"second":33}""",
                LocalDateTime.of(1999, 12, 31, 1, 0, 0) to
                        """{"year":1999,"month":12,"day":31,"hour":1,"minute":0,"second":0}"""
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
