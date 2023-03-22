
package createsalescontractpmt2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Contract_Partners complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Contract_Partners"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SoldTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ShipTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contract_Partners", propOrder = {
    "soldTo",
    "shipTo"
})
public class ContractPartners {

    @XmlElement(name = "SoldTo", required = true, nillable = true)
    protected String soldTo;
    @XmlElement(name = "ShipTo", required = true, nillable = true)
    protected String shipTo;

    /**
     * Gets the value of the soldTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoldTo() {
        return soldTo;
    }

    /**
     * Sets the value of the soldTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoldTo(String value) {
        this.soldTo = value;
    }

    /**
     * Gets the value of the shipTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipTo() {
        return shipTo;
    }

    /**
     * Sets the value of the shipTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipTo(String value) {
        this.shipTo = value;
    }

}
