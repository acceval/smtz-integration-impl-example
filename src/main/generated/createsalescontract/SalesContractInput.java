
package createsalescontract;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for salesContractInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="salesContractInput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Contract_Header" type="{http://CreateSalesContract}Contract_Header"/&gt;
 *         &lt;element name="Contract_Items" type="{http://CreateSalesContract}Contract_Items" maxOccurs="unbounded"/&gt;
 *         &lt;element name="Contract_Partners" type="{http://CreateSalesContract}Contract_Partners"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "salesContractInput", propOrder = {
    "contractHeader",
    "contractItems",
    "contractPartners"
})
public class SalesContractInput {

    @XmlElement(name = "Contract_Header", required = true, nillable = true)
    protected ContractHeader contractHeader;
    @XmlElement(name = "Contract_Items", required = true, nillable = true)
    protected List<ContractItems> contractItems;
    @XmlElement(name = "Contract_Partners", required = true, nillable = true)
    protected ContractPartners contractPartners;

    /**
     * Gets the value of the contractHeader property.
     * 
     * @return
     *     possible object is
     *     {@link ContractHeader }
     *     
     */
    public ContractHeader getContractHeader() {
        return contractHeader;
    }

    /**
     * Sets the value of the contractHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractHeader }
     *     
     */
    public void setContractHeader(ContractHeader value) {
        this.contractHeader = value;
    }

    /**
     * Gets the value of the contractItems property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contractItems property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContractItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContractItems }
     * 
     * 
     */
    public List<ContractItems> getContractItems() {
        if (contractItems == null) {
            contractItems = new ArrayList<ContractItems>();
        }
        return this.contractItems;
    }

    /**
     * Gets the value of the contractPartners property.
     * 
     * @return
     *     possible object is
     *     {@link ContractPartners }
     *     
     */
    public ContractPartners getContractPartners() {
        return contractPartners;
    }

    /**
     * Sets the value of the contractPartners property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractPartners }
     *     
     */
    public void setContractPartners(ContractPartners value) {
        this.contractPartners = value;
    }

}
