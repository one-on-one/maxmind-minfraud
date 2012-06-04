package com.ono.maxmind

/**
 * Created by Eric Berry (cavneb@gmail.com)
 */

class MaxmindResponse {

    Boolean countryMatch
    String countryCode
    Boolean highRiskCountry
    Integer distance
    String ipRegion
    String ipCity
    String ipPostalCode
    Float ipLatitude
    Float ipLongitude
    String ipIsp
    String ipOrg
    String ipAsnum
    String ipCorporateProxy
    String ipUserType
    Boolean anonymousProxy
    Float proxyScore
    String isTransparentProxy
    Boolean freeMail
    String carderEmail
    String highRiskUsername
    String highRiskPassword
    String binMatch
    String binCountry
    String binNameMatch
    String binName
    String binPhoneMatch
    String binPhone
    String phoneInBillingLocation
    String shipForward
    Boolean cityPostalMatch
    String shipCityPostalMatch
    Float score
    String explanation
    Float riskScore
    Integer queriesRemaining
    String maxmindId
    String error
    String response

    def MaxmindResponse(ApiConsumerResponse apiConsumerResponse) {
        if (!apiConsumerResponse) {
            throw new java.lang.IllegalArgumentException('[apiConsumerResponse] is required')
        }
        parseResponse(apiConsumerResponse.responseText)
    }

    protected parseResponse(String responseBody) {
        def attrs = convertResponseToMap(responseBody)

        attrs.each { k, v ->

            switch (k) {
                case 'err':
                    setAttribute('error', v)
                    break
                case 'custPhoneInBillingLoc':
                    setAttribute('phoneInBillingLocation', v)
                    break
                case 'maxmindID':
                    setAttribute('maxmindId', v)
                    break
                case 'isTransProxy':
                    setAttribute('isTransparentProxy', v)
                    break
                case 'explanation':
                    explanation = v
                    break
                default:
                    setAttribute(k, v)
            }
        }
    }

    protected void setAttribute(String k, String v) {
        k = camelCaseFromUnderscore(k)

        try {
            // Should a 'switch' statement be used here?
            if (!v) {
                this."${k}" = null
            }
            else if (v.isInteger()) {
                this."${k}" = Integer.parseInt(v)
            }
            else if (v.isFloat()) {
                this."${k}" = Float.parseFloat(v)
            }
            else if (v ==~ /[Yy]es/) {
                this."${k}" = true
            }
            else if (v ==~ /[Nn]o/) {
                this."${k}" = false
            }
            else {
                this."${k}" = v
            }
        }
        catch (Exception ex) {
//            ex.printStackTrace()
        }
    }

    static Map<String, String> convertResponseToMap(String responseBody) {
        def attrs = [:]
        def ret = responseBody.split(';').collect { item -> item.split('=') }
        ret.each { attrs[it[0]] = it.size() == 2 ? it[1] : null }
        return attrs
    }

    static String camelCaseFromUnderscore(String str) {
        return str.replaceAll(/(_)(.{1})/) { it[2].capitalize() }
    }

}
