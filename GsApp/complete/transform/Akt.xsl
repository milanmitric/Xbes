<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
		<head>
		</head>
		<body>
			
			<h2><xsl:value-of select="Akt/Naslov"></xsl:value-of> </h2>

            <xsl:for-each select="Akt/Deo">
                
            </xsl:for-each>
			<xsl:value-of select="Akt/ZavrsniDeo/Organ"/> <br></br>
			<xsl:value-of select="Akt/ZavrsniDeo/Broj"/> <br></br>
			<xsl:value-of select="Akt/ZavrsniDeo/Datum"/> <br></br>
			<xsl:if test="Akt/ZavrsniDeo/Potpisnik/Titula"><xsl:value-of select="Akt/ZavrsniDeo/Potpisnik/Titula"/> <br></br></xsl:if>  
			<xsl:value-of select="Akt/ZavrsniDeo/Potpisnik/Ime"/> <br></br>
			<xsl:value-of select="Akt/ZavrsniDeo/Potpisnik/Prezime"/> <br></br>
		</body>
		</html>
	</xsl:template>
</xsl:stylesheet>