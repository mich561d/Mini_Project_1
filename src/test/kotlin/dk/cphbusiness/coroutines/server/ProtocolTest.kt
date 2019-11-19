package dk.cphbusiness.coroutines.server

import mini_p_1.Method
import mini_p_1.Request
import mini_p_1.Response
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayOutputStream

class ProtocolTest {
  val requestText = """
      GET /greeter HTTP/1.1
      
      """.trimIndent()

  @Test
  fun testRequestResource() {
    val input = requestText.byteInputStream()
    val request = Request(input)
    assertEquals("/greeter", request.resource)
    }

  @Test
  fun testRequestMethod() {
    val input = requestText.byteInputStream()
    val request = Request(input)
    assertEquals(Method.GET, request.method)
    }

  val responseText = """
      HTTP/1.1 200 OK
      Content-Type: text/html; charset=UTF-8
      Content-Length: 18
      Connection: close
      
      <p>Hello Kurt!</p>
      """.trimIndent()

  @Test
  fun testSayHelloToKurt() {
    val output = ByteArrayOutputStream(1024)
    val response = Response(output)
    response.append("<p>Hello")
    response.append(" Kurt!</p>")
    response.send()
    println(output.toString())
    assertEquals(responseText, output.toString())
    }

  }

