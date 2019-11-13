package dk.cphbusiness.coroutines

import kotlinx.coroutines.*
import javax.xml.bind.JAXBElement
import kotlin.concurrent.thread
import kotlin.random.Random

fun doWorkWithThreads(limit: Int, step: Int) {
  for (i in 1..limit) thread {
    Thread.sleep(100)
    if (i%step == 0) println("T$i ")
    }
  }

fun doWorkWithCoroutines(limit: Int, step: Int) {
  runBlocking {
    for (i in 1..limit) {
      launch {
        delay(100)
        if (i % step == 0) println("C$i ")
        }
      }
    }
  }

suspend fun writeLine(text: String) = withContext(Dispatchers.Default) {
  println("$text on ${Thread.currentThread().name}")
  }
/*
class FizzBuzz : Iterable<String> {
  override fun iterator(): Iterator<String> = iterator {
    (1..1000).map { if (it%3 == 0) ("fizz" to it) else ("" to it) }
    }
  }
 */

fun main() {
  runBlocking {
    for (i in 1..3) launch {
      println("#$i")
      for (j in 1..100) {
        //(1..1_000_000).forEach { it*2 }
        Thread.sleep(50)
        //delay(50)
        writeLine("W$i line $j")
        }
      }
    }
//  val cTime = System.currentTimeMillis()
//  doWorkWithCoroutines(1_000_000, 100_000)
//  val tTime = System.currentTimeMillis()
//  println("${(tTime - cTime)/1000} seconds")
//  doWorkWithThreads(1_000_000, 100_000)
//  val eTime = System.currentTimeMillis()
//  println("${(eTime - tTime)/1000} seconds")
  }

/*
class NumberPrinter(private val number: Int) : Runnable {
  override fun run() {
    Thread.sleep(100)
    println("T"+number)
    }
  }

fun main() {
  for (i in 1..100) {
    Thread(NumberPrinter(i)).start()
    }
  }
 */