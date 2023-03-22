
package getsku;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the getsku package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetSKU_QNAME = new QName("http://getSKU", "getSKU");
    private final static QName _GetSKUResponse_QNAME = new QName("http://getSKU", "getSKUResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: getsku
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetSKU }
     * 
     */
    public GetSKU createGetSKU() {
        return new GetSKU();
    }

    /**
     * Create an instance of {@link GetSKUResponse }
     * 
     */
    public GetSKUResponse createGetSKUResponse() {
        return new GetSKUResponse();
    }

    /**
     * Create an instance of {@link SkuOutput }
     * 
     */
    public SkuOutput createSkuOutput() {
        return new SkuOutput();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSKU }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetSKU }{@code >}
     */
    @XmlElementDecl(namespace = "http://getSKU", name = "getSKU")
    public JAXBElement<GetSKU> createGetSKU(GetSKU value) {
        return new JAXBElement<GetSKU>(_GetSKU_QNAME, GetSKU.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSKUResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetSKUResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://getSKU", name = "getSKUResponse")
    public JAXBElement<GetSKUResponse> createGetSKUResponse(GetSKUResponse value) {
        return new JAXBElement<GetSKUResponse>(_GetSKUResponse_QNAME, GetSKUResponse.class, null, value);
    }

}
