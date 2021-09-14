package br.com.mvm.gzip.config

import org.springframework.stereotype.Component
import java.io.IOException
import java.io.InputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import javax.ws.rs.WebApplicationException
import javax.ws.rs.ext.*


@Component
class GZIPInterceptor : ReaderInterceptor, WriterInterceptor {

    @Throws(IOException::class, WebApplicationException::class)
    override fun aroundWriteTo(ctx: WriterInterceptorContext) {
        val os = GZIPOutputStream(ctx.outputStream)
        ctx.headers.putSingle("Content-Encoding", "gzip")
        ctx.outputStream = os
        ctx.proceed()
        return
    }

    @Throws(IOException::class)
    override fun aroundReadFrom(ctx: ReaderInterceptorContext): Any? {
        val encoding = ctx.headers.getFirst("Content-Encoding")
        if (!"gzip".equals(encoding, ignoreCase = true)) {
            return ctx.proceed()
        }
        val inputStream : InputStream = GZIPInputStream(ctx.inputStream)
        ctx.inputStream = inputStream
        return ctx.proceed()
    }


}