package br.com.mvm.gzip.config

import br.com.mvm.gzip.service.BufferRequestWrapper
import br.com.mvm.gzip.service.BufferedResponseWrapper
import br.com.mvm.gzip.service.CompressHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class RequestFilter : Filter {

    val log = LoggerFactory.getLogger(RequestInterceptor::class.java);
    val zipHandler = CompressHandler()


    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig?) {
        log.info("1. init method.")
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        log.info("2. doFilter method.")

        val zipHandler = CompressHandler()

        val requestMap = getTypeSafeRequestMap(request   as HttpServletRequest)

        val body : String


        val bufferedRequest = BufferRequestWrapper(request)

        val bufferedResponse = BufferedResponseWrapper(response as HttpServletResponse)

        var logMessage = StringBuilder("REST Request - ")
            .append("HTTP METHOD:").appendLine(request.getMethod())
            .append("PATH INFO:").appendLine(request.getPathInfo())
            .append("REQUEST MAP:").appendLine(requestMap)
            .append("REQUEST BODY:").appendLine(bufferedRequest.getRequestBody())
            .append("REMOTE ADDRESS:").appendLine(request.getRemoteAddr())

        log.info(logMessage.toString())

        if (chain != null) {
            chain.doFilter(bufferedRequest, bufferedResponse)
        }

        if (requestMap != null) {
            if(requestMap.isEmpty()){
                body = bufferedRequest.getRequestBody().toString()
            }else{
                body =  "TODO" //zipHandler.unZipStream(request.inputStream).toString()
            }
        }

    }


    override fun destroy() {
        log.info("3. destroy method.")
    }

    fun getTypeSafeRequestMap(request: HttpServletRequest): Map<String, String>? {
        val typesafeRequestMap: MutableMap<String, String> = HashMap()
        val requestParamNames: Enumeration<*> = request.parameterNames
        while (requestParamNames.hasMoreElements()) {
            val requestParamName = requestParamNames.nextElement() as String
            val requestParamValue = request.getParameter(requestParamName)
            typesafeRequestMap[requestParamName] = requestParamValue
        }
        return typesafeRequestMap
    }

}