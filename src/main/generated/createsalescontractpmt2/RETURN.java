
package createsalescontractpmt2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RETURN complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RETURN"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MESSAGE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LOG_NO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LOG_MSG_NO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MESSAGE_V1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MESSAGE_V2" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MESSAGE_V3" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MESSAGE_V4" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PARAMETER" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ROW" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FIELD" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SYSTEM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RETURN", propOrder = {
    "type",
    "id",
    "number",
    "message",
    "logno",
    "logmsgno",
    "messagev1",
    "messagev2",
    "messagev3",
    "messagev4",
    "parameter",
    "row",
    "field",
    "system"
})
public class RETURN {

    @XmlElement(name = "TYPE", required = true, nillable = true)
    protected String type;
    @XmlElement(name = "ID", required = true, nillable = true)
    protected String id;
    @XmlElement(name = "NUMBER", required = true, nillable = true)
    protected String number;
    @XmlElement(name = "MESSAGE", required = true, nillable = true)
    protected String message;
    @XmlElement(name = "LOG_NO", required = true, nillable = true)
    protected String logno;
    @XmlElement(name = "LOG_MSG_NO", required = true, nillable = true)
    protected String logmsgno;
    @XmlElement(name = "MESSAGE_V1", required = true, nillable = true)
    protected String messagev1;
    @XmlElement(name = "MESSAGE_V2", required = true, nillable = true)
    protected String messagev2;
    @XmlElement(name = "MESSAGE_V3", required = true, nillable = true)
    protected String messagev3;
    @XmlElement(name = "MESSAGE_V4", required = true, nillable = true)
    protected String messagev4;
    @XmlElement(name = "PARAMETER", required = true, nillable = true)
    protected String parameter;
    @XmlElement(name = "ROW", required = true, nillable = true)
    protected String row;
    @XmlElement(name = "FIELD", required = true, nillable = true)
    protected String field;
    @XmlElement(name = "SYSTEM", required = true, nillable = true)
    protected String system;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTYPE() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTYPE(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMBER() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMBER(String value) {
        this.number = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGE() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGE(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the logno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOGNO() {
        return logno;
    }

    /**
     * Sets the value of the logno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOGNO(String value) {
        this.logno = value;
    }

    /**
     * Gets the value of the logmsgno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLOGMSGNO() {
        return logmsgno;
    }

    /**
     * Sets the value of the logmsgno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLOGMSGNO(String value) {
        this.logmsgno = value;
    }

    /**
     * Gets the value of the messagev1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGEV1() {
        return messagev1;
    }

    /**
     * Sets the value of the messagev1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGEV1(String value) {
        this.messagev1 = value;
    }

    /**
     * Gets the value of the messagev2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGEV2() {
        return messagev2;
    }

    /**
     * Sets the value of the messagev2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGEV2(String value) {
        this.messagev2 = value;
    }

    /**
     * Gets the value of the messagev3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGEV3() {
        return messagev3;
    }

    /**
     * Sets the value of the messagev3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGEV3(String value) {
        this.messagev3 = value;
    }

    /**
     * Gets the value of the messagev4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESSAGEV4() {
        return messagev4;
    }

    /**
     * Sets the value of the messagev4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESSAGEV4(String value) {
        this.messagev4 = value;
    }

    /**
     * Gets the value of the parameter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPARAMETER() {
        return parameter;
    }

    /**
     * Sets the value of the parameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPARAMETER(String value) {
        this.parameter = value;
    }

    /**
     * Gets the value of the row property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getROW() {
        return row;
    }

    /**
     * Sets the value of the row property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setROW(String value) {
        this.row = value;
    }

    /**
     * Gets the value of the field property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIELD() {
        return field;
    }

    /**
     * Sets the value of the field property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIELD(String value) {
        this.field = value;
    }

    /**
     * Gets the value of the system property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSYSTEM() {
        return system;
    }

    /**
     * Sets the value of the system property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSYSTEM(String value) {
        this.system = value;
    }

}
