package mini_p_1

import com.google.gson.Gson
import java.io.File
import java.net.ServerSocket
import java.net.SocketException
import kotlin.concurrent.thread

interface WebContent {
  fun load()
  fun save()
}

class WebServer(private val content: ChoirContent, private val port: Int) {
  private var running = true
  private val serverSocket = ServerSocket(port)

  private fun handle(request: Request, response: Response) {
    val method = request.method.toString()
    val resource = request.resource
    //println(resource)
    if (resource == "/exit") {
      stop()
      response.body.append("Exit...")
    } else {
      val body = request.body
      when (Method.valueOf(method)) {
        Method.GET -> {
          val split = resource.split("/")
          if (split[1].toLowerCase() == "member") {
            when (split.size) {
              2 -> {
                response.body.append(content.getMember())
              }
              3 -> {
                response.body.append(content.getMember(split[2].toInt()))
              }
              else -> {
                response.body.append("Invalid URL! fucker!")
              }
            }
          } else {
            response.body.append("Invalid URL! fucker!")
          }

        }
        Method.PUT -> {
          val split = resource.split("=")[1].split(":")
          val member = Member(split[1].toInt(), split[0].toString())
          content.putMember(member)
        }
        Method.POST -> {
          response.body.append("Not implemented!")
        }
        Method.DELETE -> {
          response.body.append("Not implemented!")
        }
        else -> throw Exception("This cannot happen!")
      }
    }
    response.send()

  }

  fun start() {
    println("Starting server at: localhost:$port")
    while (running) {
      try {
        val socket = serverSocket.accept()
        thread {
          handle(Request(socket.getInputStream()), Response(socket.getOutputStream()))
        }
      } catch (e: SocketException) {
        println("--------------- Socket Exception ---------------")
        println(e.message)
        println("------------------------------------------------")
      }

    }
  }

  private fun stop() {
    println("Stopping server at: localhost:$port")
    content.save()
    running = false
    serverSocket.close()
  }
}


data class Member(val id: Int, val name: String)

class ChoirContent : WebContent {
  private var members = mutableMapOf<Int, Member>()

  init {
    load()
  }

  fun getMember(): List<Member> = members.values.toList()

  fun getMember(id: Int): Member? = members[id]

  fun putMember(member: Member): Member {
     if( member == null ) {
      "You havent send any member..."
    }

    if( members.containsKey(member.id) ) {
      members.replace(member.id, member)
    }
    if( !members.containsKey(member.id)) {
      members[member.id] = member
      return member
    }
    return member
  }

  override fun load() {
    val fileURI = javaClass.getResource("/Members.json").toURI()
    val json = File(fileURI).readText()
    val jsonList = Gson().fromJson(json, Array<Member>::class.java).toList()
    jsonList.forEach { members[it.id] = it }
  }

  override fun save() {
    members = members.toSortedMap()
    println(members)
    val fileURI = javaClass.getResource("/Members.json").toURI()
    val json = Gson().toJson(members.toList().map { it.second })
    File(fileURI).writeText(json)
  }

}


fun main() {
  val server = WebServer(ChoirContent(), 4711)
  server.start()
}