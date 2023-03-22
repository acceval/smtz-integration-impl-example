package ws;

import org.springframework.stereotype.Service;

import com.smarttradzt.integration.spec.model.CustomerAddressInMessage;

@Service
public class CustomerAddressWebServiceImpl implements CustomerAddressWebService {
	@Override
    public MasterDataStatusMessage sendCustomerAddress(CustomerAddressInMessage msg) throws Exception {

        MasterDataStatusMessage status = new MasterDataStatusMessage();
        status.setStatus("SUCCESS");
        status.setMessage("Customer Address recieved " + msg.getCode());
        return status;
    }
}
