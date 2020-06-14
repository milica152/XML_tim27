<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xpath-default-namespace="https://github.com/milica152/XML_tim27"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:template match="/">
        <table class="author-data">
            <tbody>
                <br/>
                <br/>
                <br/>
                <tr>
                    <td align="left">
                        <p><xsl:value-of select="/coverLetter/author/surname"/>, <xsl:value-of select="/coverLetter/author/name"/></p>
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        <p><xsl:value-of select="/coverLetter/author/profession"/></p>
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        <p><xsl:value-of select="/coverLetter/author/contact"/></p>
                    </td>
                </tr>
                <tr><td> <br/> </td></tr>
                <tr>
                    <td align="left">
                        <p><xsl:value-of select="/coverLetter/date"/></p>
                    </td>
                </tr>
            </tbody>
        </table>
        <hr/>        
        <xsl:for-each select="/coverLetter/paragraph">
            <p>
                <xsl:attribute name="id">
                    <xsl:value-of select="@id"/></xsl:attribute>
                <xsl:attribute name="style">
                    <xsl:value-of select="@style"/></xsl:attribute>
                <xsl:value-of select="text()"/>
            </p>
        </xsl:for-each>        
        
        <br/>
        <br/>
        <p><xsl:value-of select="/coverLetter/signature/sincerely"/></p>
        <img width="150px">
            <xsl:attribute name="src">
                data:image/png;base64,
                <xsl:value-of select="/coverLetter/signature/signatureImage"/>
            </xsl:attribute>
            <xsl:attribute name="alt">
                signature
            </xsl:attribute>
        </img>    
    </xsl:template>
</xsl:stylesheet>