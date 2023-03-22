
package updatesalescontract;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesContractChangeInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesContractChangeInput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Contract_Header" type="{http://UpdateSalesContract}Contract_Header"/&gt;
 *         &lt;element name="Contract_Items" type="{http://UpdateSalesContract}Contract_Items" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesContractChangeInput", propOrder = {
    "contractHeader",
    "contractItems"
})
public class SalesContractChangeInput {

    @XmlElement(name = "Contract_Header", required = true, nillable = true)
    protected ContractHeader contractHeader;
    @XmlElement(name = "Contract_Items", required = true, nillable = true)
    protected List<ContractItems> contractItems;

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

}
