package br.com.mvm.gzip.config

import br.com.mvm.gzip.service.GZipServletResponseWrapper
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class GZipServletFilter: Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        if (acceptsGZipEncoding(httpRequest)) {
            httpResponse.addHeader("Content-Encoding", "gzip")
            val gzipResponse = GZipServletResponseWrapper(httpResponse)
            chain?.doFilter(request, gzipResponse)
            gzipResponse?.close()
        } else {
            chain?.doFilter(request, response)
        }
    }

    private fun acceptsGZipEncoding(httpRequest: HttpServletRequest): Boolean {
        val acceptEncoding = httpRequest.getHeader("Accept-Encoding")
        return acceptEncoding != null &&
                acceptEncoding.indexOf("gzip") != -1
    }
}