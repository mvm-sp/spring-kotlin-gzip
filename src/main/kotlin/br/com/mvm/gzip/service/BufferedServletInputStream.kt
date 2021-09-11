package br.com.mvm.gzip.service

import java.io.ByteArrayInputStream
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream


class BufferedServletInputStream : ServletInputStream {
    private var bais: ByteArrayInputStream? = null

    constructor(bais: ByteArrayInputStream?) {
        this.bais = bais
    }

    override fun available(): Int {
        return bais!!.available()
    }

    override fun isFinished(): Boolean {
        return true
    }

    override fun isReady(): Boolean {
        return true
    }

    override fun setReadListener(listener: ReadListener?) {}

    override fun read(): Int {
        return bais!!.read()
    }

    override fun read(buf: ByteArray?, off: Int, len: Int): Int {
        return bais!!.read(buf, off, len)
    }

}