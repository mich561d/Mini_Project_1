package dk.cphbusiness.coroutines.server

import java.net.ServerSocket
import kotlin.concurrent.thread

class Server(val port: Int = 4711) {
  var running = true

  fun handle(request: Request, response: Response) {
    println(request.resource)
    // TODO("Implement!")
    }

  fun start() {
    val serverSocket = ServerSocket(port)
    while (running) {
      val socket = serverSocket.accept()
      thread {
        handle(Request(socket.getInputStream()), Response(socket.getOutputStream()))
        }
      }
    }

  }

fun main() {
  println("Starting server")
  Server().start()
  }