
package getbom;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getBOMResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getBOMResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="headerOutput" type="{http://getBOM}bomHeaderOutput" maxOccurs="unbounded"/&gt;
 *         &lt;element name="itemOutput" type="{http://getBOM}bomItemOutput" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getBOMResponse", propOrder = {
    "headerOutput",
    "itemOutput"
})
public class GetBOMResponse {

    @XmlElement(required = true, nillable = true)
    protected List<BomHeaderOutput> headerOutput;
    @XmlElement(required = true, nillable = true)
    protected List<BomItemOutput> itemOutput;

    /**
     * Gets the value of the headerOutput property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the headerOutput property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHeaderOutput().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BomHeaderOutput }
     * 
     * 
     */
    public List<BomHeaderOutput> getHeaderOutput() {
        if (headerOutput == null) {
            headerOutput = new ArrayList<BomHeaderOutput>();
        }
        return this.headerOutput;
    }

    /**
     * Gets the value of the itemOutput property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemOutput property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemOutput().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BomItemOutput }
     * 
     * 
     */
    public List<BomItemOutput> getItemOutput() {
        if (itemOutput == null) {
            itemOutput = new ArrayList<BomItemOutput>();
        }
        return this.itemOutput;
    }

}
