Maxmind minFraud
================

Interfaces with Maxmind's minFraud anti-fraud service.

Installation
------------
  grails install-plugin maxmind-minfraud

Usage
-----

### Minimum Required ###
These are the only required fields to acquire a response from Maxmind.
  
    def request = new MaxmindRequest(
        licenseKey: 'LICENSE_KEY',
        clientIp: '24.24.24.24',
        city: 'New York',
        region: 'NY',
        postalCode: '11434',
        country: 'US'
    )


### Recommended ###
For increased accuracy, these are the recommended fields to submit to Maxmind. The additional
fields here are optional and can be all or none.

    def request = new MaxmindRequest(
        licenseKey: 'LICENSE_KEY',
        client_ip: '24.24.24.24',
        city: 'New York',
        region: 'NY',
        postal: '11434',
        country: 'US',
        domain: 'yahoo.com',
        bin: '549099',
        forwarded_ip: '24.24.24.25',
        email: 'test@test.com',
        username: 'test_carder_username',
        password: 'test_carder_password'
    )

### Thorough ###

    def request = new MaxmindRequest(
        licenseKey: 'LICENSE_KEY',
        client_ip: '24.24.24.24',
        city: 'New York',
        region: 'NY',
        postal: '11434',
        country: 'US',
        domain: 'yahoo.com',
        bin: '549099',
        forwarded_ip: '24.24.24.25',
        email: 'test@test.com',
        username: 'test_carder_username',
        password: 'test_carder_password',
        bin_name: 'MBNA America Bank',
        bin_phone: '800-421-2110',
        cust_phone: '212-242',
        requested_type: 'premium',
        shipping_address: '145-50 157th Street',
        shipping_city: 'Jamaica',
        shipping_region: 'NY',
        shipping_postal: '11434',
        shipping_country: 'US',
        transaction_id: '1234',
        session_id: 'abcd9876',
        user_agent: 'Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_5; en-us) AppleWebKit/525.18 (KHTML, like Gecko) Version/3.1.2 Safari/525.20.1',
        accept_language: 'en-us')
    )

Also see examples/example.rb


Reference
---------
[minFraud API Reference](http://www.maxmind.com/app/ccv)

Props
-----
Plugin code extracted from [https://github.com/hackedunit/maxmind](https://github.com/hackedunit/maxmind)
