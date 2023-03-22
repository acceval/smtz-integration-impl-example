
package getbom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bomItemOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bomItemOutput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BOMComponent" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="materialDescription" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="componentQuantity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="baseUnitOfMeasure" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="deletionIndicator" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="alternativeBOM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bomItemOutput", propOrder = {
    "bomComponent",
    "materialDescription",
    "componentQuantity",
    "baseUnitOfMeasure",
    "deletionIndicator",
    "alternativeBOM"
})
public class BomItemOutput {

    @XmlElement(name = "BOMComponent", required = true, nillable = true)
    protected String bomComponent;
    @XmlElement(required = true, nillable = true)
    protected String materialDescription;
    @XmlElement(required = true, nillable = true)
    protected String componentQuantity;
    @XmlElement(required = true, nillable = true)
    protected String baseUnitOfMeasure;
    @XmlElement(required = true, nillable = true)
    protected String deletionIndicator;
    @XmlElement(required = true, nillable = true)
    protected String alternativeBOM;

    /**
     * Gets the value of the bomComponent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBOMComponent() {
        return bomComponent;
    }

    /**
     * Sets the value of the bomComponent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBOMComponent(String value) {
        this.bomComponent = value;
    }

    /**
     * Gets the value of the materialDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialDescription() {
        return materialDescription;
    }

    /**
     * Sets the value of the materialDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialDescription(String value) {
        this.materialDescription = value;
    }

    /**
     * Gets the value of the componentQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComponentQuantity() {
        return componentQuantity;
    }

    /**
     * Sets the value of the componentQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComponentQuantity(String value) {
        this.componentQuantity = value;
    }

    /**
     * Gets the value of the baseUnitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaseUnitOfMeasure() {
        return baseUnitOfMeasure;
    }

    /**
     * Sets the value of the baseUnitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseUnitOfMeasure(String value) {
        this.baseUnitOfMeasure = value;
    }

    /**
     * Gets the value of the deletionIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeletionIndicator() {
        return deletionIndicator;
    }

    /**
     * Sets the value of the deletionIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeletionIndicator(String value) {
        this.deletionIndicator = value;
    }

    /**
     * Gets the value of the alternativeBOM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternativeBOM() {
        return alternativeBOM;
    }

    /**
     * Sets the value of the alternativeBOM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternativeBOM(String value) {
        this.alternativeBOM = value;
    }

}
