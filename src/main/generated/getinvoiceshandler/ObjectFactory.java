
package getinvoiceshandler;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the getinvoiceshandler package. 
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

    private final static QName _GetInvoiceInput_QNAME = new QName("http://GetInvoicesHandler", "GetInvoiceInput");
    private final static QName _GetInvoicesResponse_QNAME = new QName("http://GetInvoicesHandler", "getInvoicesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: getinvoiceshandler
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetInvoiceInput }
     * 
     */
    public GetInvoiceInput createGetInvoiceInput() {
        return new GetInvoiceInput();
    }

    /**
     * Create an instance of {@link GetInvoicesResponse }
     * 
     */
    public GetInvoicesResponse createGetInvoicesResponse() {
        return new GetInvoicesResponse();
    }

    /**
     * Create an instance of {@link SalesOrganizations }
     * 
     */
    public SalesOrganizations createSalesOrganizations() {
        return new SalesOrganizations();
    }

    /**
     * Create an instance of {@link Divisions }
     * 
     */
    public Divisions createDivisions() {
        return new Divisions();
    }

    /**
     * Create an instance of {@link GetInvoiceOutput }
     * 
     */
    public GetInvoiceOutput createGetInvoiceOutput() {
        return new GetInvoiceOutput();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInvoiceInput }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetInvoiceInput }{@code >}
     */
    @XmlElementDecl(namespace = "http://GetInvoicesHandler", name = "GetInvoiceInput")
    public JAXBElement<GetInvoiceInput> createGetInvoiceInput(GetInvoiceInput value) {
        return new JAXBElement<GetInvoiceInput>(_GetInvoiceInput_QNAME, GetInvoiceInput.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInvoicesResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetInvoicesResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://GetInvoicesHandler", name = "getInvoicesResponse")
    public JAXBElement<GetInvoicesResponse> createGetInvoicesResponse(GetInvoicesResponse value) {
        return new JAXBElement<GetInvoicesResponse>(_GetInvoicesResponse_QNAME, GetInvoicesResponse.class, null, value);
    }

}
