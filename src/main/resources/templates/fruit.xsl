<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <head>
                <title>My Fruit</title>
            </head>
            <body>
                <div style="text-align:center">
                    <h1>Fruit Information</h1>
                    <table>
                        <tr>
                            <td><strong>Name:</strong></td>
                            <td><xsl:value-of select="FruitsHibernateEntity/name"/></td>
                        </tr>
                        <tr>
                            <td><strong>Calories:</strong></td>
                            <td><xsl:value-of select="FruitsHibernateEntity/calories"/></td>
                        </tr>
                        <tr>
                            <td><strong>Sugar:</strong></td>
                            <td><xsl:value-of select="FruitsHibernateEntity/sugar"/></td>
                        </tr>
                        <tr>
                            <td><strong>ID:</strong></td>
                            <td><xsl:value-of select="FruitsHibernateEntity/id"/></td>
                        </tr>
                    </table>
                </div>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
