<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:output method="xml"/>

	<xsl:template match="/">
		<xsl:element name="ShopFloor_Report">
			<xsl:attribute name="xsi:noNamespaceSchemaLocation">
				<xsl:text>main_xml.xsd</xsl:text>
			</xsl:attribute>

				<xsl:element name="Depositos">

					<!-- depósitos ordenados por código e sem 'Z' no código -->
					<xsl:variable name="remover" select="'Z'"/>
					<xsl:for-each select="//deposito">
						<xsl:sort select="codigo"/>
						<xsl:if test="not(contains(codigo, $remover))">
							<xsl:copy-of select="."/>
						</xsl:if>
					</xsl:for-each>
				</xsl:element>

				<!-- apenas linhas de produção que têm uma ordem de produção associada -->
				<xsl:element name="LinhasProducao">
					<xsl:for-each select="//ordemProducao">
						<xsl:variable name="checkOrdem" select="idLinhaProducao"/>
						<xsl:for-each select="//linhaProducao">
							<xsl:variable name="checkLinha" select="idLinhaProducao"/>
							<xsl:if test="$checkLinha = $checkOrdem">
								<xsl:copy-of select="."/>
							</xsl:if>
						</xsl:for-each>
					</xsl:for-each>
				</xsl:element>

				<!-- todas as categorias acima da posição 2 -->
				<xsl:element name="CategoriasMaterial">
					<xsl:for-each select="/*/CategoriasMaterial">
						<xsl:copy-of select="child::*[position() &gt; 2]"/>
					</xsl:for-each>
				</xsl:element>

				<!-- ordens com id inferior a 4000 -->
				<xsl:element name="OrdensProducao">
					<xsl:for-each select="//ordemProducao">
					<xsl:variable name="id" select="idOrdem"/>
					<xsl:choose>
						<xsl:when test="$id &lt; 4000">
							<xsl:copy-of select="."/>
						</xsl:when>
						<xsl:otherwise><xsl:comment>Existem ordens com id superior ou igual a 4k</xsl:comment></xsl:otherwise>
					</xsl:choose>
					</xsl:for-each>					
				</xsl:element>

		</xsl:element>
	</xsl:template>
</xsl:stylesheet>