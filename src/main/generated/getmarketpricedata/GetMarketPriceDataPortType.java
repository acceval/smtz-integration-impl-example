package getmarketpricedata;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.3.2
 * 2023-03-20T16:44:00.299+08:00
 * Generated source version: 3.3.2
 *
 */
@WebService(targetNamespace = "http://getMarketPriceData", name = "getMarketPriceData_PortType")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface GetMarketPriceDataPortType {

    @WebMethod(action = "getMarketPriceData_Binder_getMarketPriceData")
    @WebResult(name = "getMarketPriceDataResponse", targetNamespace = "http://getMarketPriceData", partName = "parameters")
    public GetMarketPriceDataResponse getMarketPriceData(

        @WebParam(partName = "parameters", name = "marketPriceInput", targetNamespace = "http://getMarketPriceData")
        MarketPriceInput parameters
    );
}