<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"  xmlns:gov="http://www.gradskaskupstina.gov/"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" >
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="akt-page">
                    <fo:region-body margin="1in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

                <fo:page-sequence master-reference="akt-page">
                    <fo:flow flow-name="xsl-region-body">
                        <fo:block font-family="sans-serif" text-align="center" font-size="30px" font-weight="bold" padding="30px">
                            <xsl:value-of select="gov:Akt/gov:Naslov"/>
                        </fo:block>
                        <!--Deo-->
                        <fo:block font-family="sans-serif" text-align="center" font-size="25px" font-weight="bold" padding="30px">
                            <xsl:for-each select="gov:Akt/gov:Deo">
                                Deo
                                <xsl:choose>
                                    <xsl:when test="@RedniBroj=1">PRVI</xsl:when>
                                    <xsl:when test="@RedniBroj=2">DRUGI</xsl:when>
                                    <xsl:when test="@RedniBroj=3">TRECI</xsl:when>
                                    <xsl:when test="@RedniBroj=4">CETVRTI</xsl:when>
                                    <xsl:when test="@RedniBroj=5">PETI</xsl:when>
                                    <xsl:otherwise><xsl:value-of select="@RedniBroj"/></xsl:otherwise>
                                </xsl:choose>
                                <br></br>
                                <xsl:value-of select="@Naziv"/>
                                <br></br>
                                <!--Glave u for petlji-->
                                <fo:block font-family="sans-serif" text-align="center" font-size="20px" font-weight="bold" padding="30px">
                                    <!--Glava unutar for petlje za ispis glave-->
                                    <xsl:for-each select="gov:Akt/gov:Deo/gov:Glava">
                                        Glava
                                        <xsl:choose>
                                            <xsl:when test="@RedniBroj=1">I</xsl:when>
                                            <xsl:when test="@RedniBroj=2">II</xsl:when>
                                            <xsl:when test="@RedniBroj=3">III</xsl:when>
                                            <xsl:when test="@RedniBroj=4">IV</xsl:when>
                                            <xsl:when test="@RedniBroj=5">V</xsl:when>
                                            <xsl:otherwise><xsl:value-of select="@RedniBroj"/></xsl:otherwise>
                                        </xsl:choose>
                                        <br></br>
                                        <xsl:value-of select="@Naziv" /><br></br>

                                        <fo:block font-family="sans-serif" text-align="center" font-size="14px" font-weight="bold" padding="20px">
                                            <!--Clan unutar glave-->
                                            <xsl:for-each select="gov:Clan">
                                                <xsl:value-of select="@Naziv"/><br></br>
                                                Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                                    <xsl:for-each select="gov:Stav">
                                                        <fo:block font-family="sans-serif" text-align="left" font-size="12px"  padding="10px">
                                                            <xsl:value-of select="gov:Tekst"/><br></br>
                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                                <xsl:for-each select="gov:Tacka">
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                        <xsl:value-of select="@RedniBroj"/>. <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                            <xsl:for-each select="gov:Podtacka">
                                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                    (<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/><br></br>
                                                                                </fo:block>
                                                                            </xsl:for-each>
                                                                        </fo:block>
                                                                    </fo:block>
                                                                </xsl:for-each>
                                                            </fo:block>
                                                        </fo:block>
                                                    </xsl:for-each>
                                                </fo:block>
                                            </xsl:for-each>
                                            <fo:block font-family="sans-serif" text-align="center" font-size="14px" font-weight="bold" padding="20px">
                                                <!--Odeljak unutar glave-->
                                                <xsl:for-each select="gov:Odeljak">
                                                    <xsl:value-of select="@RedniBroj" />. <xsl:value-of select="@Naziv" /><br></br>
                                                    <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="10px">
                                                        <!--Pododeljak unutar odeljka-->
                                                        <xsl:for-each select="gov:Pododeljak">
                                                            <xsl:value-of select="@RednoSlovo" />) <xsl:value-of select="@Naziv" /><br></br>
                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px" font-weight="bold" padding="10px">
                                                                <!--Clan unutar pododeljka-->
                                                                <xsl:for-each select="gov:Clan">
                                                                    <xsl:value-of select="@Naziv"/><br></br>
                                                                    Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                                                        <xsl:for-each select="gov:Stav">
                                                                            <fo:block font-family="sans-serif" text-align="left" font-size="12px"  padding="10px">
                                                                                <xsl:value-of select="gov:Tekst"/><br></br>
                                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                                                    <xsl:for-each select="gov:Tacka">
                                                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                            <xsl:value-of select="@RedniBroj"/>. <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                                <xsl:for-each select="gov:Podtacka">
                                                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                                        (<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/><br></br>
                                                                                                    </fo:block>
                                                                                                </xsl:for-each>
                                                                                            </fo:block>
                                                                                        </fo:block>
                                                                                    </xsl:for-each>
                                                                                </fo:block>
                                                                            </fo:block>
                                                                        </xsl:for-each>
                                                                    </fo:block>
                                                                </xsl:for-each>
                                                            </fo:block>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                    <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="10px">
                                                        <!--Clan unutar odeljka-->
                                                        <xsl:for-each select="gov:Clan">
                                                            <xsl:value-of select="@Naziv"/><br></br>
                                                            Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                            <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                                                <xsl:for-each select="gov:Stav">
                                                                    <fo:block font-family="sans-serif" text-align="left" font-size="12px"  padding="10px">
                                                                        <xsl:value-of select="gov:Tekst"/><br></br>
                                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                                            <xsl:for-each select="gov:Tacka">
                                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                    <xsl:value-of select="@RedniBroj"/>. <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                        <xsl:for-each select="gov:Podtacka">
                                                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                                (<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/><br></br>
                                                                                            </fo:block>
                                                                                        </xsl:for-each>
                                                                                    </fo:block>
                                                                                </fo:block>
                                                                            </xsl:for-each>
                                                                        </fo:block>
                                                                    </fo:block>
                                                                </xsl:for-each>
                                                            </fo:block>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                </xsl:for-each>
                                            </fo:block>
                                        </fo:block>
                                    </xsl:for-each>
                                </fo:block>
                            </xsl:for-each>
                        </fo:block>
                        <!--Glave u for petlji-->
                        <fo:block font-family="sans-serif" text-align="center" font-size="20px" font-weight="bold" padding="30px">
                            <!--Glava unutar for petlje za ispis glave-->
                            <xsl:for-each select="gov:Akt/gov:Glava">
                                Glava
                                <xsl:choose>
                                    <xsl:when test="@RedniBroj=1">I</xsl:when>
                                    <xsl:when test="@RedniBroj=2">II</xsl:when>
                                    <xsl:when test="@RedniBroj=3">III</xsl:when>
                                    <xsl:when test="@RedniBroj=4">IV</xsl:when>
                                    <xsl:when test="@RedniBroj=5">V</xsl:when>
                                    <xsl:otherwise><xsl:value-of select="@RedniBroj"/></xsl:otherwise>
                                </xsl:choose>
                                <br></br>
                                <xsl:value-of select="@Naziv" /><br></br>

                                <fo:block font-family="sans-serif" text-align="center" font-size="14px" font-weight="bold" padding="20px">
                                <!--Clan unutar glave-->
                                <xsl:for-each select="gov:Clan">
                                    <xsl:value-of select="@Naziv"/><br></br>
                                    Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                    <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                        <xsl:for-each select="gov:Stav">
                                            <fo:block font-family="sans-serif" text-align="left" font-size="12px"  padding="10px">
                                                <xsl:value-of select="gov:Tekst"/><br></br>
                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                            <xsl:for-each select="gov:Tacka">
                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                    <xsl:value-of select="@RedniBroj"/>. <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                <xsl:for-each select="gov:Podtacka">
                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                        (<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/><br></br>
                                                    </fo:block>
                                                </xsl:for-each>
                                                </fo:block>
                                                </fo:block>
                                            </xsl:for-each>
                                            </fo:block>
                                            </fo:block>
                                        </xsl:for-each>
                                    </fo:block>
                                </xsl:for-each>
                                <fo:block font-family="sans-serif" text-align="center" font-size="14px" font-weight="bold" padding="20px">
                                <!--Odeljak unutar glave-->
                                <xsl:for-each select="gov:Odeljak">
                                    <xsl:value-of select="@RedniBroj" />. <xsl:value-of select="@Naziv" /><br></br>
                                    <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="10px">
                                    <!--Pododeljak unutar odeljka-->
                                    <xsl:for-each select="gov:Pododeljak">
                                        <xsl:value-of select="@RednoSlovo" />) <xsl:value-of select="@Naziv" /><br></br>
                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" font-weight="bold" padding="10px">
                                        <!--Clan unutar pododeljka-->
                                            <xsl:for-each select="gov:Clan">
                                                <xsl:value-of select="@Naziv"/><br></br>
                                                Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                                    <xsl:for-each select="gov:Stav">
                                                        <fo:block font-family="sans-serif" text-align="left" font-size="12px"  padding="10px">
                                                            <xsl:value-of select="gov:Tekst"/><br></br>
                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                                <xsl:for-each select="gov:Tacka">
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                        <xsl:value-of select="@RedniBroj"/>. <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                            <xsl:for-each select="gov:Podtacka">
                                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                    (<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/><br></br>
                                                                                </fo:block>
                                                                            </xsl:for-each>
                                                                        </fo:block>
                                                                    </fo:block>
                                                                </xsl:for-each>
                                                            </fo:block>
                                                        </fo:block>
                                                    </xsl:for-each>
                                                </fo:block>
                                            </xsl:for-each>
                                        </fo:block>
                                    </xsl:for-each>
                                    </fo:block>
                                    <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="10px">
                                    <!--Clan unutar odeljka-->
                                        <xsl:for-each select="gov:Clan">
                                            <xsl:value-of select="@Naziv"/><br></br>
                                            Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                            <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                                <xsl:for-each select="gov:Stav">
                                                    <fo:block font-family="sans-serif" text-align="left" font-size="12px"  padding="10px">
                                                        <xsl:value-of select="gov:Tekst"/><br></br>
                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                            <xsl:for-each select="gov:Tacka">
                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                    <xsl:value-of select="@RedniBroj"/>. <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                        <xsl:for-each select="gov:Podtacka">
                                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                (<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/><br></br>
                                                                            </fo:block>
                                                                        </xsl:for-each>
                                                                    </fo:block>
                                                                </fo:block>
                                                            </xsl:for-each>
                                                        </fo:block>
                                                    </fo:block>
                                                </xsl:for-each>
                                            </fo:block>
                                        </xsl:for-each>
                                    </fo:block>
                                </xsl:for-each>
                                </fo:block>
                                </fo:block>
                            </xsl:for-each>
                        </fo:block>

                        <!--Clanovi u for petlji-->
                        <fo:block font-family="sans-serif" text-align="center" font-size="20px" font-weight="bold" padding="30px">
                            <xsl:for-each select="gov:Akt/gov:Clan">
                                Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                <xsl:value-of select="@Naziv"/><br></br>
                                <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                    <xsl:for-each select="gov:Stav">
                                        <fo:block font-family="sans-serif" text-align="left" font-size="12px"  padding="10px">
                                            <xsl:value-of select="gov:Tekst"/><br></br>
                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                <xsl:for-each select="gov:Tacka">
                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                        <xsl:value-of select="@RedniBroj"/>. <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                            <xsl:for-each select="gov:Podtacka">
                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                    (<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/><br></br>
                                                                </fo:block>
                                                            </xsl:for-each>
                                                        </fo:block>
                                                    </fo:block>
                                                </xsl:for-each>
                                            </fo:block>
                                        </fo:block>
                                    </xsl:for-each>
                                </fo:block>
                            </xsl:for-each>
                        </fo:block>

                        <!--Zavrsni deo-->
                        <xsl:if test="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe"><xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe"/>
                            <fo:block font-family="sans-serif" text-align="center" font-size="20px" font-weight="bold" padding="30px">
                            Glava
                                <xsl:choose>
                                    <xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@RedniBroj=1">I</xsl:when>
                                    <xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@RedniBroj=2">II</xsl:when>
                                    <xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@RedniBroj=3">III</xsl:when>
                                    <xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@RedniBroj=4">IV</xsl:when>
                                    <xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@RedniBroj=5">V</xsl:when>
                                    <xsl:otherwise><xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@RedniBroj"/></xsl:otherwise>
                                </xsl:choose>
                                <br></br>
                            <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@Naziv" /><br></br>

                            <fo:block font-family="sans-serif" text-align="center" font-size="14px" font-weight="bold" padding="20px">
                                <!--Clan unutar glave-->
                                <xsl:for-each select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/gov:Clan">
                                    Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                    <xsl:value-of select="@Naziv"/><br></br>
                                    <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                        <xsl:for-each select="gov:Stav">
                                            <fo:block font-family="sans-serif" text-align="left" font-size="12px"  padding="10px">
                                                <xsl:value-of select="gov:Tekst"/><br></br>
                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                    <xsl:for-each select="gov:Tacka">
                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                            <xsl:value-of select="@RedniBroj"/>. <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                <xsl:for-each select="gov:Podtacka">
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                        (<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/><br></br>
                                                                    </fo:block>
                                                                </xsl:for-each>
                                                            </fo:block>
                                                        </fo:block>
                                                    </xsl:for-each>
                                                </fo:block>
                                            </fo:block>
                                        </xsl:for-each>
                                    </fo:block>
                                </xsl:for-each>
                                <fo:block font-family="sans-serif" text-align="center" font-size="14px" font-weight="bold" padding="20px">
                                    <!--Odeljak unutar glave-->
                                    <xsl:for-each select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/gov:Odeljak">
                                        <xsl:value-of select="@RedniBroj" />. <xsl:value-of select="@Naziv" /><br></br>
                                        <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="10px">
                                            <!--Pododeljak unutar odeljka-->
                                            <xsl:for-each select="gov:Pododeljak">
                                                <xsl:value-of select="@RednoSlovo" />) <xsl:value-of select="@Naziv" /><br></br>
                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" font-weight="bold" padding="10px">
                                                    <!--Clan unutar pododeljka-->
                                                    <xsl:for-each select="gov:Clan">
                                                        <xsl:value-of select="@Naziv"/><br></br>
                                                        Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                        <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                                            <xsl:for-each select="gov:Stav">
                                                                <fo:block font-family="sans-serif" text-align="left" font-size="12px"  padding="10px">
                                                                    <xsl:value-of select="gov:Tekst"/><br></br>
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                                        <xsl:for-each select="gov:Tacka">
                                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                <xsl:value-of select="@RedniBroj"/>. <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                    <xsl:for-each select="gov:Podtacka">
                                                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                            (<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/><br></br>
                                                                                        </fo:block>
                                                                                    </xsl:for-each>
                                                                                </fo:block>
                                                                            </fo:block>
                                                                        </xsl:for-each>
                                                                    </fo:block>
                                                                </fo:block>
                                                            </xsl:for-each>
                                                        </fo:block>
                                                    </xsl:for-each>
                                                </fo:block>
                                            </xsl:for-each>
                                        </fo:block>
                                        <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="10px">
                                            <!--Clan unutar odeljka-->
                                            <xsl:for-each select="gov:Clan">
                                                <xsl:value-of select="@Naziv"/><br></br>
                                                Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                                    <xsl:for-each select="gov:Stav">
                                                        <fo:block font-family="sans-serif" text-align="left" font-size="12px"  padding="10px">
                                                            <xsl:value-of select="gov:Tekst"/><br></br>
                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                                <xsl:for-each select="gov:Tacka">
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                        <xsl:value-of select="@RedniBroj"/>. <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                            <xsl:for-each select="gov:Podtacka">
                                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                    (<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/><br></br>
                                                                                </fo:block>
                                                                            </xsl:for-each>
                                                                        </fo:block>
                                                                    </fo:block>
                                                                </xsl:for-each>
                                                            </fo:block>
                                                        </fo:block>
                                                    </xsl:for-each>
                                                </fo:block>
                                            </xsl:for-each>
                                        </fo:block>
                                    </xsl:for-each>
                                </fo:block>
                            </fo:block>
                            </fo:block>
                        </xsl:if>

                        <fo:block font-family="sans-serif" font-size="11px" font-weight="bold" padding="3px">
                            Organ: <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Organ"/>
                        </fo:block>
                        <fo:block font-family="sans-serif" font-size="11px" font-weight="bold" padding="3px">
                            Broj: <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Broj"/>
                        </fo:block>
                        <fo:block font-family="sans-serif" font-size="11px" font-weight="bold" padding="3px">
                            Datum: <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Datum"/>
                        </fo:block>

                        <xsl:if test="gov:Akt/gov:ZavrsniDeo/gov:Potpisnik/gov:Titula"><xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Potpisnik/gov:Titula"/>
                            <fo:block font-family="sans-serif" font-size="14px" font-weight="bold" padding="3px">
                            Titula: <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Potpisnik/gov:Titula"/>
                            </fo:block>
                        </xsl:if>
                        <fo:block font-family="sans-serif" font-size="11px" font-weight="bold" padding="3px">
                            Ime: <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Potpisnik/gov:Ime"/>
                        </fo:block>

                        <fo:block font-family="sans-serif" font-size="11px" font-weight="bold" padding="3px">
                            Prezime: <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Potpisnik/gov:Prezime"/>
                        </fo:block>
                    </fo:flow>
                </fo:page-sequence>

        </fo:root>
    </xsl:template>
</xsl:stylesheet>