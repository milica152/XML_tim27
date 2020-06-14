<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xpath-default-namespace="https://github.com/milica152/XML_tim27"
                exclude-result-prefixes="xs"
                version="2.0">
    <xsl:template match="/">
        <h1 align="center">
            <xsl:value-of select="/scientificPaper/title"/>
        </h1>
        <table class="paper-metadata" align="center">
            <tbody>
                <xsl:for-each select="/scientificPaper/metadata">
                    <tr>
                        <td align="center" width="26%">
                            <p>Status: <xsl:value-of select="status"/></p>
                        </td>
                        <td align="center" width="45%">
                            <p>Date Published: <xsl:value-of select="datePublished"/></p>
                        </td>
                        <td align="center">
                            <p>Date Accepted: <xsl:value-of select="dateAccepted"/></p>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
        <hr />
        <table class="paper-authors" align="center">
            <tbody>
                <tr>
                    <xsl:for-each select="/scientificPaper/authors/author">
                        <xsl:attribute name="id"> <xsl:value-of select="@id"></xsl:value-of></xsl:attribute>
                        <td align="center" style="padding-right:20px;">
                            <h4><xsl:value-of select="surname"/>, <xsl:value-of select="name"/></h4>
                            <p><xsl:value-of select="profession"/></p>
                            <p><xsl:value-of select="contact"/></p>
                        </td>
                    </xsl:for-each>
                </tr>
            </tbody>
        </table>
        <hr />
        <p><strong>ABSTRACT</strong>:</p>
        <xsl:for-each select="/scientificPaper/abstract/paragraph">
            <p>
                <xsl:attribute name="id"> <xsl:value-of select="@id"></xsl:value-of></xsl:attribute>
                <xsl:attribute name="style"> <xsl:value-of select="@style"></xsl:value-of></xsl:attribute>
                <xsl:value-of select="text()"/>
            </p>
        </xsl:for-each>

        <p><strong>KEYWORDS: </strong>
            <xsl:for-each select="/scientificPaper/metadata/keywords/keyword">
                <xsl:value-of select="text()"/>;
            </xsl:for-each>
        </p>


        <xsl:for-each select="/scientificPaper/chapters">
            <xsl:apply-templates/>
        </xsl:for-each>
        <hr />
        <xsl:for-each select="/scientificPaper/references/reference">
            <p>
                <xsl:number/><a>
                <xsl:attribute name="href"><xsl:value-of select="url"></xsl:value-of></xsl:attribute>
                <xsl:value-of select="name"/>
            </a>, <xsl:value-of select="year"/>, <xsl:value-of select="authorName"/>
            </p>
        </xsl:for-each>

    </xsl:template>
    <xsl:template match="chapter/text/paragraph">
        <p>
            <xsl:attribute name="id"> <xsl:value-of select="@id"></xsl:value-of></xsl:attribute>
            <xsl:attribute name="style"> <xsl:value-of select="@style"></xsl:value-of></xsl:attribute>
            <xsl:value-of select="text()"/>
        </p>

    </xsl:template>
    <xsl:template match="title[@level='1']">
        <h3>
            <xsl:attribute name="style"> <xsl:value-of select="@style"></xsl:value-of></xsl:attribute>
            <xsl:value-of select="text()"/>
        </h3>
    </xsl:template>

    <xsl:template match="title[@level='2']">
        <h4>
            <xsl:attribute name="style"> <xsl:value-of select="@style"></xsl:value-of></xsl:attribute>
            <xsl:value-of select="text()"/>
        </h4>
    </xsl:template>

    <xsl:template match="title[@level='3']">
        <h5>
            <xsl:attribute name="style"> <xsl:value-of select="@style"></xsl:value-of></xsl:attribute>
            <xsl:value-of select="text()"/>
        </h5>
    </xsl:template>

    <xsl:template match="title[not(@level='0' or @level='1' or @level='2' or @level='3')]">
        <h6>
            <xsl:attribute name="style"> <xsl:value-of select="@style"></xsl:value-of></xsl:attribute>
            <xsl:value-of select="text()"/>
        </h6>
    </xsl:template>

    <xsl:template match="id">
        <xsl:value-of select="TId"/>
    </xsl:template>

    <xsl:template match="list[@isOrdered='0']">
        <ul>
            <xsl:for-each select="listItem">
                <li>
                    <xsl:value-of select="text()"/>
                </li>
            </xsl:for-each>
        </ul>
    </xsl:template>

    <xsl:template match="list[@isOrdered='1']">
        <ol>
            <xsl:for-each select="listItem">
                <li>
                    <xsl:value-of select="text()"/>
                </li>
            </xsl:for-each>
        </ol>
    </xsl:template>

    <xsl:template match="table">
        <table border="1px">
            <xsl:attribute name="id"> <xsl:value-of select="@id"></xsl:value-of></xsl:attribute>
            <xsl:attribute name="width"> <xsl:value-of select="@width"></xsl:value-of><xsl:value-of select="@measuringUnit"></xsl:value-of></xsl:attribute>
            <xsl:attribute name="height"> <xsl:value-of select="@height"></xsl:value-of><xsl:value-of select="@measuringUnit"></xsl:value-of></xsl:attribute>
            <xsl:for-each select="row">
                <tr>
                    <xsl:for-each select="cell">
                        <td>
                            <xsl:value-of select="text()"/>
                        </td>
                    </xsl:for-each>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>

    <xsl:template match="image">
        <table border="1px">
            <tr>
                <td align="center">
                    <img width="100%">
                        <xsl:attribute name="id"> <xsl:value-of select="@id"></xsl:value-of></xsl:attribute>
                        <xsl:attribute name="src">
                            data:image/png;base64, <xsl:value-of select="concreteImage"></xsl:value-of>
                        </xsl:attribute>
                        <xsl:attribute name="alt">
                            <xsl:value-of select="description"></xsl:value-of>
                        </xsl:attribute>
                    </img>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <p>
                        <xsl:value-of select="description"></xsl:value-of>
                    </p>
                </td>
            </tr>
        </table>
    </xsl:template>

    <xsl:template match="codeBlock">
        <code>
            <xsl:value-of select="text()"/>
        </code>
    </xsl:template>



</xsl:stylesheet>