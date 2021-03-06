
package covfefe.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für TreasuresToGoType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="TreasuresToGoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="player" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="treasures" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TreasuresToGoType", propOrder = {
    "player",
    "treasures"
})
public class TreasuresToGoType {

    protected int player;
    protected int treasures;

    /**
     * Ruft den Wert der player-Eigenschaft ab.
     * 
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Legt den Wert der player-Eigenschaft fest.
     * 
     */
    public void setPlayer(int value) {
        this.player = value;
    }

    /**
     * Ruft den Wert der treasures-Eigenschaft ab.
     * 
     */
    public int getTreasures() {
        return treasures;
    }

    /**
     * Legt den Wert der treasures-Eigenschaft fest.
     * 
     */
    public void setTreasures(int value) {
        this.treasures = value;
    }

}
