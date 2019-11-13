package dk.cphbusiness.coroutines.server

import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.lang.StringBuilder

enum class Method { GET, PUT, POST, DELETE }

class Request(input: InputStream) {
  val resource: String
  val method: Method
  init {
    val reader = input.bufferedReader()
    val line = reader.readLine()
    val parts = line.split(" ")
    resource = parts[1]
    method = Method.valueOf(parts[0])
    }
  }

class Response(private val output: OutputStream) {
  val body = StringBuilder()

  fun append(text: String) {
    body.append(text)
    }

  fun send() {
    val head = """
        HTTP/1.1 200 OK
        Content-Type: text/html; charset=UTF-8
        Content-Length: ${body.length}
        Connection: close
        
        """.trimIndent()
    val writer = output.bufferedWriter()
    writer.append(head)
    writer.newLine()
    writer.append(body)
    writer.close()
    }

  }
/*
fun main() {
  val output = ByteArrayOutputStream(1024)
  val writer = output.bufferedWriter()
  }
 */