<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
 >
	<xsl:template match="/">
		<html>
		<head>
		</head>
		<body>
			
			<h2><xsl:value-of select="Akt/Naslov"></xsl:value-of> </h2>

            <xsl:for-each select="Akt/Deo">
				<xsl:value-of select="@Naziv" /><br></br>
				<xsl:value-of select="@RedniBroj" /><br></br>

				<xsl:for-each select="Glava">
					<xsl:value-of select="@RedniBroj" /><br></br>
					<xsl:value-of select="@Naziv" /><br></br>
					<xsl:for-each select="Clan">
						<xsl:value-of select="@RedniBroj"/><br></br>
						<xsl:value-of select="@Naziv"/><br></br>
						<xsl:for-each select="Stav">

							<xsl:for-each select="Sadrzaj">
								<xsl:value-of select="Tekst"/><br></br>
								<xsl:value-of select="Referenca"/><br></br>

								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_akt"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_akt"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_clanovi"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_clanovi"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_stavovi"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_stavovi"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_tacke"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_tacke"/>
								</xsl:element>

								<xsl:value-of select="Datum"/><br></br>
							</xsl:for-each>

							<xsl:if test="PredTekst"> <xsl:value-of select="PredTekst"/><br></br> </xsl:if>



							<xsl:for-each select="Tacka">
								<xsl:value-of select="@RedniBroj"/><br></br>
								<xsl:value-of select="Sadrzaj"/><br></br>
								<xsl:for-each select="Podtacka">
									<xsl:value-of select="text()"/><br></br>
									<xsl:value-of select="@RedniBroj"/><br></br>
								</xsl:for-each>
							</xsl:for-each>



						</xsl:for-each>
					</xsl:for-each>
					<xsl:for-each select="Odeljak">
						<xsl:value-of select="@RedniBroj" /><br></br>
						<xsl:value-of select="@Naziv" /><br></br>

						<xsl:for-each select="Pododeljak">
							<xsl:value-of select="@RednoSlovo" /><br></br>

							<xsl:for-each select="Clan">
								<xsl:value-of select="@RedniBroj"/><br></br>
								<xsl:value-of select="@Naziv"/><br></br>
								<xsl:for-each select="Stav">

									<xsl:for-each select="Sadrzaj">
										<xsl:value-of select="Tekst"/><br></br>
										<xsl:value-of select="Referenca"/><br></br>

										<xsl:element name="a">
											<xsl:attribute name="href">
												<xsl:value-of select="Referenca/@ref_akt"/>
											</xsl:attribute>
											<xsl:value-of select="Referenca/@ref_akt"/>
										</xsl:element>
										<xsl:element name="a">
											<xsl:attribute name="href">
												<xsl:value-of select="Referenca/@ref_clanovi"/>
											</xsl:attribute>
											<xsl:value-of select="Referenca/@ref_clanovi"/>
										</xsl:element>
										<xsl:element name="a">
											<xsl:attribute name="href">
												<xsl:value-of select="Referenca/@ref_stavovi"/>
											</xsl:attribute>
											<xsl:value-of select="Referenca/@ref_stavovi"/>
										</xsl:element>
										<xsl:element name="a">
											<xsl:attribute name="href">
												<xsl:value-of select="Referenca/@ref_tacke"/>
											</xsl:attribute>
											<xsl:value-of select="Referenca/@ref_tacke"/>
										</xsl:element>

										<xsl:value-of select="Datum"/><br></br>
									</xsl:for-each>

									<xsl:if test="PredTekst"> <xsl:value-of select="PredTekst"/><br></br> </xsl:if>



									<xsl:for-each select="Tacka">
										<xsl:value-of select="@RedniBroj"/><br></br>
										<xsl:value-of select="Sadrzaj"/><br></br>
										<xsl:for-each select="Podtacka">
											<xsl:value-of select="text()"/><br></br>
											<xsl:value-of select="@RedniBroj"/><br></br>
										</xsl:for-each>
									</xsl:for-each>



								</xsl:for-each>
							</xsl:for-each>

						</xsl:for-each>

						<xsl:for-each select="Clan">
							<xsl:value-of select="@RedniBroj"/><br></br>
							<xsl:value-of select="@Naziv"/><br></br>
							<xsl:for-each select="Stav">

								<xsl:for-each select="Sadrzaj">
									<xsl:value-of select="Tekst"/><br></br>
									<xsl:value-of select="Referenca"/><br></br>

									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_akt"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_akt"/>
									</xsl:element>
									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_clanovi"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_clanovi"/>
									</xsl:element>
									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_stavovi"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_stavovi"/>
									</xsl:element>
									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_tacke"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_tacke"/>
									</xsl:element>

									<xsl:value-of select="Datum"/><br></br>
								</xsl:for-each>

								<xsl:if test="PredTekst"> <xsl:value-of select="PredTekst"/><br></br> </xsl:if>



								<xsl:for-each select="Tacka">
									<xsl:value-of select="@RedniBroj"/><br></br>
									<xsl:value-of select="Sadrzaj"/><br></br>
									<xsl:for-each select="Podtacka">
										<xsl:value-of select="text()"/><br></br>
										<xsl:value-of select="@RedniBroj"/><br></br>
									</xsl:for-each>
								</xsl:for-each>



							</xsl:for-each>
						</xsl:for-each>

					</xsl:for-each>
			</xsl:for-each>



			</xsl:for-each>

			<xsl:for-each select="Akt/Glava">
				<xsl:value-of select="@RedniBroj" /><br></br>
				<xsl:value-of select="@Naziv" /><br></br>
				<xsl:for-each select="Clan">
					<xsl:value-of select="@RedniBroj"/><br></br>
					<xsl:value-of select="@Naziv"/><br></br>
					<xsl:for-each select="Stav">

						<xsl:for-each select="Sadrzaj">
							<xsl:value-of select="Tekst"/><br></br>
							<xsl:value-of select="Referenca"/><br></br>

							<xsl:element name="a">
								<xsl:attribute name="href">
									<xsl:value-of select="Referenca/@ref_akt"/>
								</xsl:attribute>
								<xsl:value-of select="Referenca/@ref_akt"/>
							</xsl:element>
							<xsl:element name="a">
								<xsl:attribute name="href">
									<xsl:value-of select="Referenca/@ref_clanovi"/>
								</xsl:attribute>
								<xsl:value-of select="Referenca/@ref_clanovi"/>
							</xsl:element>
							<xsl:element name="a">
								<xsl:attribute name="href">
									<xsl:value-of select="Referenca/@ref_stavovi"/>
								</xsl:attribute>
								<xsl:value-of select="Referenca/@ref_stavovi"/>
							</xsl:element>
							<xsl:element name="a">
								<xsl:attribute name="href">
									<xsl:value-of select="Referenca/@ref_tacke"/>
								</xsl:attribute>
								<xsl:value-of select="Referenca/@ref_tacke"/>
							</xsl:element>

							<xsl:value-of select="Datum"/><br></br>
						</xsl:for-each>

						<xsl:if test="PredTekst"> <xsl:value-of select="PredTekst"/><br></br> </xsl:if>



						<xsl:for-each select="Tacka">
							<xsl:value-of select="@RedniBroj"/><br></br>
							<xsl:value-of select="Sadrzaj"/><br></br>
							<xsl:for-each select="Podtacka">
								<xsl:value-of select="text()"/><br></br>
								<xsl:value-of select="@RedniBroj"/><br></br>
							</xsl:for-each>
						</xsl:for-each>



					</xsl:for-each>
				</xsl:for-each>
				<xsl:for-each select="Odeljak">
					<xsl:value-of select="@RedniBroj" /><br></br>
					<xsl:value-of select="@Naziv" /><br></br>

					<xsl:for-each select="Pododeljak">
						<xsl:value-of select="@RednoSlovo" /><br></br>

						<xsl:for-each select="Clan">
							<xsl:value-of select="@RedniBroj"/><br></br>
							<xsl:value-of select="@Naziv"/><br></br>
							<xsl:for-each select="Stav">

								<xsl:for-each select="Sadrzaj">
									<xsl:value-of select="Tekst"/><br></br>
									<xsl:value-of select="Referenca"/><br></br>

									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_akt"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_akt"/>
									</xsl:element>
									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_clanovi"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_clanovi"/>
									</xsl:element>
									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_stavovi"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_stavovi"/>
									</xsl:element>
									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_tacke"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_tacke"/>
									</xsl:element>

									<xsl:value-of select="Datum"/><br></br>
								</xsl:for-each>

								<xsl:if test="PredTekst"> <xsl:value-of select="PredTekst"/><br></br> </xsl:if>



								<xsl:for-each select="Tacka">
									<xsl:value-of select="@RedniBroj"/><br></br>
									<xsl:value-of select="Sadrzaj"/><br></br>
									<xsl:for-each select="Podtacka">
										<xsl:value-of select="text()"/><br></br>
										<xsl:value-of select="@RedniBroj"/><br></br>
									</xsl:for-each>
								</xsl:for-each>



							</xsl:for-each>
						</xsl:for-each>

					</xsl:for-each>

					<xsl:for-each select="Clan">
						<xsl:value-of select="@RedniBroj"/><br></br>
						<xsl:value-of select="@Naziv"/><br></br>
						<xsl:for-each select="Stav">

							<xsl:for-each select="Sadrzaj">
								<xsl:value-of select="Tekst"/><br></br>
								<xsl:value-of select="Referenca"/><br></br>

								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_akt"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_akt"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_clanovi"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_clanovi"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_stavovi"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_stavovi"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_tacke"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_tacke"/>
								</xsl:element>

								<xsl:value-of select="Datum"/><br></br>
							</xsl:for-each>

							<xsl:if test="PredTekst"> <xsl:value-of select="PredTekst"/><br></br> </xsl:if>



							<xsl:for-each select="Tacka">
								<xsl:value-of select="@RedniBroj"/><br></br>
								<xsl:value-of select="Sadrzaj"/><br></br>
								<xsl:for-each select="Podtacka">
									<xsl:value-of select="text()"/><br></br>
									<xsl:value-of select="@RedniBroj"/><br></br>
								</xsl:for-each>
							</xsl:for-each>



						</xsl:for-each>
					</xsl:for-each>

				</xsl:for-each>
			</xsl:for-each>

			<xsl:for-each select="Akt/Clan">
				<xsl:value-of select="@RedniBroj"/><br></br>
				<xsl:value-of select="@Naziv"/><br></br>
				<xsl:for-each select="Stav">

					<xsl:for-each select="Sadrzaj">
						<xsl:value-of select="Tekst"/><br></br>
						<xsl:value-of select="Referenca"/><br></br>

						<xsl:element name="a">
							<xsl:attribute name="href">
								<xsl:value-of select="Referenca/@ref_akt"/>
							</xsl:attribute>
							<xsl:value-of select="Referenca/@ref_akt"/>
						</xsl:element>
						<xsl:element name="a">
							<xsl:attribute name="href">
								<xsl:value-of select="Referenca/@ref_clanovi"/>
							</xsl:attribute>
							<xsl:value-of select="Referenca/@ref_clanovi"/>
						</xsl:element>
						<xsl:element name="a">
							<xsl:attribute name="href">
								<xsl:value-of select="Referenca/@ref_stavovi"/>
							</xsl:attribute>
							<xsl:value-of select="Referenca/@ref_stavovi"/>
						</xsl:element>
						<xsl:element name="a">
							<xsl:attribute name="href">
								<xsl:value-of select="Referenca/@ref_tacke"/>
							</xsl:attribute>
							<xsl:value-of select="Referenca/@ref_tacke"/>
						</xsl:element>

						<xsl:value-of select="Datum"/><br></br>
					</xsl:for-each>

					<xsl:if test="PredTekst"> <xsl:value-of select="PredTekst"/><br></br> </xsl:if>



					<xsl:for-each select="Tacka">
						<xsl:value-of select="@RedniBroj"/><br></br>
						<xsl:value-of select="Sadrzaj"/><br></br>
						<xsl:for-each select="Podtacka">
							<xsl:value-of select="text()"/><br></br>
							<xsl:value-of select="@RedniBroj"/><br></br>
						</xsl:for-each>
					</xsl:for-each>



				</xsl:for-each>
			</xsl:for-each>

			<xsl:if test="Akt/ZavrsniDeo/PrelazneOdredbe"><xsl:value-of select="Akt/ZavrsniDeo/PrelazneOdredbe"/>
			<xsl:value-of select="@RedniBroj" /><br></br>
			<xsl:value-of select="@Naziv" /><br></br>
				<xsl:for-each select="Clan">
					<xsl:value-of select="@RedniBroj"/><br></br>
					<xsl:value-of select="@Naziv"/><br></br>
						<xsl:for-each select="Stav">

							<xsl:for-each select="Sadrzaj">
								<xsl:value-of select="Tekst"/><br></br>
								<xsl:value-of select="Referenca"/><br></br>

								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_akt"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_akt"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_clanovi"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_clanovi"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_stavovi"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_stavovi"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_tacke"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_tacke"/>
								</xsl:element>

								<xsl:value-of select="Datum"/><br></br>
							</xsl:for-each>

							<xsl:if test="PredTekst"> <xsl:value-of select="PredTekst"/><br></br> </xsl:if>



							<xsl:for-each select="Tacka">
								<xsl:value-of select="@RedniBroj"/><br></br>
								<xsl:value-of select="Sadrzaj"/><br></br>
									<xsl:for-each select="Podtacka">
										<xsl:value-of select="text()"/><br></br>
										<xsl:value-of select="@RedniBroj"/><br></br>
									</xsl:for-each>
							</xsl:for-each>



						</xsl:for-each>
				</xsl:for-each>
				<xsl:for-each select="Odeljak">
					<xsl:value-of select="@RedniBroj" /><br></br>
					<xsl:value-of select="@Naziv" /><br></br>

					<xsl:for-each select="Pododeljak">
						<xsl:value-of select="@RednoSlovo" /><br></br>

						<xsl:for-each select="Clan">
							<xsl:value-of select="@RedniBroj"/><br></br>
							<xsl:value-of select="@Naziv"/><br></br>
							<xsl:for-each select="Stav">

								<xsl:for-each select="Sadrzaj">
									<xsl:value-of select="Tekst"/><br></br>
									<xsl:value-of select="Referenca"/><br></br>

									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_akt"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_akt"/>
									</xsl:element>
									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_clanovi"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_clanovi"/>
									</xsl:element>
									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_stavovi"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_stavovi"/>
									</xsl:element>
									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_tacke"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_tacke"/>
									</xsl:element>

									<xsl:value-of select="Datum"/><br></br>
								</xsl:for-each>

								<xsl:if test="PredTekst"> <xsl:value-of select="PredTekst"/><br></br> </xsl:if>



								<xsl:for-each select="Tacka">
									<xsl:value-of select="@RedniBroj"/><br></br>
									<xsl:value-of select="Sadrzaj"/><br></br>
									<xsl:for-each select="Podtacka">
										<xsl:value-of select="text()"/><br></br>
										<xsl:value-of select="@RedniBroj"/><br></br>
									</xsl:for-each>
								</xsl:for-each>



							</xsl:for-each>
						</xsl:for-each>

					</xsl:for-each>

					<xsl:for-each select="Clan">
						<xsl:value-of select="@RedniBroj"/><br></br>
						<xsl:value-of select="@Naziv"/><br></br>
						<xsl:for-each select="Stav">

							<xsl:for-each select="Sadrzaj">
								<xsl:value-of select="Tekst"/><br></br>
								<xsl:value-of select="Referenca"/><br></br>

								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_akt"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_akt"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_clanovi"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_clanovi"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_stavovi"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_stavovi"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_tacke"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_tacke"/>
								</xsl:element>

								<xsl:value-of select="Datum"/><br></br>
							</xsl:for-each>

							<xsl:if test="PredTekst"> <xsl:value-of select="PredTekst"/><br></br> </xsl:if>



							<xsl:for-each select="Tacka">
								<xsl:value-of select="@RedniBroj"/><br></br>
								<xsl:value-of select="Sadrzaj"/><br></br>
								<xsl:for-each select="Podtacka">
									<xsl:value-of select="text()"/><br></br>
									<xsl:value-of select="@RedniBroj"/><br></br>
								</xsl:for-each>
							</xsl:for-each>



						</xsl:for-each>
					</xsl:for-each>

				</xsl:for-each>
			</xsl:if>

			<xsl:if test="Akt/ZavrsniDeo/ZavrsneOdredbe"><xsl:value-of select="Akt/ZavrsniDeo/ZavrsneOdredbe"/>
				<xsl:value-of select="@RedniBroj" /><br></br>
				<xsl:value-of select="@Naziv" /><br></br>
				<xsl:for-each select="Clan">
					<xsl:value-of select="@RedniBroj"/><br></br>
					<xsl:value-of select="@Naziv"/><br></br>
					<xsl:for-each select="Stav">

						<xsl:for-each select="Sadrzaj">
							<xsl:value-of select="Tekst"/><br></br>
							<xsl:value-of select="Referenca"/><br></br>

							<xsl:element name="a">
								<xsl:attribute name="href">
									<xsl:value-of select="Referenca/@ref_akt"/>
								</xsl:attribute>
								<xsl:value-of select="Referenca/@ref_akt"/>
							</xsl:element>
							<xsl:element name="a">
								<xsl:attribute name="href">
									<xsl:value-of select="Referenca/@ref_clanovi"/>
								</xsl:attribute>
								<xsl:value-of select="Referenca/@ref_clanovi"/>
							</xsl:element>
							<xsl:element name="a">
								<xsl:attribute name="href">
									<xsl:value-of select="Referenca/@ref_stavovi"/>
								</xsl:attribute>
								<xsl:value-of select="Referenca/@ref_stavovi"/>
							</xsl:element>
							<xsl:element name="a">
								<xsl:attribute name="href">
									<xsl:value-of select="Referenca/@ref_tacke"/>
								</xsl:attribute>
								<xsl:value-of select="Referenca/@ref_tacke"/>
							</xsl:element>

							<xsl:value-of select="Datum"/><br></br>
						</xsl:for-each>

						<xsl:if test="PredTekst"> <xsl:value-of select="PredTekst"/><br></br> </xsl:if>



						<xsl:for-each select="Tacka">
							<xsl:value-of select="@RedniBroj"/><br></br>
							<xsl:value-of select="Sadrzaj"/><br></br>
							<xsl:for-each select="Podtacka">
								<xsl:value-of select="text()"/><br></br>
								<xsl:value-of select="@RedniBroj"/><br></br>
							</xsl:for-each>
						</xsl:for-each>



					</xsl:for-each>
				</xsl:for-each>
				<xsl:for-each select="Odeljak">
					<xsl:value-of select="@RedniBroj" /><br></br>
					<xsl:value-of select="@Naziv" /><br></br>

					<xsl:for-each select="Pododeljak">
						<xsl:value-of select="@RednoSlovo" /><br></br>

						<xsl:for-each select="Clan">
							<xsl:value-of select="@RedniBroj"/><br></br>
							<xsl:value-of select="@Naziv"/><br></br>
							<xsl:for-each select="Stav">

								<xsl:for-each select="Sadrzaj">
									<xsl:value-of select="Tekst"/><br></br>
									<xsl:value-of select="Referenca"/><br></br>

									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_akt"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_akt"/>
									</xsl:element>
									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_clanovi"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_clanovi"/>
									</xsl:element>
									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_stavovi"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_stavovi"/>
									</xsl:element>
									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="Referenca/@ref_tacke"/>
										</xsl:attribute>
										<xsl:value-of select="Referenca/@ref_tacke"/>
									</xsl:element>

									<xsl:value-of select="Datum"/><br></br>
								</xsl:for-each>

								<xsl:if test="PredTekst"> <xsl:value-of select="PredTekst"/><br></br> </xsl:if>



								<xsl:for-each select="Tacka">
									<xsl:value-of select="@RedniBroj"/><br></br>
									<xsl:value-of select="Sadrzaj"/><br></br>
									<xsl:for-each select="Podtacka">
										<xsl:value-of select="text()"/><br></br>
										<xsl:value-of select="@RedniBroj"/><br></br>
									</xsl:for-each>
								</xsl:for-each>



							</xsl:for-each>
						</xsl:for-each>

					</xsl:for-each>

					<xsl:for-each select="Clan">
						<xsl:value-of select="@RedniBroj"/><br></br>
						<xsl:value-of select="@Naziv"/><br></br>
						<xsl:for-each select="Stav">

							<xsl:for-each select="Sadrzaj">
								<xsl:value-of select="Tekst"/><br></br>
								<xsl:value-of select="Referenca"/><br></br>

								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_akt"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_akt"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_clanovi"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_clanovi"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_stavovi"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_stavovi"/>
								</xsl:element>
								<xsl:element name="a">
									<xsl:attribute name="href">
										<xsl:value-of select="Referenca/@ref_tacke"/>
									</xsl:attribute>
									<xsl:value-of select="Referenca/@ref_tacke"/>
								</xsl:element>

								<xsl:value-of select="Datum"/><br></br>
							</xsl:for-each>

							<xsl:if test="PredTekst"> <xsl:value-of select="PredTekst"/><br></br> </xsl:if>



							<xsl:for-each select="Tacka">
								<xsl:value-of select="@RedniBroj"/><br></br>
								<xsl:value-of select="Sadrzaj"/><br></br>
								<xsl:for-each select="Podtacka">
									<xsl:value-of select="text()"/><br></br>
									<xsl:value-of select="@RedniBroj"/><br></br>
								</xsl:for-each>
							</xsl:for-each>



						</xsl:for-each>
					</xsl:for-each>

				</xsl:for-each>
			</xsl:if>
			<!--Od Organa na nadalje-->
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