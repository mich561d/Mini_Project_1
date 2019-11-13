package dk.cphbusiness.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.selects.select

fun mainx() = runBlocking<Unit> {
    val names = Channel<String>()
    val greetings = Channel<String>()
    greeter(7, names, greetings)
    greeter(17, names, greetings)
    greeter(23, names, greetings)
    println("Sending names...")
    launch {
        names.send("Ole")
        names.send("Gregers")
        names.send("Kurt")
        names.send("Sonja")
        names.send("Anders")
        names.send("Tobias")
        names.close()
        }
    println("Names send")
    while (!greetings.isClosedForReceive) {
        println(greetings.receive())
        }
    //greetings.close()
    }

fun CoroutineScope.greeter(
        id: Int,
        names: ReceiveChannel<String>,
        greetings: SendChannel<String>
        ) = launch {
    while (!names.isClosedForReceive) {
        select<Unit> {
            names.onReceive {
                delay(2000)
                greetings.send("$id> Hello $it")
                }
            }
        }
/*
    for (name in names) {
        delay(2000)
        println("$id> Hello $name")
        }
 */
    }
