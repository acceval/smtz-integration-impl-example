
package forexhandler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ForexOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ForexOutput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FromCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ToCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Rate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ExchangeRateType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RatioFrom" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RatioTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ForexOutput", propOrder = {
    "effectiveDate",
    "fromCurrency",
    "toCurrency",
    "rate",
    "exchangeRateType",
    "ratioFrom",
    "ratioTo"
})
public class ForexOutput {

    @XmlElement(name = "EffectiveDate", required = true, nillable = true)
    protected String effectiveDate;
    @XmlElement(name = "FromCurrency", required = true, nillable = true)
    protected String fromCurrency;
    @XmlElement(name = "ToCurrency", required = true, nillable = true)
    protected String toCurrency;
    @XmlElement(name = "Rate", required = true, nillable = true)
    protected String rate;
    @XmlElement(name = "ExchangeRateType", required = true, nillable = true)
    protected String exchangeRateType;
    @XmlElement(name = "RatioFrom", required = true, nillable = true)
    protected String ratioFrom;
    @XmlElement(name = "RatioTo", required = true, nillable = true)
    protected String ratioTo;

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffectiveDate(String value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the fromCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromCurrency() {
        return fromCurrency;
    }

    /**
     * Sets the value of the fromCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromCurrency(String value) {
        this.fromCurrency = value;
    }

    /**
     * Gets the value of the toCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToCurrency() {
        return toCurrency;
    }

    /**
     * Sets the value of the toCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToCurrency(String value) {
        this.toCurrency = value;
    }

    /**
     * Gets the value of the rate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRate() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRate(String value) {
        this.rate = value;
    }

    /**
     * Gets the value of the exchangeRateType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExchangeRateType() {
        return exchangeRateType;
    }

    /**
     * Sets the value of the exchangeRateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExchangeRateType(String value) {
        this.exchangeRateType = value;
    }

    /**
     * Gets the value of the ratioFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatioFrom() {
        return ratioFrom;
    }

    /**
     * Sets the value of the ratioFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatioFrom(String value) {
        this.ratioFrom = value;
    }

    /**
     * Gets the value of the ratioTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatioTo() {
        return ratioTo;
    }

    /**
     * Sets the value of the ratioTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatioTo(String value) {
        this.ratioTo = value;
    }

}
