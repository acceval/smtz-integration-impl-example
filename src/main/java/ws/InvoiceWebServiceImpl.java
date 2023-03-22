package ws;

import org.springframework.stereotype.Service;

@Service
public class InvoiceWebServiceImpl implements InvoiceWebService {
    @Override
    public WebServiceStatusMessage sendInvoice(InvoiceWebServiceRequest request) throws Exception {

        WebServiceStatusMessage status = new WebServiceStatusMessage();
        status.setStatus("SUCCESS");
        status.setMessage("Invoice recieved.");
        return status;
    }
}
