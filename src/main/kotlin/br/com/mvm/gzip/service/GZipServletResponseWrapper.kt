package br.com.mvm.gzip.service

import java.io.IOException
import java.io.OutputStreamWriter
import java.io.PrintWriter
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponseWrapper


class GZipServletResponseWrapper: HttpServletResponseWrapper {

    private var gzipOutputStream: GZipServletOutputStream? = null
    private var printWriter: PrintWriter? = null

    @Throws(IOException::class)
    constructor( response: HttpServletResponse) : super(response){}

    @Throws(IOException::class)
    fun close() {


            printWriter?.close()

            gzipOutputStream?.close()

    }


    /**
     * Flush OutputStream or PrintWriter
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    override fun flushBuffer() {

        //PrintWriter.flush() does not throw exception

            printWriter?.flush()

        var exception1: IOException? = null
        try {
            gzipOutputStream?.flush()
        } catch (e: IOException) {
            exception1 = e
        }
        var exception2: IOException? = null
        try {
            super.flushBuffer()
        } catch (e: IOException) {
            exception2 = e
        }
        if (exception1 != null) throw exception1
        if (exception2 != null) throw exception2
    }

    @Throws(IOException::class)
    override fun getOutputStream(): ServletOutputStream? {
        check(printWriter == null) { "PrintWriter obtained already - cannot get OutputStream" }
        if (gzipOutputStream == null) {
            gzipOutputStream = GZipServletOutputStream(
                response.outputStream
            )
        }
        return gzipOutputStream
    }

    @Throws(IOException::class)
    override fun getWriter(): PrintWriter? {
        check(!(printWriter == null && gzipOutputStream != null)) { "OutputStream obtained already - cannot get PrintWriter" }
        if (printWriter == null) {
            gzipOutputStream = GZipServletOutputStream(
                response.outputStream
            )
            printWriter = PrintWriter(
                OutputStreamWriter(
                    gzipOutputStream, response.characterEncoding
                )
            )
        }
        return printWriter
    }


    override fun setContentLength(len: Int) {
        //ignore, since content length of zipped content
        //does not match content length of unzipped content.
    }
}