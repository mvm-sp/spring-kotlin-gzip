package br.com.mvm.gzip.service

import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets.UTF_8
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream


class CompressHandler {

    fun gzip(content: String): ByteArray {
        val bos = ByteArrayOutputStream()
        GZIPOutputStream(bos).bufferedWriter(UTF_8).use { it.write(content) }
        return bos.toByteArray()
    }

    fun ungzip(content: ByteArray): String =
        GZIPInputStream(content.inputStream()).bufferedReader(UTF_8).use { it.readText() }

}