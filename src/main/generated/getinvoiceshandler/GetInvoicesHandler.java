package getinvoiceshandler;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.3.2
 * 2023-03-20T16:44:00.208+08:00
 * Generated source version: 3.3.2
 *
 */
@WebServiceClient(name = "GetInvoicesHandler",
                  wsdlLocation = "file:/C:/Users/cyk_1/git/smtz-integration-impl-example/src/main/resources/wsdl/GetInvoicesHandler.wsdl",
                  targetNamespace = "http://GetInvoicesHandler")
public class GetInvoicesHandler extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://GetInvoicesHandler", "GetInvoicesHandler");
    public final static QName GetInvoicesHandlerPort = new QName("http://GetInvoicesHandler", "GetInvoicesHandler_Port");
    static {
        URL url = null;
        try {
            url = new URL("file:/C:/Users/cyk_1/git/smtz-integration-impl-example/src/main/resources/wsdl/GetInvoicesHandler.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(GetInvoicesHandler.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:/C:/Users/cyk_1/git/smtz-integration-impl-example/src/main/resources/wsdl/GetInvoicesHandler.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public GetInvoicesHandler(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GetInvoicesHandler(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GetInvoicesHandler() {
        super(WSDL_LOCATION, SERVICE);
    }

    public GetInvoicesHandler(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public GetInvoicesHandler(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public GetInvoicesHandler(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns GetInvoicesHandlerPortType
     */
    @WebEndpoint(name = "GetInvoicesHandler_Port")
    public GetInvoicesHandlerPortType getGetInvoicesHandlerPort() {
        return super.getPort(GetInvoicesHandlerPort, GetInvoicesHandlerPortType.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GetInvoicesHandlerPortType
     */
    @WebEndpoint(name = "GetInvoicesHandler_Port")
    public GetInvoicesHandlerPortType getGetInvoicesHandlerPort(WebServiceFeature... features) {
        return super.getPort(GetInvoicesHandlerPort, GetInvoicesHandlerPortType.class, features);
    }

}
