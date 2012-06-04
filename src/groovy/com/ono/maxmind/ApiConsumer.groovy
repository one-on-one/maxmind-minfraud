package com.ono.maxmind

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovyx.net.http.Method

/**
 * Created by Eric Berry (cavneb@gmail.com)
 */

class ApiConsumer {

    static ApiConsumerResponse postText(String baseUrl, String path, query, method = Method.POST) {

        ApiConsumerResponse apiConsumerResponse = new ApiConsumerResponse(
                baseUrl: baseUrl,
                path: path,
                requestText: query.toString(),
                method: method.toString())

        try {
            def http = new HTTPBuilder(baseUrl)

            // perform a POST request, expecting TEXT response
            http.request(method, ContentType.TEXT) {
                uri.path = path
                uri.query = query
                headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'

                // response handler for a success response code
                response.success = { resp, reader ->
                    apiConsumerResponse.responseStatus = resp.statusLine
                    apiConsumerResponse.responseCode = resp.status
                    apiConsumerResponse.responseText = reader.getText()
                    apiConsumerResponse.hasErrors = false
                    apiConsumerResponse.responseContentType = resp.headers['Content-Type']
                }

                response.failure = { resp, reader ->
                    apiConsumerResponse.responseStatus = resp.statusLine
                    apiConsumerResponse.responseCode = resp.status
                    apiConsumerResponse.responseText = reader.getText()
                    apiConsumerResponse.hasErrors = true
                    apiConsumerResponse.responseContentType = resp.headers['Content-Type']
                }
            }
        } catch (Exception ex) {
            apiConsumerResponse.hasErrors = true
            apiConsumerResponse.errorMessage = ex.message
            apiConsumerResponse.exception = ex
            ex.printStackTrace()
        }

        return apiConsumerResponse
    }

    static def getText(String baseUrl, String path, query) {
        return postText(baseUrl, path, query, Method.GET)
    }

}
