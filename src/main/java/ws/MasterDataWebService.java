package ws;

import com.smarttradzt.integration.spec.model.MasterDataCustomerInMessage;
import com.smarttradzt.integration.spec.model.MasterDataMaterialInMessage;

public interface MasterDataWebService {
    MasterDataStatusMessage sendMaterial(MasterDataMaterialInMessage msg) throws Exception;

    MasterDataStatusMessage sendCustomer(MasterDataCustomerInMessage msg) throws Exception;
}
