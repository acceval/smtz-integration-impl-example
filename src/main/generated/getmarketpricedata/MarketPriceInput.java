
package getmarketpricedata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for marketPriceInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="marketPriceInput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="QUOSRC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="QUOTNO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZZDTFROM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ZZDTTO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "marketPriceInput", propOrder = {
    "quosrc",
    "quotno",
    "zzdtfrom",
    "zzdtto"
})
public class MarketPriceInput {

    @XmlElement(name = "QUOSRC", required = true, nillable = true)
    protected String quosrc;
    @XmlElement(name = "QUOTNO", required = true, nillable = true)
    protected String quotno;
    @XmlElement(name = "ZZDTFROM", required = true, nillable = true)
    protected String zzdtfrom;
    @XmlElement(name = "ZZDTTO", required = true, nillable = true)
    protected String zzdtto;

    /**
     * Gets the value of the quosrc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQUOSRC() {
        return quosrc;
    }

    /**
     * Sets the value of the quosrc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQUOSRC(String value) {
        this.quosrc = value;
    }

    /**
     * Gets the value of the quotno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQUOTNO() {
        return quotno;
    }

    /**
     * Sets the value of the quotno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQUOTNO(String value) {
        this.quotno = value;
    }

    /**
     * Gets the value of the zzdtfrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZZDTFROM() {
        return zzdtfrom;
    }

    /**
     * Sets the value of the zzdtfrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZZDTFROM(String value) {
        this.zzdtfrom = value;
    }

    /**
     * Gets the value of the zzdtto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZZDTTO() {
        return zzdtto;
    }

    /**
     * Sets the value of the zzdtto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZZDTTO(String value) {
        this.zzdtto = value;
    }

}
