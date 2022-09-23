<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<!-- <xsl:output method="html"/>-->
<xsl:output method="text" version="5.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
	|   | Codigo Material | Descricao | Codigo Categoria Material |
	<xsl:apply-templates select="ShopFloor_Report/GestaoProducao/Materiais/material">
	<xsl:sort select="codigoMaterial"/>
	</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="material">
	| <xsl:value-of select="position()"/> | <xsl:value-of select="codigoMaterial"/> | <xsl:value-of select="descricao"/> | <xsl:value-of select="codigoCatMaterial"/> |
	</xsl:template>
</xsl:stylesheet>
