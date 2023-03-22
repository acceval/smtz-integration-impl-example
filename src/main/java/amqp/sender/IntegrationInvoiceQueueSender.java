package amqp.sender;

import org.springframework.stereotype.Component;

import com.smarttradzt.integration.spec.ampq.QueueSender;

@Component
public class IntegrationInvoiceQueueSender extends QueueSender {

    @Override
    protected String getTopicExchageName() {
        return SMARTTRADZ_TOPIC;
    }

    @Override
    protected String getSenderQueueName() {
        return "invoice-integration-queue";
    }
}
