
package covfefe.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für AcceptMessageType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="AcceptMessageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accept" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="errorCode" type="{}ErrorType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcceptMessageType", propOrder = {
    "accept",
    "errorCode"
})
public class AcceptMessageType {

    protected boolean accept;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ErrorType errorCode;

    /**
     * Ruft den Wert der accept-Eigenschaft ab.
     * 
     */
    public boolean isAccept() {
        return accept;
    }

    /**
     * Legt den Wert der accept-Eigenschaft fest.
     * 
     */
    public void setAccept(boolean value) {
        this.accept = value;
    }

    /**
     * Ruft den Wert der errorCode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ErrorType }
     *     
     */
    public ErrorType getErrorCode() {
        return errorCode;
    }

    /**
     * Legt den Wert der errorCode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorType }
     *     
     */
    public void setErrorCode(ErrorType value) {
        this.errorCode = value;
    }

}
