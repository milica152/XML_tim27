<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xpath-default-namespace="https://github.com/milica152/XML_tim27"
    exclude-result-prefixes="xs">
    <xsl:template match="/">
        <h2>
            Review of scientific paper: <xsl:value-of select="/review/title"/>
        </h2>
        <table style="width: 570px;" border="1" cellspacing="0">
            <tbody>
                <tr bgcolor="#9acd32">
                    <th colspan="3">I REVIEWER INFORMATION</th>
                </tr>
                <tr>
                    <td>Name</td>
                    <td colspan="2">
                        <xsl:value-of select="/review/reviewer/surname"/>,
                        <xsl:value-of select="/review/reviewer/name"/>                       
                    </td>                   
                </tr>
                <tr>
                    <td>Profession</td>
                    <td colspan="2">
                        <xsl:value-of select="/review/reviewer/profession"/>
                    </td>
                </tr>
                <tr>
                    <td>Contact</td>
                    <td colspan="2">
                        <xsl:value-of select="/review/reviewer/contact"/>
                    </td>
                </tr>
                
                <tr bgcolor="#9acd32">
                    <th colspan="3">II AUTHORS INFORMATION</th>
                </tr>
                
                <xsl:for-each select="/review/authors/author">
                    <tr style="outline: thin solid">
                        <td>Name</td>
                        <td colspan="2">
                            <xsl:value-of select="surname"/>, 
                            <xsl:value-of select="name"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Profession</td>
                        <td colspan="2">
                            <xsl:value-of select="profession"/>
                        </td>
                    </tr>
                    <tr >
                        <td>Contact</td>
                        <td colspan="2">
                            <xsl:value-of select="contact"/>
                        </td>
                    </tr>
                </xsl:for-each>
                
                <tr bgcolor="#9acd32">
                    <th colspan="3">III QUESTIONNAIRE</th>
                </tr>
                <tr style="text-align: center;" bgcolor="#999999">
                    <td>
                        <blockquote>QUESTION</blockquote>
                    </td>
                    <td>
                        <blockquote>RATING</blockquote>
                    </td>
                    <td>
                        <blockquote>COMMENTS AND EXAMPLES</blockquote>
                    </td>
                </tr>
                <xsl:for-each select="/review/questionnaire/item">
                    <tr>
                        <td>
                            <xsl:value-of select="question"/>
                        </td>
                        <td>
                            <xsl:value-of select="answer"/>
                        </td>
                        <td>
                            <xsl:value-of select="comment_example"/>
                         </td>
                    </tr>
                </xsl:for-each>               
               
                <tr bgcolor="#9acd32">
                    <th colspan="3">IV ADDITIONAL COMMENTS</th>
                </tr>
                <xsl:for-each select="/review/comments/comment">
                    <tr>
                        <td colspan="3">
                            <xsl:value-of select="text()"/>
                        </td>
                    </tr>
                </xsl:for-each>
                
                <tr bgcolor="#9acd32">
                    <th colspan="3">V RATING</th>
                </tr>
                <tr>
                    <td>Rating</td>
                    <td colspan="2">
                        <xsl:value-of select="/review/paperRating"/>
                    </td>
                </tr>
                <tr>
                    <td>Recommendation</td>
                    <td colspan="2">
                        <xsl:value-of select="/review/recommendation"/>
                    </td>
                </tr>
            </tbody>
        </table>
        
    </xsl:template>
    
</xsl:stylesheet>