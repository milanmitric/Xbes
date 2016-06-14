<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 >
	<xsl:template match="/">
		<html>
		<head>
		</head>
		<body>
			
			<h1><xsl:value-of select="Akt/Naslov"/> </h1>
			
			

            <xsl:for-each select="Akt/Deo">
				<h2> Deo <xsl:value-of select="@Naziv"/> <xsl:value-of select="@RedniBroj" /></h2><br></br>

				<xsl:for-each select="Glava">
					Glava <xsl:value-of select="@RedniBroj" />.<br></br>
					<xsl:value-of select="@Naziv" /><br></br>
					<xsl:for-each select="Clan">
						Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
						<xsl:value-of select="@Naziv"/><br></br>
							<xsl:for-each select="Stav">
								Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
								<xsl:value-of select="Tekst"/><br></br><br></br>
									<xsl:for-each select="Tacka">
										Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
										<xsl:value-of select="Sadrzaj"/><br></br>
											<xsl:for-each select="Podtacka">
												Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="text()"/><br></br>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>
					</xsl:for-each>
					
					<xsl:for-each select="Odeljak">
						Odeljak <xsl:value-of select="@RedniBroj" />.<br></br>
						<xsl:value-of select="@Naziv" /><br></br>
							<xsl:for-each select="Pododeljak">
								RednoSlovo <xsl:value-of select="@RednoSlovo" />.<br></br>
											<xsl:for-each select="Clan">
													Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
															<xsl:value-of select="@Naziv"/><br></br>
																	<xsl:for-each select="Stav">
																			Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
																			<xsl:value-of select="Tekst"/><br></br><br></br>
																				<xsl:for-each select="Tacka">
																					Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																						<xsl:value-of select="Sadrzaj"/><br></br>
																							<xsl:for-each select="Podtacka">
																								Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																								<xsl:value-of select="text()"/><br></br>
																							</xsl:for-each>
																				</xsl:for-each>
																	</xsl:for-each>
											</xsl:for-each>
							</xsl:for-each>
							<xsl:for-each select="Clan">
								Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
								<xsl:value-of select="@Naziv"/><br></br>
									<xsl:for-each select="Stav">
										Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
										<xsl:value-of select="Tekst"/><br></br><br></br>
											<xsl:for-each select="Tacka">
												Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="Sadrzaj"/><br></br>
													<xsl:for-each select="Podtacka">
														Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
														<xsl:value-of select="text()"/><br></br>
													</xsl:for-each>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>	
					</xsl:for-each>
				</xsl:for-each>
			</xsl:for-each>
			
			<xsl:for-each select="Akt/Glava">
					Glava <xsl:value-of select="@RedniBroj" />.<br></br>
					<xsl:value-of select="@Naziv" /><br></br>
					<xsl:for-each select="Clan">
						Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
						<xsl:value-of select="@Naziv"/><br></br>
							<xsl:for-each select="Stav">
								Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
								<xsl:value-of select="Tekst"/><br></br><br></br>
									<xsl:for-each select="Tacka">
										Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
										<xsl:value-of select="Sadrzaj"/><br></br>
											<xsl:for-each select="Podtacka">
												Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="text()"/><br></br>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>
					</xsl:for-each>
					
					<xsl:for-each select="Odeljak">
						Odeljak <xsl:value-of select="@RedniBroj" />.<br></br>
						<xsl:value-of select="@Naziv" /><br></br>
							<xsl:for-each select="Pododeljak">
								RednoSlovo <xsl:value-of select="@RednoSlovo" />.<br></br>
											<xsl:for-each select="Clan">
													Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
															<xsl:value-of select="@Naziv"/><br></br>
																	<xsl:for-each select="Stav">
																			Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
																			<xsl:value-of select="Tekst"/><br></br><br></br>
																				<xsl:for-each select="Tacka">
																					Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																						<xsl:value-of select="Sadrzaj"/><br></br>
																							<xsl:for-each select="Podtacka">
																								Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																								<xsl:value-of select="text()"/><br></br>
																							</xsl:for-each>
																				</xsl:for-each>
																	</xsl:for-each>
											</xsl:for-each>
							</xsl:for-each>
							<xsl:for-each select="Clan">
								Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
								<xsl:value-of select="@Naziv"/><br></br>
									<xsl:for-each select="Stav">
										Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
										<xsl:value-of select="Tekst"/><br></br><br></br>
											<xsl:for-each select="Tacka">
												Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="Sadrzaj"/><br></br>
													<xsl:for-each select="Podtacka">
														Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
														<xsl:value-of select="text()"/><br></br>
													</xsl:for-each>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>	
					</xsl:for-each>
			</xsl:for-each>
			
			<xsl:for-each select="Akt/Clan">
						Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
						<xsl:value-of select="@Naziv"/><br></br>
							<xsl:for-each select="Stav">
								Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
								<xsl:value-of select="Tekst"/><br></br><br></br>
									<xsl:for-each select="Tacka">
										Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
										<xsl:value-of select="Sadrzaj"/><br></br>
											<xsl:for-each select="Podtacka">
												Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="text()"/><br></br>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>
			</xsl:for-each>
			
			
			<xsl:if test="Akt/ZavrsniDeo/PrelazneOdredbe"><xsl:value-of select="Akt/ZavrsniDeo/PrelazneOdredbe"/>
					<h2> Prelazne Odredbe </h2>
					Glava <xsl:value-of select="Akt/ZavrsniDeo/PrelazneOdredbe/@RedniBroj" />.<br></br>
					<xsl:value-of select="Akt/ZavrsniDeo/PrelazneOdredbe/@Naziv" /><br></br>
					<xsl:for-each select="Akt/ZavrsniDeo/PrelazneOdredbe/Clan">
						Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
						<xsl:value-of select="@Naziv"/><br></br>
							<xsl:for-each select="Stav">
								Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
								<xsl:value-of select="Tekst"/><br></br><br></br>
									<xsl:for-each select="Tacka">
										Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
										<xsl:value-of select="Sadrzaj"/><br></br>
											<xsl:for-each select="Podtacka">
												Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="text()"/><br></br>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>
					</xsl:for-each>
					
					<xsl:for-each select="Akt/ZavrsniDeo/PrelazneOdredbe/Odeljak">
						Odeljak <xsl:value-of select="@RedniBroj" />.<br></br>
						<xsl:value-of select="@Naziv" /><br></br>
							<xsl:for-each select="Pododeljak">
								RednoSlovo <xsl:value-of select="@RednoSlovo" />.<br></br>
											<xsl:for-each select="Clan">
													Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
															<xsl:value-of select="@Naziv"/><br></br>
																	<xsl:for-each select="Stav">
																			Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
																			<xsl:value-of select="Tekst"/><br></br><br></br>
																				<xsl:for-each select="Tacka">
																					Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																						<xsl:value-of select="Sadrzaj"/><br></br>
																							<xsl:for-each select="Podtacka">
																								Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																								<xsl:value-of select="text()"/><br></br>
																							</xsl:for-each>
																				</xsl:for-each>
																	</xsl:for-each>
											</xsl:for-each>
							</xsl:for-each>
							<xsl:for-each select="Clan">
								Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
								<xsl:value-of select="@Naziv"/><br></br>
									<xsl:for-each select="Stav">
										Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
										<xsl:value-of select="Tekst"/><br></br><br></br>
											<xsl:for-each select="Tacka">
												Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="Sadrzaj"/><br></br>
													<xsl:for-each select="Podtacka">
														Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
														<xsl:value-of select="text()"/><br></br>
													</xsl:for-each>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>	
					</xsl:for-each>
			</xsl:if>

			<xsl:if test="Akt/ZavrsniDeo/ZavrsneOdredbe"><xsl:value-of select="Akt/ZavrsniDeo/ZavrsneOdredbe"/>
					<h2> Zavrsne Odredbe </h2>
						Glava <xsl:value-of select="Akt/ZavrsniDeo/ZavrsneOdredbe/@RedniBroj" />.<br></br>
								<xsl:value-of select="Akt/ZavrsniDeo/ZavrsneOdredbe/@Naziv" /><br></br>
								<xsl:for-each select="Akt/ZavrsniDeo/ZavrsneOdredbe/Clan">
									Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
									<xsl:value-of select="@Naziv"/><br></br>
										<xsl:for-each select="Stav">
											Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
											<xsl:value-of select="Tekst"/><br></br><br></br>
												<xsl:for-each select="Tacka">
													Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
													<xsl:value-of select="Sadrzaj"/><br></br>
														<xsl:for-each select="Podtacka">
															Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
															<xsl:value-of select="text()"/><br></br>
														</xsl:for-each>
												</xsl:for-each>
										</xsl:for-each>
								</xsl:for-each>
					
					<xsl:for-each select="Akt/ZavrsniDeo/ZavrsneOdredbe/Odeljak">
						Odeljak <xsl:value-of select="@RedniBroj" />.<br></br>
						<xsl:value-of select="@Naziv" /><br></br>
							<xsl:for-each select="Pododeljak">
								RednoSlovo <xsl:value-of select="@RednoSlovo" />.<br></br>
											<xsl:for-each select="Clan">
													Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
															<xsl:value-of select="@Naziv"/><br></br>
																	<xsl:for-each select="Stav">
																			Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
																			<xsl:value-of select="Tekst"/><br></br><br></br>
																				<xsl:for-each select="Tacka">
																					Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																						<xsl:value-of select="Sadrzaj"/><br></br>
																							<xsl:for-each select="Podtacka">
																								Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
																								<xsl:value-of select="text()"/><br></br>
																							</xsl:for-each>
																				</xsl:for-each>
																	</xsl:for-each>
											</xsl:for-each>
							</xsl:for-each>
							<xsl:for-each select="Clan">
								Clan <xsl:value-of select="@RedniBroj"/>.<br></br>
								<xsl:value-of select="@Naziv"/><br></br>
									<xsl:for-each select="Stav">
										Stav <xsl:value-of select="@RedniBroj"/>.<br></br>
										<xsl:value-of select="Tekst"/><br></br><br></br>
											<xsl:for-each select="Tacka">
												Tacka <xsl:value-of select="@RedniBroj"/>.<br></br>
												<xsl:value-of select="Sadrzaj"/><br></br>
													<xsl:for-each select="Podtacka">
														Podtacka <xsl:value-of select="@RedniBroj"/>.<br></br>
														<xsl:value-of select="text()"/><br></br>
													</xsl:for-each>
											</xsl:for-each>
									</xsl:for-each>
							</xsl:for-each>	
					</xsl:for-each>
			</xsl:if>
			<!--Od Organa na nadalje-->
			Organ : <xsl:value-of select="Akt/ZavrsniDeo/Organ"/> <br></br>
			Broj : <xsl:value-of select="Akt/ZavrsniDeo/Broj"/> <br></br>
			Datum : <xsl:value-of select="Akt/ZavrsniDeo/Datum"/> <br></br>
			<xsl:if test="Akt/ZavrsniDeo/Potpisnik/Titula">Titula : <xsl:value-of select="Akt/ZavrsniDeo/Potpisnik/Titula"/> <br></br></xsl:if>  
			Ime :<xsl:value-of select="Akt/ZavrsniDeo/Potpisnik/Ime"/> <br></br>
			Prezime :<xsl:value-of select="Akt/ZavrsniDeo/Potpisnik/Prezime"/> <br></br>
		</body>
		</html>
	</xsl:template>
</xsl:stylesheet>