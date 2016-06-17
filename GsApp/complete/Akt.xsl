<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"  xmlns:gov="http://www.gradskaskupstina.gov/"
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
		<head>
			<style type="text/css">
				p.clan {
					text-align: center;
					font-weght: bold;
				}
				p.pododeljak {
					text-align: center;
				}
				p.stav {
					text-indent: 30px;
				}
				p.tacka {
					text-indent: 30px;
					margin-left: 40px;
				}
				p.podtacka {
					text-indent: 30px;
					margin-left: 80px;
				}
				h3.glava {
					text-align: center;
					font-weight: bold;
				}
				h3.organ {
					text-align: center;
					font-weight: bold;
				}
				h4.odeljak {
					text-align: center;
					font-weight: bold;
				}
			</style>
		</head>
		<body>
			
			<h1 style="text-align: center; font-weight: bold;"><xsl:value-of select="gov:Akt/gov:Naslov"/> </h1>

            <xsl:for-each select="gov:Akt/gov:Deo">
				<h2 style="text-align: center; font-weight: bold;"> DEO
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
				</h2><br></br>

				<xsl:for-each select="gov:Glava">
					<h3 class="glava">Glava
					<xsl:choose>
						<xsl:when test="@RedniBroj=1">I</xsl:when>
						<xsl:when test="@RedniBroj=2">II</xsl:when>
						<xsl:when test="@RedniBroj=3">III</xsl:when>
						<xsl:when test="@RedniBroj=4">IV</xsl:when>
						<xsl:when test="@RedniBroj=5">V</xsl:when>
						<xsl:otherwise><xsl:value-of select="@RedniBroj"/></xsl:otherwise>
					</xsl:choose>
					<br></br>
					<xsl:value-of select="@Naziv" /></h3><br></br>
					<xsl:for-each select="gov:Clan">
						<p class="clan"><xsl:value-of select="@Naziv"/>
						Clan <xsl:value-of select="@RedniBroj"/>.</p>
							<xsl:for-each select="gov:Stav">
								<p class="stav"><xsl:value-of select="gov:Tekst" /></p>
								<xsl:value-of select="gov:Tekst"/><br></br><br></br>
									<xsl:for-each select="gov:Tacka">
										<p class="tacka"><xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="gov:Sadrzaj"/></p>
											<xsl:for-each select="gov:Podtacka">
												<p class="podtacka">(<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/></p>
												
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>
					</xsl:for-each>

					<xsl:for-each select="gov:Odeljak">
						<h4 class="odeljak"><xsl:value-of select="@RedniBroj" />. <xsl:value-of select="@Naziv" /></h4>
							<xsl:for-each select="gov:Pododeljak">
								<p class="pododeljak"><xsl:value-of select="@RednoSlovo" />) <xsl:value-of select="@Naziv" /></p>
									<xsl:for-each select="gov:Clan">
										<p class="clan"><xsl:value-of select="@Naziv"/>
										Clan <xsl:value-of select="@RedniBroj"/>.</p>
												<xsl:for-each select="gov:Stav">
													<p class="stav"><xsl:value-of select="gov:Tekst" /></p>
															<xsl:for-each select="gov:Tacka">
																
																	<p class="tacka"><xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="gov:Sadrzaj"/></p>
																		<xsl:for-each select="gov:Podtacka">
																			<p class="podtacka">(<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/></p>
																			
																		</xsl:for-each>
															</xsl:for-each>
												</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>
							<xsl:for-each select="gov:Clan">
								<p class="clan"><xsl:value-of select="@Naziv"/>
								Clan <xsl:value-of select="@RedniBroj"/>.</p>
									<xsl:for-each select="gov:Stav">
										<p class="stav"><xsl:value-of select="gov:Tekst"/></p>
											<xsl:for-each select="gov:Tacka">
												
												<p class="tacka"><xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="gov:Sadrzaj"/></p>
													<xsl:for-each select="gov:Podtacka">
														<p class="podtacka">(<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/></p>
														
													</xsl:for-each>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>	
					</xsl:for-each>
				</xsl:for-each>
			</xsl:for-each>
			
			<xsl:for-each select="gov:Akt/gov:Glava">
				<h3 class="glava">Glava
					<xsl:choose>
						<xsl:when test="@RedniBroj=1">I</xsl:when>
						<xsl:when test="@RedniBroj=2">II</xsl:when>
						<xsl:when test="@RedniBroj=3">III</xsl:when>
						<xsl:when test="@RedniBroj=4">IV</xsl:when>
						<xsl:when test="@RedniBroj=5">V</xsl:when>
						<xsl:otherwise><xsl:value-of select="@RedniBroj"/></xsl:otherwise>
					</xsl:choose>
					<br></br>
					<xsl:value-of select="@Naziv" /></h3><br></br>
					<xsl:for-each select="gov:Clan">
						<p class="clan"><xsl:value-of select="@Naziv"/>
						Clan <xsl:value-of select="@RedniBroj"/>.</p>
							<xsl:for-each select="gov:Stav">
								<p class="stav"><xsl:value-of select="gov:Tekst" /></p>
									<xsl:for-each select="gov:Tacka">
										
										<p class="tacka"><xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="gov:Sadrzaj"/></p>
											<xsl:for-each select="gov:Podtacka">
												<p class="podtacka">(<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/></p>
												
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>
					</xsl:for-each>
					
					<xsl:for-each select="gov:Odeljak">
						<h4 class="odeljak"><xsl:value-of select="@RedniBroj" />. <xsl:value-of select="@Naziv" /></h4>
							<xsl:for-each select="gov:Pododeljak">
								<p class="pododeljak"><xsl:value-of select="@RednoSlovo" />) <xsl:value-of select="@Naziv" /></p>
									<xsl:for-each select="gov:Clan">
										<p class="clan"><xsl:value-of select="@Naziv"/>
										Clan <xsl:value-of select="@RedniBroj"/>.</p>
												<xsl:for-each select="gov:Stav">
													<p class="stav"><xsl:value-of select="gov:Tekst" /></p>
													<xsl:for-each select="gov:Tacka">
														
															<p class="tacka"><xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="gov:Sadrzaj"/></p>
																<xsl:for-each select="gov:Podtacka">
																	<p class="podtacka">(<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/></p>
																	
																</xsl:for-each>
													</xsl:for-each>
												</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>
							<xsl:for-each select="gov:Clan">
								<p class="clan"><xsl:value-of select="@Naziv"/>
								Clan <xsl:value-of select="@RedniBroj"/>.</p>
									<xsl:for-each select="gov:Stav">
										<p class="stav"><xsl:value-of select="gov:Tekst" /></p>
											<xsl:for-each select="gov:Tacka">
												
												<p class="tacka"><xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="gov:Sadrzaj"/></p>
													<xsl:for-each select="gov:Podtacka">
														<p class="podtacka">(<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/></p>
														
													</xsl:for-each>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>	
					</xsl:for-each>
			</xsl:for-each>
			
			<xsl:for-each select="gov:Akt/gov:Clan">
					<p class="clan"><xsl:value-of select="@Naziv"/>
					Clan <xsl:value-of select="@RedniBroj"/>.</p>
						<xsl:for-each select="gov:Stav">
							<p class="stav"><xsl:value-of select="gov:Tekst" /></p>
								<xsl:for-each select="gov:Tacka">
									
									<p class="tacka"><xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="gov:Sadrzaj"/></p>
										<xsl:for-each select="gov:Podtacka">
											<p class="podtacka">(<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/></p>
											
										</xsl:for-each>
								</xsl:for-each>
						</xsl:for-each>
			</xsl:for-each>
			
			
			<xsl:if test="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe"><xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe"/>
				<h3 class="glava">Glava
					<xsl:choose>
						<xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@RedniBroj=1">I</xsl:when>
						<xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@RedniBroj=2">II</xsl:when>
						<xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@RedniBroj=3">III</xsl:when>
						<xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@RedniBroj=4">IV</xsl:when>
						<xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@RedniBroj=5">V</xsl:when>
						<xsl:otherwise><xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@RedniBroj"/></xsl:otherwise>
					</xsl:choose>
					<br></br>
					<xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@Naziv" /></h3><br></br>
					<xsl:for-each select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/gov:Clan">
						<p class="clan"><xsl:value-of select="@Naziv"/>
						Clan <xsl:value-of select="@RedniBroj"/>.</p>
							<xsl:for-each select="gov:Stav">
								<p class="stav"><xsl:value-of select="gov:Tekst" /></p>
								<xsl:value-of select="gov:Tekst"/><br></br><br></br>
									<xsl:for-each select="gov:Tacka">
										
										<p class="tacka"><xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="gov:Sadrzaj"/></p>
											<xsl:for-each select="gov:Podtacka">
												<p class="podtacka">(<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/></p>
												
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>
					</xsl:for-each>
					
					<xsl:for-each select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/gov:Odeljak">
						<h4 class="odeljak"><xsl:value-of select="@RedniBroj" />. <xsl:value-of select="@Naziv" /></h4>
						<xsl:value-of select="@Naziv" /><br></br>
							<xsl:for-each select="gov:Pododeljak">
								<p class="pododeljak"><xsl:value-of select="@RednoSlovo" />) <xsl:value-of select="@Naziv" /></p>
											<xsl:for-each select="gov:Clan">
													<p class="clan"><xsl:value-of select="@Naziv"/>
													Clan <xsl:value-of select="@RedniBroj"/>.</p>
																	<xsl:for-each select="gov:Stav">
																			<p class="stav"><xsl:value-of select="gov:Tekst" /></p>
																			<xsl:value-of select="gov:Tekst"/><br></br><br></br>
																				<xsl:for-each select="gov:Tacka">
																					
																						<p class="tacka"><xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="gov:Sadrzaj"/></p>
																							<xsl:for-each select="gov:Podtacka">
																								<p class="podtacka">(<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/></p>
																								
																							</xsl:for-each>
																				</xsl:for-each>
																	</xsl:for-each>
											</xsl:for-each>
							</xsl:for-each>
							<xsl:for-each select="gov:Clan">
								<p class="clan"><xsl:value-of select="@Naziv"/>
								Clan <xsl:value-of select="@RedniBroj"/>.</p>
									<xsl:for-each select="gov:Stav">
										<p class="stav"><xsl:value-of select="gov:Tekst" /></p>
										<xsl:value-of select="gov:Tekst"/><br></br><br></br>
											<xsl:for-each select="gov:Tacka">
												
												<p class="tacka"><xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="gov:Sadrzaj"/></p>
													<xsl:for-each select="gov:Podtacka">
														<p class="podtacka">(<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/></p>
														
													</xsl:for-each>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>	
					</xsl:for-each>
			</xsl:if>

			<xsl:if test="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe"><xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe"/>
				<h3 class="glava">Glava
					<xsl:choose>
						<xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe/@RedniBroj=1">I</xsl:when>
						<xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe/@RedniBroj=2">II</xsl:when>
						<xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe/@RedniBroj=3">III</xsl:when>
						<xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe/@RedniBroj=4">IV</xsl:when>
						<xsl:when test="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe/@RedniBroj=5">V</xsl:when>
						<xsl:otherwise><xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe/@RedniBroj"/></xsl:otherwise>
					</xsl:choose>
					<br></br>
					<xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe/@RedniBroj" /></h3><br></br>
								<xsl:for-each select="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe/gov:Clan">
									<p class="clan"><xsl:value-of select="@Naziv"/>
									Clan <xsl:value-of select="@RedniBroj"/>.</p>
										<xsl:for-each select="gov:Stav">
											<p class="stav"><xsl:value-of select="gov:Tekst" /></p>
											<xsl:value-of select="gov:Tekst"/><br></br><br></br>
												<xsl:for-each select="gov:Tacka">
													
													<p class="tacka"><xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="gov:Sadrzaj"/></p>
														<xsl:for-each select="gov:Podtacka">
															<p class="podtacka">(<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/></p>
															
														</xsl:for-each>
												</xsl:for-each>
										</xsl:for-each>
								</xsl:for-each>
					
					<xsl:for-each select="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe/gov:Odeljak">
						<h4 class="odeljak"><xsl:value-of select="@RedniBroj" />. <xsl:value-of select="@Naziv" /></h4>
						<xsl:value-of select="@Naziv" /><br></br>
							<xsl:for-each select="gov:Pododeljak">
								<p class="pododeljak"><xsl:value-of select="@RednoSlovo" />) <xsl:value-of select="@Naziv" /></p>
											<xsl:for-each select="gov:Clan">
													<p class="clan"><xsl:value-of select="@Naziv"/>
													Clan <xsl:value-of select="@RedniBroj"/>.</p>
																	<xsl:for-each select="gov:Stav">
																			<p class="stav"><xsl:value-of select="gov:Tekst" /></p>
																			<xsl:value-of select="gov:Tekst"/><br></br><br></br>
																				<xsl:for-each select="gov:Tacka">
																					
																						<p class="tacka"><xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="gov:Sadrzaj"/></p>
																							<xsl:for-each select="gov:Podtacka">
																								<p class="podtacka">(<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/></p>
																								
																							</xsl:for-each>
																				</xsl:for-each>
																	</xsl:for-each>
											</xsl:for-each>
							</xsl:for-each>
							<xsl:for-each select="gov:Clan">
								<p class="clan"><xsl:value-of select="@Naziv"/>
								Clan <xsl:value-of select="@RedniBroj"/>.</p>
									<xsl:for-each select="gov:Stav">
										<p class="stav"><xsl:value-of select="gov:Tekst" /></p>
										<xsl:value-of select="gov:Tekst"/><br></br><br></br>
											<xsl:for-each select="gov:Tacka">
												<p class="tacka"><xsl:value-of select="@RedniBroj"/>)
												<xsl:value-of select="gov:Sadrzaj"/></p>
												<xsl:for-each select="gov:Podtacka">
													<p class="podtacka">(<xsl:value-of select="@RedniBroj"/>) <xsl:value-of select="text()"/></p>
													
												</xsl:for-each>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>	
					</xsl:for-each>
			</xsl:if>
			<!--Od Organa na nadalje-->
			<xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Broj"/><br></br>
			<xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Datum"/>
			<h3 class="organ"><xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Organ"/></h3>
			<xsl:if test="gov:Akt/gov:ZavrsniDeo/gov:Potpisnik/gov:Titula"> <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Potpisnik/gov:Titula"/></xsl:if>
			<xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Potpisnik/gov:Ime"/>&#160;<xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Potpisnik/gov:Prezime"/>
		</body>
		</html>
	</xsl:template>
</xsl:stylesheet>