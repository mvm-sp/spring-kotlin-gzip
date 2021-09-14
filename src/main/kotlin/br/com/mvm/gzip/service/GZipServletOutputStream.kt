package br.com.mvm.gzip.service

import java.io.IOException
import java.io.OutputStream
import java.util.zip.GZIPOutputStream
import javax.servlet.ServletOutputStream
import javax.servlet.WriteListener


class GZipServletOutputStream: ServletOutputStream {

    private var gzipOutputStream: GZIPOutputStream? = null

    @Throws(IOException::class)
    constructor( output : OutputStream) : super(){
        gzipOutputStream =  GZIPOutputStream(output)
    }

    @Throws(IOException::class)
    override fun close() {
        gzipOutputStream!!.close()
    }

    @Throws(IOException::class)
    override fun flush() {
        gzipOutputStream!!.flush()
    }

    @Throws(IOException::class)
    override fun write(b: ByteArray?) {
        gzipOutputStream!!.write(b)
    }

    @Throws(IOException::class)
    override fun write(b: ByteArray?, off: Int, len: Int) {
        gzipOutputStream!!.write(b, off, len)
    }

    override fun isReady(): Boolean {return true}


    override fun setWriteListener(linstener: WriteListener?) {}

    @Throws(IOException::class)
    override fun write(b: Int) {
        gzipOutputStream!!.write(b)
    }
}