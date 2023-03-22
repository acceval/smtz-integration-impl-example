
package getbom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getBOM complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getBOM"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="materialCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="plantCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getBOM", propOrder = {
    "materialCode",
    "plantCode"
})
public class GetBOM {

    @XmlElement(required = true, nillable = true)
    protected String materialCode;
    @XmlElement(required = true, nillable = true)
    protected String plantCode;

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
     * Gets the value of the plantCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlantCode() {
        return plantCode;
    }

    /**
     * Sets the value of the plantCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlantCode(String value) {
        this.plantCode = value;
    }

}
