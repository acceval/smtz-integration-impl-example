
package getcustomercreditinfo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the getcustomercreditinfo package. 
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

    private final static QName _CustomerCreditInput_QNAME = new QName("http://getCustomerCreditInfo", "customerCreditInput");
    private final static QName _CustomerCreditOutput_QNAME = new QName("http://getCustomerCreditInfo", "customerCreditOutput");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: getcustomercreditinfo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CustomerCreditInput }
     * 
     */
    public CustomerCreditInput createCustomerCreditInput() {
        return new CustomerCreditInput();
    }

    /**
     * Create an instance of {@link CustomerCreditOutput }
     * 
     */
    public CustomerCreditOutput createCustomerCreditOutput() {
        return new CustomerCreditOutput();
    }

    /**
     * Create an instance of {@link Header }
     * 
     */
    public Header createHeader() {
        return new Header();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerCreditInput }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CustomerCreditInput }{@code >}
     */
    @XmlElementDecl(namespace = "http://getCustomerCreditInfo", name = "customerCreditInput")
    public JAXBElement<CustomerCreditInput> createCustomerCreditInput(CustomerCreditInput value) {
        return new JAXBElement<CustomerCreditInput>(_CustomerCreditInput_QNAME, CustomerCreditInput.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerCreditOutput }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CustomerCreditOutput }{@code >}
     */
    @XmlElementDecl(namespace = "http://getCustomerCreditInfo", name = "customerCreditOutput")
    public JAXBElement<CustomerCreditOutput> createCustomerCreditOutput(CustomerCreditOutput value) {
        return new JAXBElement<CustomerCreditOutput>(_CustomerCreditOutput_QNAME, CustomerCreditOutput.class, null, value);
    }

}
