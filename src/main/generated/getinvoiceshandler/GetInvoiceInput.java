
package getinvoiceshandler;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetInvoiceInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetInvoiceInput"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DateFrom" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DateTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SalesOrganizations" type="{http://GetInvoicesHandler}SalesOrganizations" maxOccurs="unbounded"/&gt;
 *         &lt;element name="Divisions" type="{http://GetInvoicesHandler}Divisions" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetInvoiceInput", propOrder = {
    "dateFrom",
    "dateTo",
    "salesOrganizations",
    "divisions"
})
public class GetInvoiceInput {

    @XmlElement(name = "DateFrom", required = true, nillable = true)
    protected String dateFrom;
    @XmlElement(name = "DateTo", required = true, nillable = true)
    protected String dateTo;
    @XmlElement(name = "SalesOrganizations", required = true, nillable = true)
    protected List<SalesOrganizations> salesOrganizations;
    @XmlElement(name = "Divisions", required = true, nillable = true)
    protected List<Divisions> divisions;

    /**
     * Gets the value of the dateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateFrom() {
        return dateFrom;
    }

    /**
     * Sets the value of the dateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateFrom(String value) {
        this.dateFrom = value;
    }

    /**
     * Gets the value of the dateTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateTo() {
        return dateTo;
    }

    /**
     * Sets the value of the dateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateTo(String value) {
        this.dateTo = value;
    }

    /**
     * Gets the value of the salesOrganizations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the salesOrganizations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSalesOrganizations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SalesOrganizations }
     * 
     * 
     */
    public List<SalesOrganizations> getSalesOrganizations() {
        if (salesOrganizations == null) {
            salesOrganizations = new ArrayList<SalesOrganizations>();
        }
        return this.salesOrganizations;
    }

    /**
     * Gets the value of the divisions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the divisions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDivisions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Divisions }
     * 
     * 
     */
    public List<Divisions> getDivisions() {
        if (divisions == null) {
            divisions = new ArrayList<Divisions>();
        }
        return this.divisions;
    }

}
