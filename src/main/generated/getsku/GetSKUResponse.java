
package getsku;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSKUResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSKUResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="skuOutput" type="{http://getSKU}skuOutput" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSKUResponse", propOrder = {
    "skuOutput"
})
public class GetSKUResponse {

    @XmlElement(required = true, nillable = true)
    protected List<SkuOutput> skuOutput;

    /**
     * Gets the value of the skuOutput property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the skuOutput property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSkuOutput().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SkuOutput }
     * 
     * 
     */
    public List<SkuOutput> getSkuOutput() {
        if (skuOutput == null) {
            skuOutput = new ArrayList<SkuOutput>();
        }
        return this.skuOutput;
    }

}
