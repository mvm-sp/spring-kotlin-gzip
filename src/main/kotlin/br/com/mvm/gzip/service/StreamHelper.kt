package br.com.mvm.gzip.service

import java.io.IOException
import java.io.InputStream
import java.io.PushbackInputStream
import java.util.zip.GZIPInputStream


class StreamHelper {

    /**
     * Gzip magic number, fixed values in the beginning to identify the gzip
     * format <br></br>
     * http://www.gzip.org/zlib/rfc-gzip.html#file-format
     */
    private val GZIP_ID1: Byte = 0x1f

    /**
     * Gzip magic number, fixed values in the beginning to identify the gzip
     * format <br></br>
     * http://www.gzip.org/zlib/rfc-gzip.html#file-format
     */
    private val GZIP_ID2 = 0x8b.toByte()

    /**
     * Return decompression input stream if needed.
     *
     * @param input
     * original stream
     * @return decompression stream
     * @throws IOException
     * exception while reading the input
     */

    @Throws(IOException::class)
    fun decompressStream(input: InputStream?): InputStream? {
        val pushbackInput = PushbackInputStream(input, 2)
        val signature = ByteArray(2)
        pushbackInput.read(signature)
        pushbackInput.unread(signature)
        return if (signature[0] == GZIP_ID1 && signature[1] == GZIP_ID2) {
            GZIPInputStream(pushbackInput)
        } else pushbackInput
    }

}