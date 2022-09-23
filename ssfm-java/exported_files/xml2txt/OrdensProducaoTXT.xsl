<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<!-- <xsl:output method="html"/>-->
<xsl:output method="text" version="5.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
	|   | idOrdem | Quantidade | Estado Ordem |
	<xsl:apply-templates select="ShopFloor_Report/GestaoProducao/OrdensProducao/ordemProducao">
	<xsl:sort select="idOrdem"/>
	</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="ordemProducao">
	| <xsl:value-of select="position()"/> | <xsl:value-of select="idOrdem"/> | <xsl:value-of select="quantidade"/> | <xsl:value-of select="estadoOrdem"/> |
	</xsl:template>
</xsl:stylesheet>
