package mini_p_1

fun main() {
  val content = ChoirContent(/* filename , ... */)
  val server = WebServer(content, 4711)
  server.start()
}

//fun mainBetter () {
//  ChoirContent (/* filename , ... */ ). publish (4711)
//}