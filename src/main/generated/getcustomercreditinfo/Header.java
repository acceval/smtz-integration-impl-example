
package getcustomercreditinfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Header complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Header"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CustomerSoldToNo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreditControlArea" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Header", propOrder = {
    "customerSoldToNo",
    "creditControlArea"
})
public class Header {

    @XmlElement(name = "CustomerSoldToNo", required = true, nillable = true)
    protected String customerSoldToNo;
    @XmlElement(name = "CreditControlArea", required = true, nillable = true)
    protected String creditControlArea;

    /**
     * Gets the value of the customerSoldToNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerSoldToNo() {
        return customerSoldToNo;
    }

    /**
     * Sets the value of the customerSoldToNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerSoldToNo(String value) {
        this.customerSoldToNo = value;
    }

    /**
     * Gets the value of the creditControlArea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditControlArea() {
        return creditControlArea;
    }

    /**
     * Sets the value of the creditControlArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditControlArea(String value) {
        this.creditControlArea = value;
    }

}
