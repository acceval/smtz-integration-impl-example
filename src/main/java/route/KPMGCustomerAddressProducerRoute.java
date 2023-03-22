package route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.smarttradzt.integration.spec.ampq.CompanyPayload;
import com.smarttradzt.integration.spec.ampq.MessageBody;
import com.smarttradzt.integration.spec.model.CustomerAddressInMessage;
import com.smarttradzt.integration.spec.model.IntegrationTask;
import com.smarttradzt.integration.spec.route.CamelUtil;
import com.smarttradzt.integration.spec.route.CommonSOAPProducerRouteBuilder;

import amqp.sender.IntegrationCustomerAddressQueueSender;
import ws.CustomerAddressWebService;

public class KPMGCustomerAddressProducerRoute extends CommonSOAPProducerRouteBuilder {
	@Autowired
	CustomerAddressWebService customerAddressWebService;

    @Autowired
    IntegrationCustomerAddressQueueSender customerAddressQueueSender;

    public KPMGCustomerAddressProducerRoute(IntegrationTask task) {
        super(task);
    }

    private CxfEndpoint getEndPoint() throws Exception {
        CxfEndpoint endpoint = new CxfEndpoint();

        String path = task.getInboundPath();

        if (!path.startsWith("/")) path = "/" + path;

        endpoint.setAddress(path);
		endpoint.setServiceClass(this.getServiceClassFQN().getName());
        endpoint.setCamelContext(getContext());

        addSecurityInterceptor(endpoint, task.getInboundUsername(), task.getInboundPassword());
        addLoggingInterceptor(endpoint);
        return endpoint;
    }

    @Override
    public void configure() throws Exception {
        String epPrefix = CamelUtil.getFromEndpointName(task);
        String routeIdPrefix = CamelUtil.getRouteId(task);

        from(getEndPoint())
                .routeId(routeIdPrefix)
                .process(preProcess())
                .recipientList(simple(epPrefix + "${header.operationName}"))
                .process(postProcess());

        from(epPrefix + "sendCustomerAddress")
            .routeId(routeIdPrefix + "sendCustomerAddress")
            .process(
            new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {

                    CustomerAddressInMessage in = exchange.getIn().getBody(CustomerAddressInMessage.class);
                    CompanyPayload payload = new CompanyPayload(task.getCompanyId(), in);

                    MessageBody body = new MessageBody(payload);

                    customerAddressQueueSender.sendMessage(body);

                    exchange.getOut().setBody(
                    		customerAddressWebService.sendCustomerAddress(in));
                }
            }
        );
    }

	@Override
	protected Class<?> getServiceClassFQN() {
		return CustomerAddressWebService.class;
	}
}
