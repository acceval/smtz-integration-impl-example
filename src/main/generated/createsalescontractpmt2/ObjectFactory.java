
package createsalescontractpmt2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the createsalescontractpmt2 package. 
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

    private final static QName _SalesContractInputPMT2_QNAME = new QName("http://CreateSalesContractPMT2", "salesContractInputPMT2");
    private final static QName _SalesContractOutputPMT2_QNAME = new QName("http://CreateSalesContractPMT2", "salesContractOutputPMT2");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: createsalescontractpmt2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SalesContractInputPMT2 }
     * 
     */
    public SalesContractInputPMT2 createSalesContractInputPMT2() {
        return new SalesContractInputPMT2();
    }

    /**
     * Create an instance of {@link SalesContractOutputPMT2 }
     * 
     */
    public SalesContractOutputPMT2 createSalesContractOutputPMT2() {
        return new SalesContractOutputPMT2();
    }

    /**
     * Create an instance of {@link ContractHeader }
     * 
     */
    public ContractHeader createContractHeader() {
        return new ContractHeader();
    }

    /**
     * Create an instance of {@link ContractItems }
     * 
     */
    public ContractItems createContractItems() {
        return new ContractItems();
    }

    /**
     * Create an instance of {@link ContractPartners }
     * 
     */
    public ContractPartners createContractPartners() {
        return new ContractPartners();
    }

    /**
     * Create an instance of {@link RETURN }
     * 
     */
    public RETURN createRETURN() {
        return new RETURN();
    }

    /**
     * Create an instance of {@link EXTENSIONIN }
     * 
     */
    public EXTENSIONIN createEXTENSIONIN() {
        return new EXTENSIONIN();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SalesContractInputPMT2 }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SalesContractInputPMT2 }{@code >}
     */
    @XmlElementDecl(namespace = "http://CreateSalesContractPMT2", name = "salesContractInputPMT2")
    public JAXBElement<SalesContractInputPMT2> createSalesContractInputPMT2(SalesContractInputPMT2 value) {
        return new JAXBElement<SalesContractInputPMT2>(_SalesContractInputPMT2_QNAME, SalesContractInputPMT2 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SalesContractOutputPMT2 }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SalesContractOutputPMT2 }{@code >}
     */
    @XmlElementDecl(namespace = "http://CreateSalesContractPMT2", name = "salesContractOutputPMT2")
    public JAXBElement<SalesContractOutputPMT2> createSalesContractOutputPMT2(SalesContractOutputPMT2 value) {
        return new JAXBElement<SalesContractOutputPMT2>(_SalesContractOutputPMT2_QNAME, SalesContractOutputPMT2 .class, null, value);
    }

}
