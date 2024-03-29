package getmarketpricedata;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.3.2
 * 2023-03-20T16:44:00.305+08:00
 * Generated source version: 3.3.2
 *
 */
@WebServiceClient(name = "getMarketPriceData",
                  wsdlLocation = "file:/C:/Users/cyk_1/git/smtz-integration-impl-example/src/main/resources/wsdl/getMarketPriceData.wsdl",
                  targetNamespace = "http://getMarketPriceData")
public class GetMarketPriceData extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://getMarketPriceData", "getMarketPriceData");
    public final static QName GetMarketPriceDataPort = new QName("http://getMarketPriceData", "getMarketPriceData_Port");
    static {
        URL url = null;
        try {
            url = new URL("file:/C:/Users/cyk_1/git/smtz-integration-impl-example/src/main/resources/wsdl/getMarketPriceData.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(GetMarketPriceData.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:/C:/Users/cyk_1/git/smtz-integration-impl-example/src/main/resources/wsdl/getMarketPriceData.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public GetMarketPriceData(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GetMarketPriceData(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GetMarketPriceData() {
        super(WSDL_LOCATION, SERVICE);
    }

    public GetMarketPriceData(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public GetMarketPriceData(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public GetMarketPriceData(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns GetMarketPriceDataPortType
     */
    @WebEndpoint(name = "getMarketPriceData_Port")
    public GetMarketPriceDataPortType getGetMarketPriceDataPort() {
        return super.getPort(GetMarketPriceDataPort, GetMarketPriceDataPortType.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GetMarketPriceDataPortType
     */
    @WebEndpoint(name = "getMarketPriceData_Port")
    public GetMarketPriceDataPortType getGetMarketPriceDataPort(WebServiceFeature... features) {
        return super.getPort(GetMarketPriceDataPort, GetMarketPriceDataPortType.class, features);
    }

}
