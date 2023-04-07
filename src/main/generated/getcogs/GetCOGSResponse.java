
package getcogs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getCOGSResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getCOGSResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ListItems" type="{http://getCOGS}CogsOutput" maxOccurs="unbounded"/&gt;
 *         &lt;element name="ListComponents" type="{http://getCOGS}CogsCompOutput" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCOGSResponse", propOrder = {
    "listItems",
    "listComponents"
})
public class GetCOGSResponse {

    @XmlElement(name = "ListItems", required = true, nillable = true)
    protected List<CogsOutput> listItems;
    @XmlElement(name = "ListComponents", required = true, nillable = true)
    protected List<CogsCompOutput> listComponents;

    /**
     * Gets the value of the listItems property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listItems property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CogsOutput }
     * 
     * 
     */
    public List<CogsOutput> getListItems() {
        if (listItems == null) {
            listItems = new ArrayList<CogsOutput>();
        }
        return this.listItems;
    }

    /**
     * Gets the value of the listComponents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listComponents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListComponents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CogsCompOutput }
     * 
     * 
     */
    public List<CogsCompOutput> getListComponents() {
        if (listComponents == null) {
            listComponents = new ArrayList<CogsCompOutput>();
        }
        return this.listComponents;
    }

}
