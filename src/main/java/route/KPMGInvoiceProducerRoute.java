package route;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.smarttradzt.integration.spec.ampq.CompanyPayload;
import com.smarttradzt.integration.spec.ampq.MessageBody;
import com.smarttradzt.integration.spec.model.IntegrationTask;
import com.smarttradzt.integration.spec.model.InvoiceResponse;
import com.smarttradzt.integration.spec.route.CamelUtil;
import com.smarttradzt.integration.spec.route.CommonSOAPProducerRouteBuilder;

import amqp.sender.IntegrationInvoiceQueueSender;
import ws.InvoiceWebService;
import ws.InvoiceWebServiceRequest;

public class KPMGInvoiceProducerRoute extends CommonSOAPProducerRouteBuilder {

    @Autowired
    InvoiceWebService invoiceWebService;

    @Autowired
	IntegrationInvoiceQueueSender invoiceQueueSender;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public KPMGInvoiceProducerRoute(IntegrationTask task) {
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

        from(epPrefix + "sendInvoice")
            .routeId(routeIdPrefix + "sendInvoice")
            .process(
            new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {

                	InvoiceWebServiceRequest in = exchange.getIn().getBody(InvoiceWebServiceRequest.class);
					InvoiceResponse response = convertToInvoiceResponse(in);
                    CompanyPayload payload = new CompanyPayload(task.getCompanyId(), response);

                    MessageBody body = new MessageBody(payload); 

                    invoiceQueueSender.sendMessage(body);

                    exchange.getOut().setBody(
                            invoiceWebService.sendInvoice(in));
                }
            }
        );
    }

	@Override
	protected Class<?> getServiceClassFQN() {
		return InvoiceWebService.class;
	}

	public InvoiceResponse convertToInvoiceResponse(InvoiceWebServiceRequest request) {
		InvoiceResponse resp = new InvoiceResponse();
		resp.setItems(new ArrayList<>());

		for (InvoiceWebServiceRequest.Item item : request.getItems()) {
			resp.getItems().add(convertToInvoiceResponseItem(item));
		}

		return resp;
	}

	private InvoiceResponse.Item convertToInvoiceResponseItem(InvoiceWebServiceRequest.Item output) {
		InvoiceResponse.Item item = new InvoiceResponse.Item();

		item.setEntryTime(output.getEntryTime());
		item.setEntryDate(convertDate(output.getEntryDate()));
		item.setSalesOrganization(output.getSalesOrganization());
		item.setDistributionChannel(output.getDistributionChannel());
		item.setDivision(output.getDivision());
		item.setSalesOffice(output.getSalesOffice());
		item.setBillingDocument(output.getBillingDocument());
		item.setBillingItem(output.getBillingItem());
		item.setBillingType(output.getBillingType());
		item.setPricingDate(convertDate(output.getPricingDate()));
		item.setSoldtoParty(output.getSoldtoParty());
		item.setShipTo(output.getShipTo());
		item.setBillTo(output.getBillTo());
		item.setPayer(output.getPayer());
		item.setCountryDestination(output.getCountryDestination());
		item.setTermsPaymentKey(output.getTermsPaymentKey());
		item.setDocumentCurrency(output.getDocumentCurrency());
		item.setExchangeRateFIPostings(convertExchangeRate(output.getExchangeRateFIPostings()));
		item.setActualInvoicedQuantity(convertNumber(output.getActualInvoicedQuantity()));
		item.setSalesUnit(output.getSalesUnit());
		item.setMaterialNumber(StringUtils.stripStart(output.getMaterialNumber(), "0"));
		item.setPlant(output.getPlant());
		item.setSalesGroup(output.getSalesGroup());
		item.setInvoicePrice(convertNumber(output.getInvoicePrice()));
		item.setConditionUnitInDocument(output.getConditionUnitInDocument());
		item.setInvoicePriceQuantity(convertNumber(output.getInvoicePriceQuantity()));
		item.setInvoicePriceCurrency(output.getInvoicePriceCurrency());
		item.setReferenceDocumentID(output.getReferenceDocumentID());
		item.setReferenceDocumentItemID(output.getReferenceDocumentItemID());
		item.setRequestedDeliveryDate(convertDate(output.getRequestedDeliveryDate()));
		item.setBillingDate(convertDate(output.getBillingDate()));
		item.setShippingConditions(output.getShippingConditions());
		item.setShipToCity(output.getShipToCity());
		item.setIncoterms(output.getIncoterms());
		item.setDestinationPort(output.getDestinationPort());
		item.setSalesOrderDate(convertDate(output.getSalesOrderDate()));
		item.setOriginPort(output.getOriginPort());
		item.setRegionPlant(output.getRegionPlant());
		item.setSalesOrderCreationDate(convertDate(output.getSalesOrderCreationDate()));
		item.setSalesContractNo(output.getSalesContractNo());
		item.setDocumentNo(output.getDocumentNo());
		item.setDocumentItemNo(StringUtils.stripStart(output.getDocumentItemNo(), "0"));
		item.setEta(convertDate(output.getEta()));
		item.setStorageLocation(output.getStorageLocation());
		item.setDocumentQuantity(convertNumber(output.getDocumentQuantity()));
		item.setDocumentValue(convertNumber(output.getDocumentValue()));
		item.setBillingItemValue(convertNumber(output.getBillingItemValue()));
		item.setPalletize(output.getPalletize());
		item.setSdDocumentCategory(output.getSdDocumentCategory());
		item.setCancelledBillingDocNo(output.getCancelledBillingDocNo());
		item.setMaterialEntered(output.getMaterialEntered());
		item.setConditionExchangeRate(convertExchangeRate(output.getConditionExchangeRate()));
		item.setNetWeight(output.getNetWeight());
		item.setPurchaseOrderDate(convertDate(output.getPurchaseOrderDate()));
		item.setPriceListType(output.getPriceListType());
		item.setGoodsMovementDate(convertDate(output.getGoodsMovementDate()));
		item.setSalesOrderNo(output.getSalesOrderNo());
		item.setTaxAmount(convertNumber(output.getTaxAmount()));
		item.setTaxCurrency(output.getTaxCurrency());
		item.setEndOfLoadingDate(convertDate(output.getEndOfLoadingDate()));
		item.setItemCategory(output.getItemCategory());
		item.setOrderReason(output.getOrderReason());

		return item;
	}

	LocalDate convertDate(String date) {
		if (StringUtils.isNotBlank(date)) {
			try {
				return LocalDate.parse(date, formatter);
			} catch (DateTimeParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	Double convertExchangeRate(String value) {
		if (StringUtils.isNotBlank(value)) {

			// if exchange rate is start with -, then is 1 divided by the value
			double excRate = 0;
			try {
				excRate = Double.parseDouble(value);

				if (excRate < 0) {
					excRate = -1 / excRate;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			return excRate;
		} else {
			return null;
		}
	}

	Double convertNumber(String value) {
		if (StringUtils.isNotBlank(value)) {
			try {
				return Double.parseDouble(value);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			return 0.0;
		} else {
			return null;
		}
	}
}
