package br.com.mvm.gzip.service

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.PrintWriter
import java.util.*
import javax.servlet.ServletOutputStream
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse


class BufferedResponseWrapper : HttpServletResponse {

    var original: HttpServletResponse? = null
    var tee: TeeServletOutputStream? = null
    var bos: ByteArrayOutputStream? = null

    constructor(res : HttpServletResponse){
        original = res
    }

    fun getContent(): String? {
        return bos.toString()
    }

    @Throws(IOException::class)
    override fun getWriter(): PrintWriter? {
        return original!!.writer
    }

    @Throws(IOException::class)
    override fun getOutputStream(): ServletOutputStream? {
        if (tee == null) {
            bos = ByteArrayOutputStream()
            tee = TeeServletOutputStream(
                original!!.outputStream,
                bos!!
            )
        }
        return tee
    }

    override fun getCharacterEncoding(): String? {
        return original!!.characterEncoding
    }

    override fun getContentType(): String? {
        return original!!.contentType
    }

    override fun setCharacterEncoding(charset: String?) {
        original!!.characterEncoding = charset
    }

    override fun setContentLength(len: Int) {
        original!!.setContentLength(len)
    }

    override fun setContentLengthLong(len: Long) {
        original!!.setContentLengthLong(len)
    }

    override fun setContentType(type: String?) {
        original!!.contentType = type
    }

    override fun setBufferSize(size: Int) {
        original!!.bufferSize = size
    }

    override fun getBufferSize(): Int {
        return original!!.bufferSize
    }

    @Throws(IOException::class)
    override fun flushBuffer() {
        tee?.flush()
    }

    override fun resetBuffer() {
        original!!.resetBuffer()
    }

    override fun isCommitted(): Boolean {
        return original!!.isCommitted
    }

    override fun reset() {
        original!!.reset()
    }

    override fun setLocale(loc: Locale?) {
        original!!.locale = loc
    }

    override fun getLocale(): Locale? {
        return original!!.locale
    }

    override fun addCookie(cookie: Cookie?) {
        original!!.addCookie(cookie)
    }

    override fun containsHeader(name: String?): Boolean {
        return original!!.containsHeader(name)
    }

    override fun encodeURL(url: String?): String? {
        return original!!.encodeURL(url)
    }

    override fun encodeRedirectURL(url: String?): String? {
        return original!!.encodeRedirectURL(url)
    }

    override fun encodeUrl(url: String?): String? {
        return original!!.encodeUrl(url)
    }

    override fun encodeRedirectUrl(url: String?): String? {
        return original!!.encodeRedirectUrl(url)
    }

    @Throws(IOException::class)
    override fun sendError(sc: Int, msg: String?) {
        original!!.sendError(sc, msg)
    }

    @Throws(IOException::class)
    override fun sendError(sc: Int) {
        original!!.sendError(sc)
    }

    @Throws(IOException::class)
    override fun sendRedirect(location: String?) {
        original!!.sendRedirect(location)
    }

    override fun setDateHeader(name: String?, date: Long) {
        original!!.setDateHeader(name, date)
    }

    override fun addDateHeader(name: String?, date: Long) {
        original!!.addDateHeader(name, date)
    }

    override fun setHeader(name: String?, value: String?) {
        original!!.setHeader(name, value)
    }

    override fun addHeader(name: String?, value: String?) {
        original!!.addHeader(name, value)
    }

    override fun setIntHeader(name: String?, value: Int) {
        original!!.setIntHeader(name, value)
    }

    override fun addIntHeader(name: String?, value: Int) {
        original!!.addIntHeader(name, value)
    }

    override fun setStatus(sc: Int) {
        original!!.status = sc
    }

    override fun setStatus(sc: Int, sm: String?) {
        original!!.setStatus(sc, sm)
    }

    override fun getHeader(arg0: String?): String? {
        return original!!.getHeader(arg0)
    }

    override fun getHeaderNames(): Collection<String?>? {
        return original!!.headerNames
    }

    override fun getHeaders(arg0: String?): Collection<String?>? {
        return original!!.getHeaders(arg0)
    }

    override fun getStatus(): Int {
        return original!!.status
    }

}