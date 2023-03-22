
package getsku;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for skuOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="skuOutput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="materialNo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="baseUnitOfMeasure" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="unitOfMeasureDisplay" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="numeratorConversion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="denominatorConversion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="materialDescription" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "skuOutput", propOrder = {
    "materialNo",
    "baseUnitOfMeasure",
    "unitOfMeasureDisplay",
    "numeratorConversion",
    "denominatorConversion",
    "materialDescription"
})
public class SkuOutput {

    @XmlElement(required = true, nillable = true)
    protected String materialNo;
    @XmlElement(required = true, nillable = true)
    protected String baseUnitOfMeasure;
    @XmlElement(required = true, nillable = true)
    protected String unitOfMeasureDisplay;
    @XmlElement(required = true, nillable = true)
    protected String numeratorConversion;
    @XmlElement(required = true, nillable = true)
    protected String denominatorConversion;
    @XmlElement(required = true, nillable = true)
    protected String materialDescription;

    /**
     * Gets the value of the materialNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialNo() {
        return materialNo;
    }

    /**
     * Sets the value of the materialNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialNo(String value) {
        this.materialNo = value;
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
     * Gets the value of the unitOfMeasureDisplay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitOfMeasureDisplay() {
        return unitOfMeasureDisplay;
    }

    /**
     * Sets the value of the unitOfMeasureDisplay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitOfMeasureDisplay(String value) {
        this.unitOfMeasureDisplay = value;
    }

    /**
     * Gets the value of the numeratorConversion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeratorConversion() {
        return numeratorConversion;
    }

    /**
     * Sets the value of the numeratorConversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeratorConversion(String value) {
        this.numeratorConversion = value;
    }

    /**
     * Gets the value of the denominatorConversion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominatorConversion() {
        return denominatorConversion;
    }

    /**
     * Sets the value of the denominatorConversion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominatorConversion(String value) {
        this.denominatorConversion = value;
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

}
