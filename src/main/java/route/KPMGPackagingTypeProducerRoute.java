package route;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.smarttradzt.integration.spec.ampq.CompanyPayload;
import com.smarttradzt.integration.spec.ampq.MessageBody;
import com.smarttradzt.integration.spec.model.IntegrationTask;
import com.smarttradzt.integration.spec.model.PackagingTypeResponse;
import com.smarttradzt.integration.spec.route.CamelUtil;
import com.smarttradzt.integration.spec.route.CommonSOAPProducerRouteBuilder;

import amqp.sender.IntegrationPackagingTypeQueueSender;
import ws.PackagingTypeWebService;
import ws.PackagingTypeWebServiceRequest;

public class KPMGPackagingTypeProducerRoute extends CommonSOAPProducerRouteBuilder {

    @Autowired
	PackagingTypeWebService packagingTypeWebService;

	@Autowired
    IntegrationPackagingTypeQueueSender packagingTypeQueueSender;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public KPMGPackagingTypeProducerRoute(IntegrationTask task) {
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

        from(epPrefix + "sendPackagingType")
            .routeId(routeIdPrefix + "sendPackagingType")
            .process(
            new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {

                    PackagingTypeWebServiceRequest in = exchange.getIn().getBody(PackagingTypeWebServiceRequest.class);
                    PackagingTypeResponse response = convertToResponse(in);
                    CompanyPayload payload = new CompanyPayload(task.getCompanyId(), response);

                    MessageBody body = new MessageBody(payload);

                    packagingTypeQueueSender.sendMessage(body);

                    exchange.getOut().setBody(
                            packagingTypeWebService.sendPackagingType(in));
                }
            }
        );
    }

    PackagingTypeResponse convertToResponse(PackagingTypeWebServiceRequest request) {
        PackagingTypeResponse response = new PackagingTypeResponse();

        response.setObjectType(request.getObjectType());
        response.setProduct(StringUtils.stripStart(request.getMaterialNo(), "0"));
        response.setClassType(request.getClassType());
        response.setClassValue(request.getClassValue());

        response.setItems(new ArrayList<>());

        for (PackagingTypeWebServiceRequest.Item requestItem : request.getItems()) {
            PackagingTypeResponse.Item item = new PackagingTypeResponse.Item();

            item.setCharacteristic(requestItem.getCharacteristic());
            item.setCharacteristicValue(requestItem.getCharacteristicValue());

            if (StringUtils.isNotBlank(requestItem.getDate())) {
                item.setDate(LocalDate.parse(requestItem.getDate(), formatter));
            }

            response.getItems().add(item);
        }

        return response;
    }

	@Override
	protected Class<?> getServiceClassFQN() {
		return PackagingTypeWebService.class;
	}
}
