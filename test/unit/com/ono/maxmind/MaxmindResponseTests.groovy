package com.ono.maxmind

/**
 * Created by Eric Berry (cavneb@gmail.com)
 */

class MaxmindResponseTests extends GroovyTestCase {

    def response = "distance=1158;countryMatch=Yes;countryCode=US;freeMail=Yes;anonymousProxy=No;score=3.08;" +
            "binMatch=NA;binCountry=;err=;proxyScore=0.00;ip_region=NY;ip_city=Rochester;ip_latitude=43.1548;" +
            "ip_longitude=-77.6156;binName=;ip_isp=Frontier Communications;ip_org=Frontier Communications;" +
            "binNameMatch=NA;binPhoneMatch=NA;binPhone=;custPhoneInBillingLoc=NotFound;highRiskCountry=No;" +
            "queriesRemaining=155827;cityPostalMatch=Yes;shipCityPostalMatch=;maxmindID=N2OFFKY3;" +
            "ip_asnum=AS7011 Frontier Communications of America, Inc.;ip_userType=residential;ip_countryConf=99;" +
            "ip_regionConf=99;ip_cityConf=74;ip_postalCode=;ip_postalConf=;ip_accuracyRadius=8;ip_netSpeedCell=Dialup;" +
            "ip_metroCode=538;ip_areaCode=585;ip_timeZone=America/New_York;ip_regionName=New York;ip_domain=;" +
            "ip_countryName=United States;ip_continentCode=NA;ip_corporateProxy=No;riskScore=4.91;" +
            "explanation=This order is slightly risky, and we suggest that you review it manually, especially for " +
            "B2B transactions. This order is higher risk because the distance between the billing address and the " +
            "user's actual location is so great. The order is slightly riskier because the e-mail domain, yahoo.com, " +
            "is a free e-mail provider"

    // The apiConsumerResponse is needed to instanciate the maxmindResponse
    def apiConsumerResponse = new ApiConsumerResponse( responseText: response )


    void testConvertResponseToMap() {
        def maxmindResponse = new MaxmindResponse(apiConsumerResponse)
        def attributes = maxmindResponse.convertResponseToMap(response)
        assert attributes['distance'] == '1158'
        assert attributes['shipCityPostalMatch'] == null
        assert attributes['ip_countryName'] == 'United States'
    }

    void testParseResponse() {
        def maxmindResponse = new MaxmindResponse(apiConsumerResponse)
        maxmindResponse.properties.each { prop ->
            println prop.inspect()
        }
        assert maxmindResponse.distance == 1158
        assert maxmindResponse.riskScore == 4.91F
        assert maxmindResponse.cityPostalMatch
        assert maxmindResponse.error == null
    }

    void testCamelCaseFromUnderscore() {
        assert MaxmindResponse.camelCaseFromUnderscore('ip_address') == 'ipAddress'
        assert MaxmindResponse.camelCaseFromUnderscore('this_is_a_test') == 'thisIsATest'
        assert MaxmindResponse.camelCaseFromUnderscore('countryMatch') == 'countryMatch'
    }
}
