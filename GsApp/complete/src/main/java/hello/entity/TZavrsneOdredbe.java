//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.24 at 06:27:21 PM CEST 
//


package hello.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TZavrsneOdredbe complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TZavrsneOdredbe">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Drzava" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="Pokrajina" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Grad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Skupstina" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Broj" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Datum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Potpisnik" type="{http://www.gradskaskupstina.gov/}TPotpisnik"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TZavrsneOdredbe", propOrder = {
    "drzava",
    "pokrajina",
    "grad",
    "skupstina",
    "broj",
    "datum",
    "potpisnik"
})
public class TZavrsneOdredbe {

    @XmlElement(name = "Drzava", required = true)
    protected String drzava;
    @XmlElement(name = "Pokrajina", required = true)
    protected String pokrajina;
    @XmlElement(name = "Grad", required = true)
    protected String grad;
    @XmlElement(name = "Skupstina", required = true)
    protected String skupstina;
    @XmlElement(name = "Broj", required = true)
    protected String broj;
    @XmlElement(name = "Datum", required = true)
    protected String datum;
    @XmlElement(name = "Potpisnik", required = true)
    protected TPotpisnik potpisnik;

    /**
     * Gets the value of the drzava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrzava() {
        return drzava;
    }

    /**
     * Sets the value of the drzava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrzava(String value) {
        this.drzava = value;
    }

    /**
     * Gets the value of the pokrajina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPokrajina() {
        return pokrajina;
    }

    /**
     * Sets the value of the pokrajina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPokrajina(String value) {
        this.pokrajina = value;
    }

    /**
     * Gets the value of the grad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrad() {
        return grad;
    }

    /**
     * Sets the value of the grad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrad(String value) {
        this.grad = value;
    }

    /**
     * Gets the value of the skupstina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkupstina() {
        return skupstina;
    }

    /**
     * Sets the value of the skupstina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkupstina(String value) {
        this.skupstina = value;
    }

    /**
     * Gets the value of the broj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBroj() {
        return broj;
    }

    /**
     * Sets the value of the broj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBroj(String value) {
        this.broj = value;
    }

    /**
     * Gets the value of the datum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatum() {
        return datum;
    }

    /**
     * Sets the value of the datum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatum(String value) {
        this.datum = value;
    }

    /**
     * Gets the value of the potpisnik property.
     * 
     * @return
     *     possible object is
     *     {@link TPotpisnik }
     *     
     */
    public TPotpisnik getPotpisnik() {
        return potpisnik;
    }

    /**
     * Sets the value of the potpisnik property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPotpisnik }
     *     
     */
    public void setPotpisnik(TPotpisnik value) {
        this.potpisnik = value;
    }

}