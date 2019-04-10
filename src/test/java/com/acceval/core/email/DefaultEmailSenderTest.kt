package com.acceval.core.email

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * @author Julian
 */
internal class DefaultEmailSenderTest {
	private lateinit var sender: DefaultEmailSender

	@Mock
	private lateinit var templateRenderer: TemplateRenderer

	@Mock
	private lateinit var emailQueueSender: EmailQueueSender

	@BeforeEach
	fun setUp() {
		MockitoAnnotations.initMocks(this)

		sender = DefaultEmailSender(templateRenderer, emailQueueSender)
	}

	@Test
	fun sendEmailWithTemplate() {
		val data = EmailContentData()
        data.text = "my custom content"
        data.template = "default"

		sender.sendEmail(data)

		verify(templateRenderer, times(1)).render(any(), eq(data))
		verify(emailQueueSender, times(1)).messageBody = any()
		verify(emailQueueSender, times(1)).sendMessage()
	}
}