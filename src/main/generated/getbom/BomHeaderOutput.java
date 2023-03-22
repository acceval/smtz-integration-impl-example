
package getbom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for bomHeaderOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="bomHeaderOutput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="materialNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="confirmedQuantity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="unitOfMeasure" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="plant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="assetClass" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="alternativeBOM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="validFromDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bomHeaderOutput", propOrder = {
    "materialNumber",
    "confirmedQuantity",
    "unitOfMeasure",
    "plant",
    "assetClass",
    "alternativeBOM",
    "validFromDate"
})
public class BomHeaderOutput {

    @XmlElement(required = true, nillable = true)
    protected String materialNumber;
    @XmlElement(required = true, nillable = true)
    protected String confirmedQuantity;
    @XmlElement(required = true, nillable = true)
    protected String unitOfMeasure;
    @XmlElement(required = true, nillable = true)
    protected String plant;
    @XmlElement(required = true, nillable = true)
    protected String assetClass;
    @XmlElement(required = true, nillable = true)
    protected String alternativeBOM;
    @XmlElement(required = true, nillable = true)
    protected String validFromDate;

    /**
     * Gets the value of the materialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialNumber() {
        return materialNumber;
    }

    /**
     * Sets the value of the materialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialNumber(String value) {
        this.materialNumber = value;
    }

    /**
     * Gets the value of the confirmedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfirmedQuantity() {
        return confirmedQuantity;
    }

    /**
     * Sets the value of the confirmedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfirmedQuantity(String value) {
        this.confirmedQuantity = value;
    }

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitOfMeasure(String value) {
        this.unitOfMeasure = value;
    }

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
     * Gets the value of the assetClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssetClass() {
        return assetClass;
    }

    /**
     * Sets the value of the assetClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssetClass(String value) {
        this.assetClass = value;
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

    /**
     * Gets the value of the validFromDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidFromDate() {
        return validFromDate;
    }

    /**
     * Sets the value of the validFromDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidFromDate(String value) {
        this.validFromDate = value;
    }

}
