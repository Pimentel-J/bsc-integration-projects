<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<!-- <xsl:output method="html"/>-->
<xsl:output method="html" version="5.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
	<html>
	<head><title>Ordens de Produção</title></head>
	<body>
	<center>
	<h1>Ordens de Produção</h1>
	<table border="1">
	<thead>
	<tr>
	<td></td><td><b>id Ordem</b></td>
	<td><b>Quantidade</b></td><td><b>Estado Ordem</b></td>
	</tr>
	</thead>
	<tbody>
	<xsl:apply-templates select="ShopFloor_Report/GestaoProducao/OrdensProducao/ordemProducao">
	<xsl:sort select="idOrdem"/>
	</xsl:apply-templates>
	</tbody>
	</table>
	</center>
	</body>
	</html>
	</xsl:template>
	<xsl:template match="ordemProducao">
	<tr>
	<td><b><xsl:value-of select="position()"/></b></td>
	<td align="left"><xsl:value-of select="idOrdem"/></td>
	<td><xsl:value-of select="quantidade"/></td>
	<td align="center"><xsl:value-of select="estadoOrdem"/></td>
	</tr>
	</xsl:template>
</xsl:stylesheet>
