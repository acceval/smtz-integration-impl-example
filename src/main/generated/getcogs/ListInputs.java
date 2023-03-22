
package getcogs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListInputs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListInputs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Plant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MaterialCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ValidOnDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListInputs", propOrder = {
    "plant",
    "materialCode",
    "validOnDate"
})
public class ListInputs {

    @XmlElement(name = "Plant", required = true, nillable = true)
    protected String plant;
    @XmlElement(name = "MaterialCode", required = true, nillable = true)
    protected String materialCode;
    @XmlElement(name = "ValidOnDate", required = true, nillable = true)
    protected String validOnDate;

    /**
     * Gets the value of the plant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlant() {
        return plant;
    }

    /**
     * Sets the value of the plant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlant(String value) {
        this.plant = value;
    }

    /**
     * Gets the value of the materialCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialCode() {
        return materialCode;
    }

    /**
     * Sets the value of the materialCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialCode(String value) {
        this.materialCode = value;
    }

    /**
     * Gets the value of the validOnDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidOnDate() {
        return validOnDate;
    }

    /**
     * Sets the value of the validOnDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidOnDate(String value) {
        this.validOnDate = value;
    }

}
