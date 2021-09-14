package br.com.mvm.gzip.config

import br.com.mvm.gzip.service.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
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
        var httpServletRequest = request
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

        val contentEncoding: String = request.getHeader("Content-Encoding")

        if (contentEncoding != null && contentEncoding.indexOf("gzip") > -1){

            var streamHelper = StreamHelper()
            try {
                val decompressStream: InputStream = streamHelper.decompressStream(httpServletRequest.getInputStream())!!
                httpServletRequest = object : HttpServletRequestWrapper(httpServletRequest as HttpServletRequest) {
                    @Throws(IOException::class)
                    override fun getInputStream(): ServletInputStream {
                        return DecompressServletInputStream(decompressStream)
                    }

                    @Throws(IOException::class)
                    override fun getReader(): BufferedReader {
                        return BufferedReader(InputStreamReader(decompressStream))
                    }
                }
            } catch (e: IOException) {
                log.error("error while handling the request", e)
            }

        }

        chain?.doFilter(httpServletRequest, response)

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

