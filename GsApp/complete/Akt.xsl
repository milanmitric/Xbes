<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"  xmlns:gov="http://www.gradskaskupstina.gov/"
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
		<head>
		</head>
		<body>
			
			<h1><xsl:value-of select="gov:Akt/gov:Naslov"/> </h1>
			
			

            <xsl:for-each select="gov:Akt/gov:Deo">
				<h2> DEO
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
					<h3>Glava
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
						<xsl:value-of select="@Naziv"/><br></br>
						Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
							<xsl:for-each select="gov:Stav">
								<xsl:<xsl:value-of select="gov:Tekst"/><br></br><br></br>
									<xsl:for-each select="gov:Tacka">
										Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
										<xsl:value-of select="gov:Sadrzaj"/><br></br>
											<xsl:for-each select="gov:Podtacka">
												Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="text()"/><br></br>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>
					</xsl:for-each>
					
					<xsl:for-each select="gov:Odeljak">
						<xsl:value-of select="@RedniBroj" />. <xsl:value-of select="@Naziv" /><br></br>
							<xsl:for-each select="gov:Pododeljak">
								<xsl:value-of select="@RednoSlovo" />) <xsl:value-of select="@Naziv" /><br></br>
									<xsl:for-each select="gov:Clan">
										Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="@Naziv"/><br></br>
												<xsl:for-each select="gov:Stav">
														<xsl:value-of select="gov:Tekst"/><br></br><br></br>
															<xsl:for-each select="gov:Tacka">
																Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																	<xsl:value-of select="gov:Sadrzaj"/><br></br>
																		<xsl:for-each select="gov:Podtacka">
																			Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																			<xsl:value-of select="text()"/><br></br>
																		</xsl:for-each>
															</xsl:for-each>
												</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>
							<xsl:for-each select="gov:Clan">
								<xsl:value-of select="@Naziv"/><br></br>
								Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
									<xsl:for-each select="gov:Stav">
										<xsl:value-of select="gov:Tekst"/><br></br><br></br>
											<xsl:for-each select="gov:Tacka">
												Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="gov:Sadrzaj"/><br></br>
													<xsl:for-each select="gov:Podtacka">
														Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
														<xsl:value-of select="text()"/><br></br>
													</xsl:for-each>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>	
					</xsl:for-each>
				</xsl:for-each>
			</xsl:for-each>
			
			<xsl:for-each select="gov:Akt/gov:Glava">
				<h3>Glava
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
						Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
						<xsl:value-of select="@Naziv"/><br></br>
							<xsl:for-each select="gov:Stav">
								<xsl:value-of select="gov:Tekst"/><br></br><br></br>
									<xsl:for-each select="gov:Tacka">
										Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
										<xsl:value-of select="gov:Sadrzaj"/><br></br>
											<xsl:for-each select="gov:Podtacka">
												Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="text()"/><br></br>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>
					</xsl:for-each>
					
					<xsl:for-each select="gov:Odeljak">
						<xsl:value-of select="@RedniBroj" />. <xsl:value-of select="@Naziv" /><br></br>
							<xsl:for-each select="gov:Pododeljak">
								<xsl:value-of select="@RednoSlovo" />) <xsl:value-of select="@Naziv" /><br></br>
									<xsl:for-each select="gov:Clan">
										<xsl:value-of select="@Naziv"/><br></br>
											Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:for-each select="gov:Stav">
													<xsl:value-of select="gov:Tekst"/><br></br><br></br>
													<xsl:for-each select="gov:Tacka">
														Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
															<xsl:value-of select="gov:Sadrzaj"/><br></br>
																<xsl:for-each select="gov:Podtacka">
																	Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																	<xsl:value-of select="text()"/><br></br>
																</xsl:for-each>
													</xsl:for-each>
												</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>
							<xsl:for-each select="gov:Clan">
								Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
								<xsl:value-of select="@Naziv"/><br></br>
									<xsl:for-each select="gov:Stav">
										<xsl:value-of select="gov:Tekst"/><br></br><br></br>
											<xsl:for-each select="gov:Tacka">
												Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="gov:Sadrzaj"/><br></br>
													<xsl:for-each select="gov:Podtacka">
														Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
														<xsl:value-of select="text()"/><br></br>
													</xsl:for-each>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>	
					</xsl:for-each>
			</xsl:for-each>
			
			<xsl:for-each select="gov:Akt/gov:Clan">
					<xsl:value-of select="@Naziv"/><br></br>
					Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
						<xsl:for-each select="gov:Stav">
							<xsl:value-of select="gov:Tekst"/><br></br><br></br>
								<xsl:for-each select="gov:Tacka">
									Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
									<xsl:value-of select="gov:Sadrzaj"/><br></br>
										<xsl:for-each select="gov:Podtacka">
											Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
											<xsl:value-of select="text()"/><br></br>
										</xsl:for-each>
								</xsl:for-each>
						</xsl:for-each>
			</xsl:for-each>
			
			
			<xsl:if test="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe"><xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe"/>
					<h2> Prelazne Odredbe </h2>
					Glava <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@RedniBroj" />.<br></br>
					<xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/@Naziv" /><br></br>
					<xsl:for-each select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/gov:Clan">
						Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
						<xsl:value-of select="@Naziv"/><br></br>
							<xsl:for-each select="gov:Stav">
								Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
								<xsl:value-of select="gov:Tekst"/><br></br><br></br>
									<xsl:for-each select="gov:Tacka">
										Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
										<xsl:value-of select="gov:Sadrzaj"/><br></br>
											<xsl:for-each select="gov:Podtacka">
												Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="text()"/><br></br>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>
					</xsl:for-each>
					
					<xsl:for-each select="gov:Akt/gov:ZavrsniDeo/gov:PrelazneOdredbe/gov:Odeljak">
						Odeljak <xsl:value-of select="@RedniBroj" />.<br></br>
						<xsl:value-of select="@Naziv" /><br></br>
							<xsl:for-each select="gov:Pododeljak">
								RednoSlovo <xsl:value-of select="@RednoSlovo" />.<br></br>
											<xsl:for-each select="gov:Clan">
													Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
															<xsl:value-of select="@Naziv"/><br></br>
																	<xsl:for-each select="gov:Stav">
																			Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
																			<xsl:value-of select="gov:Tekst"/><br></br><br></br>
																				<xsl:for-each select="gov:Tacka">
																					Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																						<xsl:value-of select="gov:Sadrzaj"/><br></br>
																							<xsl:for-each select="gov:Podtacka">
																								Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																								<xsl:value-of select="text()"/><br></br>
																							</xsl:for-each>
																				</xsl:for-each>
																	</xsl:for-each>
											</xsl:for-each>
							</xsl:for-each>
							<xsl:for-each select="gov:Clan">
								Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
								<xsl:value-of select="@Naziv"/><br></br>
									<xsl:for-each select="gov:Stav">
										Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
										<xsl:value-of select="gov:Tekst"/><br></br><br></br>
											<xsl:for-each select="gov:Tacka">
												Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="gov:Sadrzaj"/><br></br>
													<xsl:for-each select="gov:Podtacka">
														Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
														<xsl:value-of select="text()"/><br></br>
													</xsl:for-each>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>	
					</xsl:for-each>
			</xsl:if>

			<xsl:if test="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe"><xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe"/>
					<h2> Zavrsne Odredbe </h2>
						Glava <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe/@RedniBroj" />.<br></br>
								<xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe/@Naziv" /><br></br>
								<xsl:for-each select="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe/gov:Clan">
									Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
									<xsl:value-of select="@Naziv"/><br></br>
										<xsl:for-each select="gov:Stav">
											Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
											<xsl:value-of select="gov:Tekst"/><br></br><br></br>
												<xsl:for-each select="gov:Tacka">
													Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
													<xsl:value-of select="gov:Sadrzaj"/><br></br>
														<xsl:for-each select="gov:Podtacka">
															Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
															<xsl:value-of select="text()"/><br></br>
														</xsl:for-each>
												</xsl:for-each>
										</xsl:for-each>
								</xsl:for-each>
					
					<xsl:for-each select="gov:Akt/gov:ZavrsniDeo/gov:ZavrsneOdredbe/gov:Odeljak">
						Odeljak <xsl:value-of select="@RedniBroj" />.<br></br>
						<xsl:value-of select="@Naziv" /><br></br>
							<xsl:for-each select="gov:Pododeljak">
								RednoSlovo <xsl:value-of select="@RednoSlovo" />.<br></br>
											<xsl:for-each select="gov:Clan">
													Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
															<xsl:value-of select="@Naziv"/><br></br>
																	<xsl:for-each select="gov:Stav">
																			Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
																			<xsl:value-of select="gov:Tekst"/><br></br><br></br>
																				<xsl:for-each select="gov:Tacka">
																					Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																						<xsl:value-of select="gov:Sadrzaj"/><br></br>
																							<xsl:for-each select="gov:Podtacka">
																								Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																								<xsl:value-of select="text()"/><br></br>
																							</xsl:for-each>
																				</xsl:for-each>
																	</xsl:for-each>
											</xsl:for-each>
							</xsl:for-each>
							<xsl:for-each select="gov:Clan">
								Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
								<xsl:value-of select="@Naziv"/><br></br>
									<xsl:for-each select="gov:Stav">
										Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
										<xsl:value-of select="gov:Tekst"/><br></br><br></br>
											<xsl:for-each select="gov:Tacka">
												Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="gov:Sadrzaj"/><br></br>
													<xsl:for-each select="gov:Podtacka">
														Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
														<xsl:value-of select="text()"/><br></br>
													</xsl:for-each>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>	
					</xsl:for-each>
			</xsl:if>
			<!--Od Organa na nadalje-->
			Organ : <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Organ"/> <br></br>
			Broj : <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Broj"/> <br></br>
			Datum : <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Datum"/> <br></br>
			<xsl:if test="gov:Akt/gov:ZavrsniDeo/gov:Potpisnik/gov:Titula">Titula : <xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Potpisnik/gov:Titula"/> <br></br></xsl:if>
			Ime :<xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Potpisnik/gov:Ime"/> <br></br>
			Prezime :<xsl:value-of select="gov:Akt/gov:ZavrsniDeo/gov:Potpisnik/gov:Prezime"/> <br></br>
		</body>
		</html>
	</xsl:template>
</xsl:stylesheet>