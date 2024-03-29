package forexhandler;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.3.2
 * 2023-03-15T09:21:40.199+08:00
 * Generated source version: 3.3.2
 *
 */
@WebServiceClient(name = "ForexHandler",
                  wsdlLocation = "file:/C:/Users/cyk_1/git/smtz-integration-impl-example/src/main/resources/wsdl/ForexHandler.wsdl",
                  targetNamespace = "http://ForexHandler")
public class ForexHandler extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://ForexHandler", "ForexHandler");
    public final static QName ForexHandlerPort = new QName("http://ForexHandler", "ForexHandler_Port");
    static {
        URL url = null;
        try {
            url = new URL("file:/C:/Users/cyk_1/git/smtz-integration-impl-example/src/main/resources/wsdl/ForexHandler.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ForexHandler.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:/C:/Users/cyk_1/git/smtz-integration-impl-example/src/main/resources/wsdl/ForexHandler.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ForexHandler(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ForexHandler(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ForexHandler() {
        super(WSDL_LOCATION, SERVICE);
    }

    public ForexHandler(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public ForexHandler(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public ForexHandler(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns ForexHandlerPortType
     */
    @WebEndpoint(name = "ForexHandler_Port")
    public ForexHandlerPortType getForexHandlerPort() {
        return super.getPort(ForexHandlerPort, ForexHandlerPortType.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ForexHandlerPortType
     */
    @WebEndpoint(name = "ForexHandler_Port")
    public ForexHandlerPortType getForexHandlerPort(WebServiceFeature... features) {
        return super.getPort(ForexHandlerPort, ForexHandlerPortType.class, features);
    }

}
