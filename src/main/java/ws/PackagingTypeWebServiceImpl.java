package ws;

import org.springframework.stereotype.Service;

@Service
public class PackagingTypeWebServiceImpl implements PackagingTypeWebService {

    @Override
    public WebServiceStatusMessage sendPackagingType(PackagingTypeWebServiceRequest request) throws Exception {

        WebServiceStatusMessage status = new WebServiceStatusMessage();
        status.setStatus("SUCCESS");
        status.setMessage("Packaging Type recieved.");
        return status;
    }
}
