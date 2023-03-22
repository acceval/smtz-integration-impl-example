
package updatesalescontract;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the updatesalescontract package. 
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

    private final static QName _SalesContractChangeInput_QNAME = new QName("http://UpdateSalesContract", "SalesContractChangeInput");
    private final static QName _SalesContractChangeOutput_QNAME = new QName("http://UpdateSalesContract", "SalesContractChangeOutput");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: updatesalescontract
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SalesContractChangeInput }
     * 
     */
    public SalesContractChangeInput createSalesContractChangeInput() {
        return new SalesContractChangeInput();
    }

    /**
     * Create an instance of {@link SalesContractChangeOutput }
     * 
     */
    public SalesContractChangeOutput createSalesContractChangeOutput() {
        return new SalesContractChangeOutput();
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
     * Create an instance of {@link RETURN }
     * 
     */
    public RETURN createRETURN() {
        return new RETURN();
    }

    /**
     * Create an instance of {@link CONTRACTSTAT }
     * 
     */
    public CONTRACTSTAT createCONTRACTSTAT() {
        return new CONTRACTSTAT();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SalesContractChangeInput }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SalesContractChangeInput }{@code >}
     */
    @XmlElementDecl(namespace = "http://UpdateSalesContract", name = "SalesContractChangeInput")
    public JAXBElement<SalesContractChangeInput> createSalesContractChangeInput(SalesContractChangeInput value) {
        return new JAXBElement<SalesContractChangeInput>(_SalesContractChangeInput_QNAME, SalesContractChangeInput.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SalesContractChangeOutput }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SalesContractChangeOutput }{@code >}
     */
    @XmlElementDecl(namespace = "http://UpdateSalesContract", name = "SalesContractChangeOutput")
    public JAXBElement<SalesContractChangeOutput> createSalesContractChangeOutput(SalesContractChangeOutput value) {
        return new JAXBElement<SalesContractChangeOutput>(_SalesContractChangeOutput_QNAME, SalesContractChangeOutput.class, null, value);
    }

}
