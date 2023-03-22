
package updatesalescontract;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesContractChangeOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesContractChangeOutput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RETURN" type="{http://UpdateSalesContract}RETURN" maxOccurs="unbounded"/&gt;
 *         &lt;element name="CONTRACT_STAT" type="{http://UpdateSalesContract}CONTRACT_STAT" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesContractChangeOutput", propOrder = {
    "_return",
    "contractstat"
})
public class SalesContractChangeOutput {

    @XmlElement(name = "RETURN", required = true, nillable = true)
    protected List<RETURN> _return;
    @XmlElement(name = "CONTRACT_STAT", required = true, nillable = true)
    protected List<CONTRACTSTAT> contractstat;

    /**
     * Gets the value of the return property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the return property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRETURN().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RETURN }
     * 
     * 
     */
    public List<RETURN> getRETURN() {
        if (_return == null) {
            _return = new ArrayList<RETURN>();
        }
        return this._return;
    }

    /**
     * Gets the value of the contractstat property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contractstat property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCONTRACTSTAT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CONTRACTSTAT }
     * 
     * 
     */
    public List<CONTRACTSTAT> getCONTRACTSTAT() {
        if (contractstat == null) {
            contractstat = new ArrayList<CONTRACTSTAT>();
        }
        return this.contractstat;
    }

}
