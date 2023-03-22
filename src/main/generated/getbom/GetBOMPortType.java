package getbom;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.3.2
 * 2023-03-15T09:23:35.407+08:00
 * Generated source version: 3.3.2
 *
 */
@WebService(targetNamespace = "http://getBOM", name = "getBOM_PortType")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface GetBOMPortType {

    @WebMethod(action = "getBOM_Binder_getBOM")
    @WebResult(name = "getBOMResponse", targetNamespace = "http://getBOM", partName = "parameters")
    public GetBOMResponse getBOM(

        @WebParam(partName = "parameters", name = "getBOM", targetNamespace = "http://getBOM")
        GetBOM parameters
    );
}
