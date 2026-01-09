import groovy.xml.XmlSlurper

def xml = '''
<orders>
    <order id="1" status="NEW">
        <customer>Jan Kowalski</customer>
        <amount>120.50</amount>
    </order>
    <order id="2" status="PAID">
        <customer>Anna Nowak</customer>
        <amount>300.00</amount>
    </order>
</orders>
'''

def root = new XmlSlurper().parseText(xml)

// Iteracja po węzłach
root.order.each { o ->
    println "ID=${o.@id}, status=${o.@status}"
    println "  customer=${o.customer.text()}"
    println "  amount=${o.amount.text()}"
}
