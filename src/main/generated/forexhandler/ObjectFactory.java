
package forexhandler;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the forexhandler package. 
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

    private final static QName _ForexInput_QNAME = new QName("http://ForexHandler", "ForexInput");
    private final static QName _ForexOutput_QNAME = new QName("http://ForexHandler", "ForexOutput");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: forexhandler
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ForexInput }
     * 
     */
    public ForexInput createForexInput() {
        return new ForexInput();
    }

    /**
     * Create an instance of {@link ForexOutput }
     * 
     */
    public ForexOutput createForexOutput() {
        return new ForexOutput();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ForexInput }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ForexInput }{@code >}
     */
    @XmlElementDecl(namespace = "http://ForexHandler", name = "ForexInput")
    public JAXBElement<ForexInput> createForexInput(ForexInput value) {
        return new JAXBElement<ForexInput>(_ForexInput_QNAME, ForexInput.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ForexOutput }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ForexOutput }{@code >}
     */
    @XmlElementDecl(namespace = "http://ForexHandler", name = "ForexOutput")
    public JAXBElement<ForexOutput> createForexOutput(ForexOutput value) {
        return new JAXBElement<ForexOutput>(_ForexOutput_QNAME, ForexOutput.class, null, value);
    }

}
