<xsl:stylesheet version="1.0"
      xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
        <body>
            <h2>Orders</h2>
            <table border="1">
                <tr>
                    <th>ID</th>
                    <th>Status</th>
                    <th>Customer</th>
                    <th>Amount</th>
                </tr>
                <xsl:for-each select="orders/order">
                    <tr>
                        <td><xsl:value-of select="@id"/></td>
                        <td><xsl:value-of select="@status"/></td>
                        <td><xsl:value-of select="customer"/></td>
                        <td><xsl:value-of select="amount"/></td>
                    </tr>
                </xsl:for-each>
            </table>
        </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
