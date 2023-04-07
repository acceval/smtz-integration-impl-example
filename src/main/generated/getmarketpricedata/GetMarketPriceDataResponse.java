
package getmarketpricedata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getMarketPriceDataResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getMarketPriceDataResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="marketPrice" type="{http://getMarketPriceData}marketPrice"/&gt;
 *         &lt;element name="ETRETURN" type="{http://getMarketPriceData}ETRETURN"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getMarketPriceDataResponse", propOrder = {
    "marketPrice",
    "etreturn"
})
public class GetMarketPriceDataResponse {

    @XmlElement(required = true, nillable = true)
    protected MarketPrice marketPrice;
    @XmlElement(name = "ETRETURN", required = true, nillable = true)
    protected ETRETURN etreturn;

    /**
     * Gets the value of the marketPrice property.
     * 
     * @return
     *     possible object is
     *     {@link MarketPrice }
     *     
     */
    public MarketPrice getMarketPrice() {
        return marketPrice;
    }

    /**
     * Sets the value of the marketPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link MarketPrice }
     *     
     */
    public void setMarketPrice(MarketPrice value) {
        this.marketPrice = value;
    }

    /**
     * Gets the value of the etreturn property.
     * 
     * @return
     *     possible object is
     *     {@link ETRETURN }
     *     
     */
    public ETRETURN getETRETURN() {
        return etreturn;
    }

    /**
     * Sets the value of the etreturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ETRETURN }
     *     
     */
    public void setETRETURN(ETRETURN value) {
        this.etreturn = value;
    }

}
