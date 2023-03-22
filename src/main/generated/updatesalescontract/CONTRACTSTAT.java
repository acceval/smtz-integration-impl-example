
package updatesalescontract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CONTRACT_STAT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CONTRACT_STAT"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CONTRACT_NO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ITEM_PMT_NO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SO_QTY" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DLVRY_QTY" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="INV_QTY" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CONTRACT_STAT", propOrder = {
    "contractno",
    "itempmtno",
    "soqty",
    "dlvryqty",
    "invqty"
})
public class CONTRACTSTAT {

    @XmlElement(name = "CONTRACT_NO", required = true, nillable = true)
    protected String contractno;
    @XmlElement(name = "ITEM_PMT_NO", required = true, nillable = true)
    protected String itempmtno;
    @XmlElement(name = "SO_QTY", required = true, nillable = true)
    protected String soqty;
    @XmlElement(name = "DLVRY_QTY", required = true, nillable = true)
    protected String dlvryqty;
    @XmlElement(name = "INV_QTY", required = true, nillable = true)
    protected String invqty;

    /**
     * Gets the value of the contractno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONTRACTNO() {
        return contractno;
    }

    /**
     * Sets the value of the contractno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONTRACTNO(String value) {
        this.contractno = value;
    }

    /**
     * Gets the value of the itempmtno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getITEMPMTNO() {
        return itempmtno;
    }

    /**
     * Sets the value of the itempmtno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setITEMPMTNO(String value) {
        this.itempmtno = value;
    }

    /**
     * Gets the value of the soqty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSOQTY() {
        return soqty;
    }

    /**
     * Sets the value of the soqty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSOQTY(String value) {
        this.soqty = value;
    }

    /**
     * Gets the value of the dlvryqty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDLVRYQTY() {
        return dlvryqty;
    }

    /**
     * Sets the value of the dlvryqty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDLVRYQTY(String value) {
        this.dlvryqty = value;
    }

    /**
     * Gets the value of the invqty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINVQTY() {
        return invqty;
    }

    /**
     * Sets the value of the invqty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINVQTY(String value) {
        this.invqty = value;
    }

}
