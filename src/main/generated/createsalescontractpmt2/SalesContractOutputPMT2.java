
package createsalescontractpmt2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for salesContractOutputPMT2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="salesContractOutputPMT2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ContractNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RETURN" type="{http://CreateSalesContractPMT2}RETURN" maxOccurs="unbounded"/&gt;
 *         &lt;element name="EXTENSIONIN" type="{http://CreateSalesContractPMT2}EXTENSIONIN" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "salesContractOutputPMT2", propOrder = {
    "contractNumber",
    "_return",
    "extensionin"
})
public class SalesContractOutputPMT2 {

    @XmlElement(name = "ContractNumber", required = true, nillable = true)
    protected String contractNumber;
    @XmlElement(name = "RETURN", required = true, nillable = true)
    protected List<RETURN> _return;
    @XmlElement(name = "EXTENSIONIN", required = true, nillable = true)
    protected List<EXTENSIONIN> extensionin;

    /**
     * Gets the value of the contractNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * Sets the value of the contractNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNumber(String value) {
        this.contractNumber = value;
    }

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
     * Gets the value of the extensionin property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extensionin property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEXTENSIONIN().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EXTENSIONIN }
     * 
     * 
     */
    public List<EXTENSIONIN> getEXTENSIONIN() {
        if (extensionin == null) {
            extensionin = new ArrayList<EXTENSIONIN>();
        }
        return this.extensionin;
    }

}
