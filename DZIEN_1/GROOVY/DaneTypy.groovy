import java.math.RoundingMode

int a = 10
def b = 3.6454
f = 6.54545645
boolean ok = true

BigDecimal price = 19.99

BigDecimal vat_java = (price*0.23G).setScale(2, RoundingMode.HALF_UP)
BigDecimal vat_g = (price*0.23G).round(2)

def user = null
def displayName = user ?: "Guest"

println("a+b= ${a+b+f}, ok = $ok, VAT_J = $vat_java, VAT_G = $vat_g, user=$displayName")

assert vat_g == 4.60
assert displayName == "Guest"
