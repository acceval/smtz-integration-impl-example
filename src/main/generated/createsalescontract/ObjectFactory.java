
package createsalescontract;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the createsalescontract package. 
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

    private final static QName _SalesContractInput_QNAME = new QName("http://CreateSalesContract", "salesContractInput");
    private final static QName _SalesContractOutput_QNAME = new QName("http://CreateSalesContract", "salesContractOutput");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: createsalescontract
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SalesContractInput }
     * 
     */
    public SalesContractInput createSalesContractInput() {
        return new SalesContractInput();
    }

    /**
     * Create an instance of {@link SalesContractOutput }
     * 
     */
    public SalesContractOutput createSalesContractOutput() {
        return new SalesContractOutput();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link SalesContractInput }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SalesContractInput }{@code >}
     */
    @XmlElementDecl(namespace = "http://CreateSalesContract", name = "salesContractInput")
    public JAXBElement<SalesContractInput> createSalesContractInput(SalesContractInput value) {
        return new JAXBElement<SalesContractInput>(_SalesContractInput_QNAME, SalesContractInput.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SalesContractOutput }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SalesContractOutput }{@code >}
     */
    @XmlElementDecl(namespace = "http://CreateSalesContract", name = "salesContractOutput")
    public JAXBElement<SalesContractOutput> createSalesContractOutput(SalesContractOutput value) {
        return new JAXBElement<SalesContractOutput>(_SalesContractOutput_QNAME, SalesContractOutput.class, null, value);
    }

}
