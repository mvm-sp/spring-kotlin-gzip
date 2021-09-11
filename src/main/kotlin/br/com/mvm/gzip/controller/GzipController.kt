package br.com.mvm.gzip.controller

import br.com.mvm.gzip.service.CompressHandler
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RequestMapping("/gzip")
@RestController
class GzipController {

    /*
    curl -i -X POST -H "Content-Type: multipart/form-data" -F "data=@file.txt" -F "userid=local" http://localhost:8080/gzip

    curl -i -X POST -H "Content-Type: application/json" -F "data=@teste.json" -F "userid=local" http://localhost:8080/gzip

    curl -v -H "Content-encoding: gzip" -X POST  -H 'Content-Type: application/json;charset=UTF-8' --data-binary  @teste.gz http://localhost:8080/gzip

    gzip -c teste.json > teste.gz

    */

    //val log = LoggerFactory.getLogger(GzipController::class.java)

    @PostMapping
    fun postGzip(@RequestBody body: String): String {
        //var handler = CompressHandler()
        //var mBody : String = handler.ungzip(body.toByteArray())
        return "body OK"
    }

}