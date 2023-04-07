package ws;

import com.smarttradzt.integration.spec.model.CustomerAddressInMessage;

public interface CustomerAddressWebService {
	MasterDataStatusMessage sendCustomerAddress(CustomerAddressInMessage msg) throws Exception;
}
