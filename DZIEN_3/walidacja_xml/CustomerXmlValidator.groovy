import groovy.xml.XmlSlurper

class CustomerXmlValidator {
    /**
     * Prosty walidator XML (logiczny, nie XSD - Schema)
     *  - customer/@id: musi być liczbą całkowitą >0
     *  - customer/@active: musi być "true" albo "false"
     *  - name: niepuste (po trim)
     *  - email: prosty regex
     */

    static class ValidationResult{
        boolean ok
        List<String> errors

        @Override
        String toString() {
            ok ? "OK":("ERRORS\n - " + errors.join("\n- "))
        }
    }

    static ValidationResult validateFile(String path){
        def root = new XmlSlurper(false,false).parse(new File(path))

        List<String> errors = []

        //iterujemy po customer
        root.customer.eachWithIndex {c,idx ->
            String prefix = "customer[#${idx}]"

            // --- id ---
            def idStr = c.@id?.toString()
            Long idVal = null

            try {
                idVal = idStr as Long
            }
            catch (Exception ignored){
                errors << "${prefix}: @id is not a number: '${idStr}'"
            }

            if (idVal != null && idVal <=0) {
                errors << "${prefix}: @id must be >0: '${idStr}'"
            }

            // ---- active ------
            def activeStr = c.@active?.toString()
            if (!(activeStr in ["true","false"])){
                errors << "${prefix}: @active must be \"true\",\"false\" got'${activeStr}'"
            }

            // ---name---
            def name = c.name?.text()?.trim()
            if(!name) {
                errors << "${prefix}: <name> is required (non-blank)"
            }

            // ---email---
            def email = c.email?.text()?.trim()
            if(!email){
                errors << "${prefix}: <email> is required"
            } else {
                //prosty regex
                def emailRx = /^[A-Za-z0-9._%+\-]+@[A-Za-z0-9.\-]+\.[A-Za-z]{2,}$/
                if (!(email ==~ emailRx)) {
                    errors << "${prefix}: <email> has invalid format: '${email}'"
                }
            }
        }
        return new ValidationResult(ok:errors.isEmpty(),errors:errors)
    }
}
