
package covfefe.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für positionType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="positionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="row" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="col" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "positionType", propOrder = {
    "row",
    "col"
})
public class PositionType {

    protected int row;
    protected int col;

    /**
     * Ruft den Wert der row-Eigenschaft ab.
     * 
     */
    public int getRow() {
        return row;
    }

    /**
     * Legt den Wert der row-Eigenschaft fest.
     * 
     */
    public void setRow(int value) {
        this.row = value;
    }

    /**
     * Ruft den Wert der col-Eigenschaft ab.
     * 
     */
    public int getCol() {
        return col;
    }

    /**
     * Legt den Wert der col-Eigenschaft fest.
     * 
     */
    public void setCol(int value) {
        this.col = value;
    }

}
