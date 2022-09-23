<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<!-- <xsl:output method="html"/>-->
<xsl:output method="html" version="5.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
	<html>
	<head><title>Materiais</title></head>
	<body>
	<center>
	<h1>Materiais</h1>
	<table border="1">
	<thead>
	<tr>
	<td></td><td><b>Codigo Material</b></td>
	<td><b>Descricao</b></td><td><b>Codigo Categoria Material</b></td>
	</tr>
	</thead>
	<tbody>
	<xsl:apply-templates select="ShopFloor_Report/GestaoProducao/Materiais/material">
	<xsl:sort select="codigoMaterial"/>
	</xsl:apply-templates>
	</tbody>
	</table>
	</center>
	</body>
	</html>
	</xsl:template>
	<xsl:template match="material">
	<tr>
	<td><b><xsl:value-of select="position()"/></b></td>
	<td align="left"><xsl:value-of select="codigoMaterial"/></td>
	<td><xsl:value-of select="descricao"/></td>
	<td align="center"><xsl:value-of select="codigoCatMaterial"/></td>
	</tr>
	</xsl:template>
</xsl:stylesheet>
