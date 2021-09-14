package br.com.mvm.gzip.service

import java.io.IOException
import java.io.InputStream
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream


class DecompressServletInputStream : ServletInputStream {

    private var inputStream: InputStream? = null

    constructor(input:InputStream){
        this.inputStream = input
    }

    @Throws(IOException::class)
    override fun read(): Int {
        return inputStream!!.read()
    }

    override fun isFinished(): Boolean {
        return isFinished
    }

    override fun isReady(): Boolean {
        return isReady
    }

    override fun setReadListener(listener: ReadListener?) {}
}