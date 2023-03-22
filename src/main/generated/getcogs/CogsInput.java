
package getcogs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CogsInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CogsInput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ListInputs" type="{http://getCOGS}ListInputs" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CogsInput", propOrder = {
    "listInputs"
})
public class CogsInput {

    @XmlElement(name = "ListInputs", required = true, nillable = true)
    protected List<ListInputs> listInputs;

    /**
     * Gets the value of the listInputs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listInputs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListInputs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ListInputs }
     * 
     * 
     */
    public List<ListInputs> getListInputs() {
        if (listInputs == null) {
            listInputs = new ArrayList<ListInputs>();
        }
        return this.listInputs;
    }

}
