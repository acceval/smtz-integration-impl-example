package route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.springframework.beans.factory.annotation.Autowired;

import com.smarttradzt.integration.spec.ampq.CompanyPayload;
import com.smarttradzt.integration.spec.ampq.MessageBody;
import com.smarttradzt.integration.spec.model.IntegrationTask;
import com.smarttradzt.integration.spec.model.MasterDataCustomerInMessage;
import com.smarttradzt.integration.spec.model.MasterDataMaterialInMessage;
import com.smarttradzt.integration.spec.route.CamelUtil;
import com.smarttradzt.integration.spec.route.CommonSOAPProducerRouteBuilder;

import amqp.sender.IntegrationCustomerQueueSender;
import amqp.sender.IntegrationMaterialQueueSender;
import ws.MasterDataWebService;

public class KPMGMasterDataProducerRoute extends CommonSOAPProducerRouteBuilder {

    @Autowired
    MasterDataWebService masterDataWebService;

    @Autowired
	IntegrationCustomerQueueSender customerQueueSender;

    @Autowired
    IntegrationMaterialQueueSender materialQueueSender;

    public KPMGMasterDataProducerRoute(IntegrationTask task) {
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

        from(epPrefix + "sendCustomer")
            .routeId(routeIdPrefix + "sendCustomer")
            .process(
            new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {

                    MasterDataCustomerInMessage in = exchange.getIn().getBody(MasterDataCustomerInMessage.class);
                    CompanyPayload payload = new CompanyPayload(task.getCompanyId(), in);

                    MessageBody body = new MessageBody(payload);

                    customerQueueSender.sendMessage(body);

                    exchange.getOut().setBody(
                            masterDataWebService.sendCustomer(in));
                }
            }
        );

        from(epPrefix + "sendMaterial")
            .routeId(routeIdPrefix + "sendMaterial")
            .process(
            new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {

                    MasterDataMaterialInMessage in = exchange.getIn().getBody(MasterDataMaterialInMessage.class);
                    CompanyPayload payload = new CompanyPayload(task.getCompanyId(), in);

                    MessageBody body = new MessageBody(payload);

                    materialQueueSender.sendMessage(body);

                    exchange.getOut().setBody(
                            masterDataWebService.sendMaterial(in));
                }
            }
        );
    }

	@Override
	protected Class<?> getServiceClassFQN() {
		return MasterDataWebService.class;
	}
}
