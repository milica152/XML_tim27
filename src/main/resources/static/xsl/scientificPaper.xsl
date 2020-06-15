<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    xpath-default-namespace="https://github.com/milica152/XML_tim27"
    version="2.0">
    
    <xsl:template match="/">
        <html>
            <head>
                <title><xsl:value-of select="/scientificPaper/title"/></title>
            </head>
            <style>
                pre code {
                background-color: #eee;
                border: 1px solid #999;
                display: block;
                padding: 20px;
                }
            </style>
            <body>
                <h1 style="text-align: center;">
                    <xsl:value-of select="/scientificPaper/title"/>
                </h1>
                <p></p>
                <xsl:for-each select="/scientificPaper/authors/author">
                    <div calss="column">
                        <h5 style="text-align: center;">
                            <xsl:value-of select="surname"/>, 
                            <xsl:value-of select="name"/>
                        </h5>
                        <h6 style="text-align: center; margin-top: -10px;">
                            <xsl:value-of select="profession"/>
                        </h6> 
                        <h6 style="text-align: center; margin-top: -10px;">
                            <xsl:value-of select="contact"/>
                        </h6>
                    </div>
                </xsl:for-each>
               
                <p></p>
                <p><strong>Abstract - </strong>
                    <xsl:value-of select="/scientificPaper/abstract/plainChapter/text/paragraph"/>
                </p>
                <p style="margin-top: -10px;"><strong>Keywords - </strong> 
                    <xsl:for-each select="/scientificPaper/metadata/keywords/keyword">
                        <xsl:value-of select="text()"/>;
                    </xsl:for-each>
                </p>
                
                <div class="row">
                    <ol style="list-style-type: upper-roman;">
                        <xsl:for-each select="/scientificPaper/chapters/chapter">
                            <li>
                                <xsl:call-template name="chapter-template">
                                </xsl:call-template>
                            </li>
                        </xsl:for-each>                                              
                    </ol>
                    <b><i>References</i></b>
                    <p>
                        <xsl:for-each select="/scientificPaper/references/reference">
                            <xsl:sort select="@id"></xsl:sort>
                            <xsl:call-template name="reference-template">
                            </xsl:call-template>
                        </xsl:for-each>
                    </p>
                </div>               
            </body>            
        </html>
    </xsl:template>
   
    <xsl:template name="chapter-template">
        <xsl:apply-templates></xsl:apply-templates>
    </xsl:template>
    <xsl:template name="reference-template">
        <div>
            <xsl:attribute name="id">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:value-of select="@id"/>. <a>
                <xsl:attribute name="href">
                    <xsl:value-of select="/scientificPaper/references/reference/url"/>
                </xsl:attribute>                
                <xsl:value-of select="/scientificPaper/references/reference/@authorName"/>; 
                <xsl:value-of select="/scientificPaper/references/reference/name"/>, 
                <xsl:value-of select="/scientificPaper/references/reference/@year"/>
            </a>
        </div><br/>
    </xsl:template>
    
    
    <xsl:template match="title">
        <strong><xsl:value-of select="text()"/></strong>
    </xsl:template>
    <xsl:template match="text">
        <xsl:for-each select="paragraph">
            <p style="margin-left: -23px;">
                <xsl:value-of select="text()"/>
            </p>
        </xsl:for-each>        
    </xsl:template>
    <xsl:template match="table">
        <div style="text-align: center;"><br/>
            <table border="1" align="center" style="width:30%">
                <xsl:for-each select="row">
                    <tr>
                        <xsl:for-each select="cell">
                            <td>
                                <xsl:apply-templates></xsl:apply-templates>
                                
                            </td>
                        </xsl:for-each>
                        
                    </tr>
                </xsl:for-each>
            </table>
            <span><i>
                <xsl:value-of select="description"/>
            </i></span>
        </div><br/>
    </xsl:template>
    <xsl:template match="image">
        <div style="text-align: center;"><img>
            <xsl:attribute name="src">data:image/jpeg;charset=utf-8;base64,<xsl:value-of select="concreteImage"/>
            </xsl:attribute>
        </img> <br/>
            <span><i> <xsl:value-of select="description"/></i></span></div>
    </xsl:template>
    <xsl:template match="list">        
        <xsl:choose>
            <xsl:when test="@isOrdered='1'">
                <ol>
                    <xsl:for-each select="listItem">
                        <li>  
                            <xsl:value-of select="text()"/>
                        </li>
                    </xsl:for-each>                    
                </ol>
            </xsl:when>
            <xsl:otherwise>
                <ul>
                    <xsl:for-each select="listItem">
                        <li>  
                            <xsl:value-of select="text()"/>
                        </li>
                    </xsl:for-each>     
                </ul>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="codeBlock">
        <pre>
         <code>
            <xsl:value-of select="text()"/>            
        </code>
        </pre>
    </xsl:template>
    
    <xsl:template match="referencePointer">
        <span><xsl:value-of select="text()"/></span>
        <xsl:variable name="refId" select="@id"/>
        <a>
            <xsl:attribute name="href">#<xsl:value-of select="./@id"/>
            </xsl:attribute>
            <sup>[
                <xsl:for-each select="../../../references/reference">
                    <xsl:sort select="@id"></xsl:sort>
                    <xsl:if test="@id = $refId">
                        <xsl:value-of select="position()"/>
                    </xsl:if>
                </xsl:for-each>               
                ]</sup>
        </a>
    </xsl:template>
    
    
    
    
</xsl:stylesheet>