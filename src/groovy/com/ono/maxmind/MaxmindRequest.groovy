package com.ono.maxmind

import org.codehaus.groovy.grails.plugins.codecs.MD5Codec
import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * Created by Eric Berry (cavneb@gmail.com)
 */

class MaxmindRequest {

    // Required Fields
    String licenseKey
    String clientIp
    String city
    String region
    String postalCode
    String country

    // Optional Fields
    String domain
    String bin
    String binName
    String binPhone
    String custPhone
    String requestedType
    String forwardedIp
    String email
    String username
    String password
    String transactionId
    String sessionId
    String shippingAddress
    String shippingCity
    String shippingRegion
    String shippingPostal
    String shippingCountry
    String userAgent
    String acceptLanguage

    def MaxmindRequest(Map attrs = [:]) {
        if(ConfigurationHolder.config.maxmind?.minfraud?.licenseKey){
            this.licenseKey = ConfigurationHolder.config.maxmind.minfraud.licenseKey
        }
        attrs.each { k, v ->
            this."${k}" = v
        }
    }

    def MaxmindRequest(String licenseKey, Map attrs = [:]) {
        this.licenseKey = licenseKey
        attrs.each { k, v ->
            this."${k}" = v
        }
    }

    def ApiConsumerResponse get(query) {
        ApiConsumerResponse response = ApiConsumer.getText('https://minfraud1.maxmind.com', '/app/ccv2r', query)
        println response.inspect()
        return response
    }

    def MaxmindResponse getResponse() {
        def query = generateQuery()
        def response = get(query)
        def maxmindResponse = new MaxmindResponse(response)
        return maxmindResponse
    }

    protected Map<String, String> generateQuery() {
        validate()

        def requiredFields = [
                'i': clientIp,
                'city': city,
                'region': region,
                'postal': postalCode,
                'country': country,
                'license_key': licenseKey
        ]

        def optionalFields = [:]
        if (domain) { optionalFields['domain'] = domain }
        if (bin) { optionalFields['bin'] = bin }
        if (binName) { optionalFields['binName'] = binName }
        if (binPhone) { optionalFields['binPhone'] = binPhone }
        if (custPhone) { optionalFields['custPhone'] = custPhone }
        if (requestedType) { optionalFields['requested_type'] = requestedType }
        if (forwardedIp) { optionalFields['forwardedIP'] = forwardedIp }
        if (email) { optionalFields['emailMD5'] = MD5Codec.encode(email) }
        if (username) { optionalFields['usernameMD5'] = MD5Codec.encode(username) }
        if (password) { optionalFields['passwordMD5'] = MD5Codec.encode(password) }
        if (shippingAddress) { optionalFields['shipAddr'] = shippingAddress }
        if (shippingRegion) { optionalFields['shipCity'] = shippingRegion }
        if (shippingRegion) { optionalFields['shipRegion'] = shippingRegion }
        if (shippingPostal) { optionalFields['shipPostal'] = shippingPostal }
        if (shippingCountry) { optionalFields['shipCountry'] = shippingCountry }
        if (transactionId) { optionalFields['txnID'] = transactionId }
        if (sessionId) { optionalFields['sessionID'] = sessionId }
        if (userAgent) { optionalFields['user_agent'] = userAgent }
        if (acceptLanguage) { optionalFields['accept_language'] = acceptLanguage }

        return requiredFields + optionalFields
    }

    protected validate() {
        if (!licenseKey) { throw new java.lang.IllegalArgumentException('[licenseKey] is required') }
        if (!clientIp) { throw new java.lang.IllegalArgumentException('[clientIp] is required') }
        if (!city) { throw new java.lang.IllegalArgumentException('[city] is required') }
        if (!region) { throw new java.lang.IllegalArgumentException('[region] is required') }
        if (!postalCode) { throw new java.lang.IllegalArgumentException('[postalCode] is required') }
        if (!country) { throw new java.lang.IllegalArgumentException('[country] is required') }
    }

}
