//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.05 at 04:51:05 PM CEST 
//


package hello.entity.gov.gradskaskupstina;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TPotpisnici complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPotpisnici">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Potpisnik" type="{http://www.gradskaskupstina.gov/}TPotpisnik" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPotpisnici", propOrder = {
    "potpisnik"
})
public class TPotpisnici {

    @XmlElement(name = "Potpisnik", required = true)
    protected List<TPotpisnik> potpisnik;

    /**
     * Gets the value of the potpisnik property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the potpisnik property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPotpisnik().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TPotpisnik }
     * 
     * 
     */
    public List<TPotpisnik> getPotpisnik() {
        if (potpisnik == null) {
            potpisnik = new ArrayList<TPotpisnik>();
        }
        return this.potpisnik;
    }

    @Override
    public String toString(){
        StringBuilder retValue = new StringBuilder("Potpisnici: ");
        for(TPotpisnik osoba : potpisnik){
            retValue.append(osoba.toString() + ", ");
        }
        return retValue.substring(0, retValue.length()-2);
    }
}
