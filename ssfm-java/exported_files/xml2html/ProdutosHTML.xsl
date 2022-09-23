<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<!-- <xsl:output method="html"/>-->
<xsl:output method="html" version="5.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
	<html>
	<head><title>Produtos</title></head>
	<body>
	<center>
	<h1>Produtos</h1>
	<table border="1">
	<thead>
	<tr>
	<td></td><td><b>Codigo Fabrico Produto</b></td>
	<td><b>Descricao Breve</b></td><td><b>Codigo Categoria Produto</b></td>
	</tr>
	</thead>
	<tbody>
	<xsl:apply-templates select="ShopFloor_Report/GestaoProducao/Produtos/produto">
	<xsl:sort select="codigoFabricoProduto"/>
	</xsl:apply-templates>
	</tbody>
	</table>
	</center>
	</body>
	</html>
	</xsl:template>
	<xsl:template match="produto">
	<tr>
	<td><b><xsl:value-of select="position()"/></b></td>
	<td align="left"><xsl:value-of select="codigoFabricoProduto"/></td>
	<td><xsl:value-of select="descricaoBreve"/></td>
	<td align="center"><xsl:value-of select="codigoCatProduto"/></td>
	</tr>
	</xsl:template>
</xsl:stylesheet>
