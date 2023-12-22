package updatesalescontract;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.3.2
 * 2023-03-20T16:46:34.636+08:00
 * Generated source version: 3.3.2
 *
 */
@WebService(targetNamespace = "http://UpdateSalesContract", name = "UpdateSalesContract_PortType")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface UpdateSalesContractPortType {

    @WebMethod(action = "UpdateSalesContract_Binder_updateSalesContract")
    @WebResult(name = "SalesContractChangeOutput", targetNamespace = "http://UpdateSalesContract", partName = "parameters")
    public SalesContractChangeOutput updateSalesContract(

        @WebParam(partName = "parameters", name = "SalesContractChangeInput", targetNamespace = "http://UpdateSalesContract")
        SalesContractChangeInput parameters
    );
}