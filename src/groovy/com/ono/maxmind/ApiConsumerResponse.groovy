package com.ono.maxmind

/**
 * Created by Eric Berry (cavneb@gmail.com)
 */

class ApiConsumerResponse {
    String baseUrl
    String path
    String method
    String responseStatus
    String responseCode
    String responseContentType
    String requestText
    String responseText
    Boolean hasErrors = false
    String errorMessage
    Exception exception

    String toString() {
        String ret = """
-------------------------- API CONSUMER RESPONSE --------------------------
            baseUrl: ${baseUrl}
               path: ${path}
             method: ${method}
     responseStatus: ${responseStatus}
       responseCode: ${responseCode}
responseContentType: ${responseContentType}
        requestText: ${requestText}
       responseText: ${responseText}
          hasErrors: ${hasErrors}
       errorMessage: ${errorMessage}
          exception: ${exception}
"""
        return ret
    }
}
