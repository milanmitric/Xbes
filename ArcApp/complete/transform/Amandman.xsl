<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns0="http://www.gradskaskupstina.gov/"
>
    <xsl:template match="/">
        <html>
            <head>
            </head>
            <body>

                <p>
                    Pravni osnov: <br></br>

                    <xsl:if test="Amandmain/PravniOsnov/@ref_akt">
                        Referencirani akt: <xsl:value-of select="Amandmani/PravniOsnov/@ref_akt"></xsl:value-of> <br></br>
                    </xsl:if>
                    <xsl:if test="Amandmani/PravniOsnov/@ref_clanovi">
                        Referencirani clanovi: <xsl:value-of select="Amandmani/PravniOsnov/@ref_clanovi"/> <br></br>
                    </xsl:if>
                    <xsl:if test="Amandmani/PravniOsnov/@ref_stavovi">
                        Referencirani stavovi: <xsl:value-of select="Amandmani/PravniOsnov/@ref_stavovi"/> <br></br>
                    </xsl:if>
                    <xsl:if test="Amandmani/PravniOsnov/@ref_tacke">
                        Referencirane tacke: <xsl:value-of select="Amandmani/PravniOsnov/@ref_tacke"/> <br></br>
                    </xsl:if>
                </p>
                <p>
                    Akt: <xsl:value-of select="Amandmani/Akt/text()"/>
                </p>

                <xsl:for-each select="Amandmani/Amandman">
                    <p>
                        Predmet izmene: <br/>
                        <xsl:if test="PredmetIzmene/@ref_clanovi">
                            Clan: <xsl:value-of select="PredmetIzmene/@ref_clanovi"/> <br/>
                        </xsl:if>
                        <xsl:if test="PredmetIzmene/@ref_stavovi">
                            Stav: <xsl:value-of select="PredmetIzmene/@ref_stavovi"/> <br/>
                        </xsl:if>
                        <xsl:if test="PredmetIzmene/@ref_tacke">
                            Tacka: <xsl:value-of select="PredmetIzmene/@ref_tacke"/> <br/>
                        </xsl:if>
                        Tip izmene: <xsl:value-of select="TipIzmene/text()"/> <br/>
                        Obrazlozenje: <xsl:value-of select="Obrazlozenje/text()"/> <br/>
                    </p>
                </xsl:for-each>
                Potpisnici:
                <p>
                    <xsl:for-each select="Amandmani/Potpisnici/Potpisnik">
                        <xsl:if test="Titula"><xsl:value-of select="Titula"/>&#160;</xsl:if>
                        <xsl:value-of select="Ime"/> &#160;
                        <xsl:value-of select="Prezime"/>,
                    </xsl:for-each>
                </p>

            </body>
        </html>
    </xsl:template>


</xsl:stylesheet>