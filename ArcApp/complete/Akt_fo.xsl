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
                        <fo:block font-family="sans-serif" text-align="center" font-size="20px" font-weight="bold" padding="30px">
                            <xsl:value-of select="gov:Akt/gov:Naslov"/>
                        </fo:block>
                        <!--Deo-->
                        <fo:block font-family="sans-serif" text-align="center" font-size="20px" font-weight="bold" padding="30px">
                            <xsl:for-each select="gov:Akt/gov:Deo">
                                Deo <xsl:value-of select="@Naziv"/> <xsl:value-of select="@RedniBroj" /><br></br>
                                <!--Glave u for petlji-->
                                <fo:block font-family="sans-serif" text-align="center" font-size="20px" font-weight="bold" padding="30px">
                                    <!--Glava unutar for petlje za ispis glave-->
                                    <xsl:for-each select="gov:Akt/gov:Deo/gov:Glava">
                                        Glava <xsl:value-of select="@RedniBroj" />.<br></br>
                                        <xsl:value-of select="@Naziv" /><br></br>

                                        <fo:block font-family="sans-serif" text-align="center" font-size="14px" font-weight="bold" padding="20px">
                                            <!--Clan unutar glave-->
                                            <xsl:for-each select="gov:Clan">
                                                Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                <xsl:value-of select="@Naziv"/><br></br>
                                                <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                                    <xsl:for-each select="gov:Stav">
                                                        Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                        <fo:block font-family="sans-serif" text-align="center" font-size="12px"  padding="10px">
                                                            <xsl:value-of select="gov:Tekst"/><br></br><br></br>
                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                                <xsl:for-each select="gov:Tacka">
                                                                    Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                        <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                            <xsl:for-each select="gov:Podtacka">
                                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                    Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="14px" padding="10px">
                                                                                        <xsl:value-of select="text()"/><br></br>
                                                                                    </fo:block>
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
                                                    Odeljak <xsl:value-of select="@RedniBroj" />.<br></br>
                                                    <xsl:value-of select="@Naziv" /><br></br>
                                                    <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="10px">
                                                        <!--Pododeljak unutar odeljka-->
                                                        <xsl:for-each select="gov:Pododeljak">
                                                            RednoSlovo <xsl:value-of select="@RednoSlovo" />.<br></br>
                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px" font-weight="bold" padding="10px">
                                                                <!--Clan unutar pododeljka-->
                                                                <xsl:for-each select="gov:Clan">
                                                                    Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                    <xsl:value-of select="@Naziv"/><br></br>
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                                                        <xsl:for-each select="gov:Stav">
                                                                            Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                            <fo:block font-family="sans-serif" text-align="center" font-size="12px"  padding="10px">
                                                                                <xsl:value-of select="gov:Tekst"/><br></br><br></br>
                                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                                                    <xsl:for-each select="gov:Tacka">
                                                                                        Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                            <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                                <xsl:for-each select="gov:Podtacka">
                                                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                                        Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                                                        <fo:block font-family="sans-serif" text-align="center" font-size="14px" padding="10px">
                                                                                                            <xsl:value-of select="text()"/><br></br>
                                                                                                        </fo:block>
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
                                                            Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                            <xsl:value-of select="@Naziv"/><br></br>
                                                            <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                                                <xsl:for-each select="gov:Stav">
                                                                    Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="12px"  padding="10px">
                                                                        <xsl:value-of select="gov:Tekst"/><br></br><br></br>
                                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                                            <xsl:for-each select="gov:Tacka">
                                                                                Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                    <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                        <xsl:for-each select="gov:Podtacka">
                                                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                                Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                                                <fo:block font-family="sans-serif" text-align="center" font-size="14px" padding="10px">
                                                                                                    <xsl:value-of select="text()"/><br></br>
                                                                                                </fo:block>
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
                                Glava <xsl:value-of select="@RedniBroj" />.<br></br>
                                <xsl:value-of select="@Naziv" /><br></br>

                                <fo:block font-family="sans-serif" text-align="center" font-size="14px" font-weight="bold" padding="20px">
                                <!--Clan unutar glave-->
                                <xsl:for-each select="gov:Clan">
                                    Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                    <xsl:value-of select="@Naziv"/><br></br>
                                    <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                        <xsl:for-each select="gov:Stav">
                                            Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
                                            <fo:block font-family="sans-serif" text-align="center" font-size="12px"  padding="10px">
                                            <xsl:value-of select="gov:Tekst"/><br></br><br></br>
                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                            <xsl:for-each select="gov:Tacka">
                                                Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                <xsl:for-each select="gov:Podtacka">
                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                    Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                    <fo:block font-family="sans-serif" text-align="center" font-size="14px" padding="10px">
                                                    <xsl:value-of select="text()"/><br></br>
                                                    </fo:block>
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
                                    Odeljak <xsl:value-of select="@RedniBroj" />.<br></br>
                                    <xsl:value-of select="@Naziv" /><br></br>
                                    <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="10px">
                                    <!--Pododeljak unutar odeljka-->
                                    <xsl:for-each select="gov:Pododeljak">
                                        RednoSlovo <xsl:value-of select="@RednoSlovo" />.<br></br>
                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" font-weight="bold" padding="10px">
                                        <!--Clan unutar pododeljka-->
                                            <xsl:for-each select="gov:Clan">
                                                Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                <xsl:value-of select="@Naziv"/><br></br>
                                                <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                                    <xsl:for-each select="gov:Stav">
                                                        Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                        <fo:block font-family="sans-serif" text-align="center" font-size="12px"  padding="10px">
                                                            <xsl:value-of select="gov:Tekst"/><br></br><br></br>
                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                                <xsl:for-each select="gov:Tacka">
                                                                    Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                        <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                            <xsl:for-each select="gov:Podtacka">
                                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                    Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="14px" padding="10px">
                                                                                        <xsl:value-of select="text()"/><br></br>
                                                                                    </fo:block>
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
                                            Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                            <xsl:value-of select="@Naziv"/><br></br>
                                            <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                                <xsl:for-each select="gov:Stav">
                                                    Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                    <fo:block font-family="sans-serif" text-align="center" font-size="12px"  padding="10px">
                                                        <xsl:value-of select="gov:Tekst"/><br></br><br></br>
                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                            <xsl:for-each select="gov:Tacka">
                                                                Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                    <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                        <xsl:for-each select="gov:Podtacka">
                                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                                <fo:block font-family="sans-serif" text-align="center" font-size="14px" padding="10px">
                                                                                    <xsl:value-of select="text()"/><br></br>
                                                                                </fo:block>
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
                                        Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
                                        <fo:block font-family="sans-serif" text-align="center" font-size="12px"  padding="10px">
                                            <xsl:value-of select="gov:Tekst"/><br></br><br></br>
                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                <xsl:for-each select="gov:Tacka">
                                                    Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                        <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                            <xsl:for-each select="gov:Podtacka">
                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                    Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="14px" padding="10px">
                                                                        <xsl:value-of select="text()"/><br></br>
                                                                    </fo:block>
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
                            Glava <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@RedniBroj" />.<br></br>
                            <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@Naziv" /><br></br>

                            <fo:block font-family="sans-serif" text-align="center" font-size="14px" font-weight="bold" padding="20px">
                                <!--Clan unutar glave-->
                                <xsl:for-each select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/gov:Clan">
                                    Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                    <xsl:value-of select="@Naziv"/><br></br>
                                    <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                        <xsl:for-each select="gov:Stav">
                                            Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
                                            <fo:block font-family="sans-serif" text-align="center" font-size="12px"  padding="10px">
                                                <xsl:value-of select="gov:Tekst"/><br></br><br></br>
                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                    <xsl:for-each select="gov:Tacka">
                                                        Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                            <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                <xsl:for-each select="gov:Podtacka">
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                        Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                        <fo:block font-family="sans-serif" text-align="center" font-size="14px" padding="10px">
                                                                            <xsl:value-of select="text()"/><br></br>
                                                                        </fo:block>
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
                                        Odeljak <xsl:value-of select="@RedniBroj" />.<br></br>
                                        <xsl:value-of select="@Naziv" /><br></br>
                                        <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="10px">
                                            <!--Pododeljak unutar odeljka-->
                                            <xsl:for-each select="gov:Pododeljak">
                                                RednoSlovo <xsl:value-of select="@RednoSlovo" />.<br></br>
                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" font-weight="bold" padding="10px">
                                                    <!--Clan unutar pododeljka-->
                                                    <xsl:for-each select="gov:Clan">
                                                        Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                        <xsl:value-of select="@Naziv"/><br></br>
                                                        <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                                            <xsl:for-each select="gov:Stav">
                                                                Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                <fo:block font-family="sans-serif" text-align="center" font-size="12px"  padding="10px">
                                                                    <xsl:value-of select="gov:Tekst"/><br></br><br></br>
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                                        <xsl:for-each select="gov:Tacka">
                                                                            Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                    <xsl:for-each select="gov:Podtacka">
                                                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                            Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                                            <fo:block font-family="sans-serif" text-align="center" font-size="14px" padding="10px">
                                                                                                <xsl:value-of select="text()"/><br></br>
                                                                                            </fo:block>
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
                                                Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                <xsl:value-of select="@Naziv"/><br></br>
                                                <fo:block font-family="sans-serif" text-align="center" font-size="12px" font-weight="bold" padding="20px">
                                                    <xsl:for-each select="gov:Stav">
                                                        Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                        <fo:block font-family="sans-serif" text-align="center" font-size="12px"  padding="10px">
                                                            <xsl:value-of select="gov:Tekst"/><br></br><br></br>
                                                            <fo:block font-family="sans-serif" text-align="center" font-size="11px"  padding="10px">
                                                                <xsl:for-each select="gov:Tacka">
                                                                    Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                        <xsl:value-of select="gov:Sadrzaj"/><br></br>
                                                                        <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                            <xsl:for-each select="gov:Podtacka">
                                                                                <fo:block font-family="sans-serif" text-align="center" font-size="11px" padding="10px">
                                                                                    Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
                                                                                    <fo:block font-family="sans-serif" text-align="center" font-size="14px" padding="10px">
                                                                                        <xsl:value-of select="text()"/><br></br>
                                                                                    </fo:block>
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