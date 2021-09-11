package br.com.mvm.gzip

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKotlinGzipApplication

fun main(args: Array<String>) {
	runApplication<SpringKotlinGzipApplication>(*args)
}
