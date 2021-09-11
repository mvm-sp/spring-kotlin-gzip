package br.com.mvm.gzip.service

import java.io.*
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper


class BufferRequestWrapper : HttpServletRequestWrapper {

    private var arrInputStream: ByteArrayInputStream? = null
    private var arrOutputStream: ByteArrayOutputStream? = null
    private var bufferImputStream: BufferedServletInputStream? = null
    private var buffer: ByteArray? = null

    @Throws(IOException::class)
    constructor(req: HttpServletRequest) : super(req) {

        // Read InputStream and store its content in a buffer.
        val inputStream : InputStream = req.inputStream
        this.arrOutputStream = ByteArrayOutputStream()
        val buf = ByteArray(4096)
        var read: Int
        while (inputStream.read(buf).also { read = it } > 0) {
            arrOutputStream!!.write(buf, 0, read)
        }
        buffer = arrOutputStream!!.toByteArray()
    }

    override fun getInputStream(): ServletInputStream? {
        this.arrInputStream = ByteArrayInputStream(buffer)
        this.bufferImputStream = BufferedServletInputStream(this.arrInputStream)
        return this.bufferImputStream
    }

    @Throws(IOException::class)
    fun getRequestBody(): String? {
        val reader = BufferedReader(
            InputStreamReader(
                this.getInputStream()
            )
        )
        var line: String? = null
        val inputBuffer = StringBuilder()
        do {
            line = reader.readLine()
            if (null != line) {
                inputBuffer.append(line.trim { it <= ' ' })
            }
        } while (line != null)
        reader.close()
        return inputBuffer.toString().trim { it <= ' ' }
    }


}
