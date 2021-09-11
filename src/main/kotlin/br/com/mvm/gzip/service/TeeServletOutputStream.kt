package br.com.mvm.gzip.service

import org.apache.commons.io.output.TeeOutputStream
import java.io.IOException
import java.io.OutputStream
import javax.servlet.ServletOutputStream
import javax.servlet.WriteListener


class TeeServletOutputStream : ServletOutputStream {

    private var targetStream: TeeOutputStream? = null

    constructor(first: OutputStream, second : OutputStream){
        targetStream = TeeOutputStream(first,second)
    }

    @Throws(IOException::class)
    override fun write(buf: Int) {
        targetStream!!.write(buf)
    }

    override fun isReady(): Boolean {
        return true
    }

    override fun setWriteListener(listener: WriteListener?) {}

    @Throws(IOException::class)
    override fun flush() {
        super.flush()
        targetStream!!.flush()
    }

    @Throws(IOException::class)
    override fun close() {
        super.close()
        targetStream!!.close()
    }
}