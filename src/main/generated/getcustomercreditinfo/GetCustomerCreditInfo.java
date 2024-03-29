package getcustomercreditinfo;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.3.2
 * 2023-03-20T16:43:59.835+08:00
 * Generated source version: 3.3.2
 *
 */
@WebServiceClient(name = "getCustomerCreditInfo",
                  wsdlLocation = "file:/C:/Users/cyk_1/git/smtz-integration-impl-example/src/main/resources/wsdl/getCustomerCreditInfo.wsdl",
                  targetNamespace = "http://getCustomerCreditInfo")
public class GetCustomerCreditInfo extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://getCustomerCreditInfo", "getCustomerCreditInfo");
    public final static QName GetCustomerCreditInfoPort = new QName("http://getCustomerCreditInfo", "getCustomerCreditInfo_Port");
    static {
        URL url = null;
        try {
            url = new URL("file:/C:/Users/cyk_1/git/smtz-integration-impl-example/src/main/resources/wsdl/getCustomerCreditInfo.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(GetCustomerCreditInfo.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:/C:/Users/cyk_1/git/smtz-integration-impl-example/src/main/resources/wsdl/getCustomerCreditInfo.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public GetCustomerCreditInfo(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GetCustomerCreditInfo(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GetCustomerCreditInfo() {
        super(WSDL_LOCATION, SERVICE);
    }

    public GetCustomerCreditInfo(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public GetCustomerCreditInfo(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public GetCustomerCreditInfo(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns GetCustomerCreditInfoPortType
     */
    @WebEndpoint(name = "getCustomerCreditInfo_Port")
    public GetCustomerCreditInfoPortType getGetCustomerCreditInfoPort() {
        return super.getPort(GetCustomerCreditInfoPort, GetCustomerCreditInfoPortType.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GetCustomerCreditInfoPortType
     */
    @WebEndpoint(name = "getCustomerCreditInfo_Port")
    public GetCustomerCreditInfoPortType getGetCustomerCreditInfoPort(WebServiceFeature... features) {
        return super.getPort(GetCustomerCreditInfoPort, GetCustomerCreditInfoPortType.class, features);
    }

}
