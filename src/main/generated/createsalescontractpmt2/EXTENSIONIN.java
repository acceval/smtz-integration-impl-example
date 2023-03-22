
package createsalescontractpmt2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EXTENSIONIN complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EXTENSIONIN"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="STRUCTURE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VALUEPART1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VALUEPART2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VALUEPART3" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VALUEPART4" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EXTENSIONIN", propOrder = {
    "structure",
    "valuepart1",
    "valuepart2",
    "valuepart3",
    "valuepart4"
})
public class EXTENSIONIN {

    @XmlElement(name = "STRUCTURE", required = true, nillable = true)
    protected String structure;
    @XmlElement(name = "VALUEPART1", required = true, nillable = true)
    protected String valuepart1;
    @XmlElement(name = "VALUEPART2", required = true, nillable = true)
    protected String valuepart2;
    @XmlElement(name = "VALUEPART3", required = true, nillable = true)
    protected String valuepart3;
    @XmlElement(name = "VALUEPART4", required = true, nillable = true)
    protected String valuepart4;

    /**
     * Gets the value of the structure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTRUCTURE() {
        return structure;
    }

    /**
     * Sets the value of the structure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTRUCTURE(String value) {
        this.structure = value;
    }

    /**
     * Gets the value of the valuepart1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVALUEPART1() {
        return valuepart1;
    }

    /**
     * Sets the value of the valuepart1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVALUEPART1(String value) {
        this.valuepart1 = value;
    }

    /**
     * Gets the value of the valuepart2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVALUEPART2() {
        return valuepart2;
    }

    /**
     * Sets the value of the valuepart2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVALUEPART2(String value) {
        this.valuepart2 = value;
    }

    /**
     * Gets the value of the valuepart3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVALUEPART3() {
        return valuepart3;
    }

    /**
     * Sets the value of the valuepart3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVALUEPART3(String value) {
        this.valuepart3 = value;
    }

    /**
     * Gets the value of the valuepart4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVALUEPART4() {
        return valuepart4;
    }

    /**
     * Sets the value of the valuepart4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVALUEPART4(String value) {
        this.valuepart4 = value;
    }

}
