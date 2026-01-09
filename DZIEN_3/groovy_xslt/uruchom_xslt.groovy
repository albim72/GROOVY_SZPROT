import javax.xml.transform.*
import javax.xml.transform.stream.*

def xmlFile  = new File("orders.xml")
def xsltFile = new File("orders.xslt")
def output   = new File("orders.html")

TransformerFactory factory = TransformerFactory.newInstance()
Transformer transformer = factory.newTransformer(
        new StreamSource(xsltFile)
)

transformer.transform(
        new StreamSource(xmlFile),
        new StreamResult(output)
)

println "HTML generated: ${output.absolutePath}"
