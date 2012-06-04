package com.ono.maxmind

/**
 * Created by Eric Berry (cavneb@gmail.com)
 */

class MaxmindRequestTests extends GroovyTestCase {

    void testInstanciateWithAttributes() {
        def maxmindRequest = new MaxmindRequest('licenseKey', [clientIp: '127.0.0.1', binName: 'foo'])
        assert maxmindRequest.licenseKey == 'licenseKey'
        assert maxmindRequest.clientIp == '127.0.0.1'
        assert maxmindRequest.binName == 'foo'
    }

    void testValidate() {
        def maxmindRequest = new MaxmindRequest('licenseKey', [
                clientIp: '127.0.0.1', city: 'Las Vegas', region: 'NV', postalCode: '89147'
        ])
        def resp = shouldFail(java.lang.IllegalArgumentException) {
            maxmindRequest.validate()
        }
        assert resp == '[country] is required'
    }

    void testGet() {
        def maxmindRequest = new MaxmindRequest('licenseKey', [
                clientIp: '127.0.0.1', city: 'Las Vegas', region: 'NV', postalCode: '89147', country: 'US',
                domain: 'foo', forwardedIp: '127.0.0.2', shippingCountry: 'US', email: 'cavneb@gmail.com'
        ])
        def query = maxmindRequest.generateQuery()
        def response = maxmindRequest.get(query)
        assert response.responseText == 'distance=;countryMatch=;countryCode=;freeMail=;anonymousProxy=;score=;binMatch=;binCountry=;err=INVALID_LICENSE_KEY;proxyScore=;spamScore=;binName=;ip_isp=;ip_org=;binNameMatch=;binPhoneMatch=;binPhone=;custPhoneInBillingLoc=;highRiskCountry=;queriesRemaining=;cityPostalMatch=;shipCityPostalMatch=;maxmindID='
        assert response.responseCode == '200'
        assert response.responseContentType == 'Content-Type: text/plain'
    }

    void testGenerateQuery() {
        def maxmindRequest = new MaxmindRequest('licenseKey', [
                clientIp: '127.0.0.1', city: 'Las Vegas', region: 'NV', postalCode: '89147', country: 'US',
                domain: 'foo', forwardedIp: '127.0.0.2', shippingCountry: 'US', email: 'cavneb@gmail.com'
        ])
        def query = maxmindRequest.generateQuery()
        assert query['license_key'] == 'licenseKey'
        assert query['i'] == '127.0.0.1'
        assert query['city'] == 'Las Vegas'
        assert query['region'] == 'NV'
        assert query['postal'] == '89147'
        assert query['country'] == 'US'
        assert query['domain'] == 'foo'
        assert query['forwardedIP'] == '127.0.0.2'
        assert query['shipCountry'] == 'US'
        assert query['emailMD5'] == '469f88e0c2d6e73916b567f389987301'
        assert !query.containsKey('accept_language')
    }

    void testGenerateQueryWithMissingData() {
        def maxmindRequest = new MaxmindRequest('licenseKey', [:])
        def resp = shouldFail(java.lang.IllegalArgumentException) {
            maxmindRequest.validate()
        }
        assert resp.contains('is required')
    }

}
