
package getcogs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the getcogs package. 
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

    private final static QName _CogsInput_QNAME = new QName("http://getCOGS", "CogsInput");
    private final static QName _GetCOGSResponse_QNAME = new QName("http://getCOGS", "getCOGSResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: getcogs
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CogsInput }
     * 
     */
    public CogsInput createCogsInput() {
        return new CogsInput();
    }

    /**
     * Create an instance of {@link GetCOGSResponse }
     * 
     */
    public GetCOGSResponse createGetCOGSResponse() {
        return new GetCOGSResponse();
    }

    /**
     * Create an instance of {@link ListInputs }
     * 
     */
    public ListInputs createListInputs() {
        return new ListInputs();
    }

    /**
     * Create an instance of {@link CogsOutput }
     * 
     */
    public CogsOutput createCogsOutput() {
        return new CogsOutput();
    }

    /**
     * Create an instance of {@link CogsCompOutput }
     * 
     */
    public CogsCompOutput createCogsCompOutput() {
        return new CogsCompOutput();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CogsInput }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CogsInput }{@code >}
     */
    @XmlElementDecl(namespace = "http://getCOGS", name = "CogsInput")
    public JAXBElement<CogsInput> createCogsInput(CogsInput value) {
        return new JAXBElement<CogsInput>(_CogsInput_QNAME, CogsInput.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCOGSResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetCOGSResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://getCOGS", name = "getCOGSResponse")
    public JAXBElement<GetCOGSResponse> createGetCOGSResponse(GetCOGSResponse value) {
        return new JAXBElement<GetCOGSResponse>(_GetCOGSResponse_QNAME, GetCOGSResponse.class, null, value);
    }

}
