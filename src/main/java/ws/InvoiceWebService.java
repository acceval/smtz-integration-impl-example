package ws;

public interface InvoiceWebService {
    WebServiceStatusMessage sendInvoice(InvoiceWebServiceRequest request) throws Exception;
}
