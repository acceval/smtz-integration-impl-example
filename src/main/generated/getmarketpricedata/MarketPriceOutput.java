
package getmarketpricedata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for marketPriceOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="marketPriceOutput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="QUOSRC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="QUOTNO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="QUOTDATE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PRICE_H" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PRICE_L" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "marketPriceOutput", propOrder = {
    "quosrc",
    "quotno",
    "quotdate",
    "priceh",
    "pricel"
})
public class MarketPriceOutput {

    @XmlElement(name = "QUOSRC", required = true, nillable = true)
    protected String quosrc;
    @XmlElement(name = "QUOTNO", required = true, nillable = true)
    protected String quotno;
    @XmlElement(name = "QUOTDATE", required = true, nillable = true)
    protected String quotdate;
    @XmlElement(name = "PRICE_H", required = true, nillable = true)
    protected String priceh;
    @XmlElement(name = "PRICE_L", required = true, nillable = true)
    protected String pricel;

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
     * Gets the value of the quotdate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQUOTDATE() {
        return quotdate;
    }

    /**
     * Sets the value of the quotdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQUOTDATE(String value) {
        this.quotdate = value;
    }

    /**
     * Gets the value of the priceh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRICEH() {
        return priceh;
    }

    /**
     * Sets the value of the priceh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRICEH(String value) {
        this.priceh = value;
    }

    /**
     * Gets the value of the pricel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRICEL() {
        return pricel;
    }

    /**
     * Sets the value of the pricel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRICEL(String value) {
        this.pricel = value;
    }

}
