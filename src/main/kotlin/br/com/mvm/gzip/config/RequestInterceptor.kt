package br.com.mvm.gzip.config

import br.com.mvm.gzip.service.CompressHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class RequestInterceptor: HandlerInterceptor {

    val log = LoggerFactory.getLogger(RequestInterceptor::class.java)
    val zipHandler = CompressHandler()


    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, dataObject: Any) : Boolean{
        log.info("1. PreHandle method.")
       // var body : String? = zipHandler.unZipStream(request.inputStream)
        return true
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, dataObject: Any, model: ModelAndView?){
        log.info("2. PostHandle method.")
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, dataObject: Any, e: Exception?) {
        log.info("3. AfterCompletion method")
    }

}
