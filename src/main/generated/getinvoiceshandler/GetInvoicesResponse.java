
package getinvoiceshandler;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getInvoicesResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getInvoicesResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Invoices" type="{http://GetInvoicesHandler}GetInvoiceOutput" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getInvoicesResponse", propOrder = {
    "invoices"
})
public class GetInvoicesResponse {

    @XmlElement(name = "Invoices", required = true, nillable = true)
    protected List<GetInvoiceOutput> invoices;

    /**
     * Gets the value of the invoices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GetInvoiceOutput }
     * 
     * 
     */
    public List<GetInvoiceOutput> getInvoices() {
        if (invoices == null) {
            invoices = new ArrayList<GetInvoiceOutput>();
        }
        return this.invoices;
    }

}
