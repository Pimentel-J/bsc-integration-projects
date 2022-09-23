<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<!-- <xsl:output method="html"/>-->
<xsl:output method="text" version="5.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
	|   | Codigo Fabrico | Descricao Breve | Codigo Categoria |
	<xsl:apply-templates select="ShopFloor_Report/GestaoProducao/Produtos/produto">
	<xsl:sort select="codigoFabricoProduto"/>
	</xsl:apply-templates>
	</xsl:template>
	<xsl:template match="produto">
	| <xsl:value-of select="position()"/> | <xsl:value-of select="codigoFabricoProduto"/> | <xsl:value-of select="descricaoBreve"/> | <xsl:value-of select="codigoCatProduto"/> |
	</xsl:template>
</xsl:stylesheet>
