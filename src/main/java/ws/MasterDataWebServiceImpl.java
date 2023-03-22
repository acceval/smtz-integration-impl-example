package ws;

import org.springframework.stereotype.Service;

import com.smarttradzt.integration.spec.model.MasterDataCustomerInMessage;
import com.smarttradzt.integration.spec.model.MasterDataMaterialInMessage;

@Service
public class MasterDataWebServiceImpl implements MasterDataWebService {

    @Override
    public MasterDataStatusMessage sendMaterial(MasterDataMaterialInMessage msg) throws Exception {

        MasterDataStatusMessage status = new MasterDataStatusMessage();
        status.setStatus("SUCCESS");
        status.setMessage("Product code recieved " + msg.getCode());
        return status;
    }

    @Override
    public MasterDataStatusMessage sendCustomer(MasterDataCustomerInMessage msg) throws Exception {

        MasterDataStatusMessage status = new MasterDataStatusMessage();
        status.setStatus("SUCCESS");
        status.setMessage("Customer code recieved " + msg.getCode());
        return status;
    }
}
