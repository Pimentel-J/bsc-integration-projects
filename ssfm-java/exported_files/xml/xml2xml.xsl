<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<Depositos>
			<xsl:attribute name="Quantidade">
				<xsl:valueof select="count(Depositos/deposito)"/>
			</xsl:attribute>
			<xsl:apply-templates select="Depositos/deposito"/>
		</Depositos>
	</xsl:template>

</xsl:stylesheet>