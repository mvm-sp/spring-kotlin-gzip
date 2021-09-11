package br.com.mvm.gzip.service

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets.UTF_8
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import javax.servlet.http.HttpServletRequest


class CompressHandler {

    fun gzip(content: String): ByteArray {
        val bos = ByteArrayOutputStream()
        GZIPOutputStream(bos).bufferedWriter(UTF_8).use { it.write(content) }
        return bos.toByteArray()
    }

    fun unGzipByteArray(content: ByteArray): String =
        GZIPInputStream(content.inputStream()).bufferedReader(UTF_8).use { it.readText() }

    // copied from okhttp3.internal.http.HttpEngine (because is private)
    @Throws(IOException::class)
    fun unZipStream(inputStream: InputStream): String? {
        val bodyStream: InputStream = GZIPInputStream(inputStream)
        val outStream = ByteArrayOutputStream()
        val buffer = ByteArray(4096)
        var length: Int
        while (bodyStream.read(buffer).also { length = it } > 0) {
            outStream.write(buffer, 0, length)
        }

        return String(outStream.toByteArray())

    }
}