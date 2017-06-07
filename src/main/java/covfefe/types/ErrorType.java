
package covfefe.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für ErrorType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="ErrorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NOERROR"/>
 *     &lt;enumeration value="ERROR"/>
 *     &lt;enumeration value="AWAIT_LOGIN"/>
 *     &lt;enumeration value="AWAIT_MOVE"/>
 *     &lt;enumeration value="ILLEGAL_MOVE"/>
 *     &lt;enumeration value="TIMEOUT"/>
 *     &lt;enumeration value="TOO_MANY_TRIES"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ErrorType")
@XmlEnum
public enum ErrorType {

    NOERROR,
    ERROR,
    AWAIT_LOGIN,
    AWAIT_MOVE,
    ILLEGAL_MOVE,
    TIMEOUT,
    TOO_MANY_TRIES;

    public String value() {
        return name();
    }

    public static ErrorType fromValue(String v) {
        return valueOf(v);
    }

}
