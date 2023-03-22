
package getbom;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the getbom package. 
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

    private final static QName _GetBOM_QNAME = new QName("http://getBOM", "getBOM");
    private final static QName _GetBOMResponse_QNAME = new QName("http://getBOM", "getBOMResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: getbom
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetBOM }
     * 
     */
    public GetBOM createGetBOM() {
        return new GetBOM();
    }

    /**
     * Create an instance of {@link GetBOMResponse }
     * 
     */
    public GetBOMResponse createGetBOMResponse() {
        return new GetBOMResponse();
    }

    /**
     * Create an instance of {@link BomHeaderOutput }
     * 
     */
    public BomHeaderOutput createBomHeaderOutput() {
        return new BomHeaderOutput();
    }

    /**
     * Create an instance of {@link BomItemOutput }
     * 
     */
    public BomItemOutput createBomItemOutput() {
        return new BomItemOutput();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBOM }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetBOM }{@code >}
     */
    @XmlElementDecl(namespace = "http://getBOM", name = "getBOM")
    public JAXBElement<GetBOM> createGetBOM(GetBOM value) {
        return new JAXBElement<GetBOM>(_GetBOM_QNAME, GetBOM.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBOMResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetBOMResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://getBOM", name = "getBOMResponse")
    public JAXBElement<GetBOMResponse> createGetBOMResponse(GetBOMResponse value) {
        return new JAXBElement<GetBOMResponse>(_GetBOMResponse_QNAME, GetBOMResponse.class, null, value);
    }

}
