package dk.cphbusiness.coroutines.builders

import javafx.application.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep
import kotlin.concurrent.thread

fun workWithThreads() {
    (1..1_000_000).forEach {
        thread {
            sleep(100)
            if (it%100_000 == 0) println("It was $it")
            }
        }
    }

fun workWithCoroutines() {
    runBlocking {
        (1..1_000_000).forEach { number ->
            launch {
                delay(100)
                if (number%100_000 == 0) println("It was $number")
                }
            }
        }
    }

fun CoroutineScope.workMore() {
    (1..1_000_000).forEach { number ->
        launch {
            delay(100)
            if (number%100_000 == 0) println("It was $number")
            }
        }
    }

fun main() {
    println("Trying coroutines")
    workWithCoroutines()
    println("Done with coroutines")
    println("Threads")
    workWithThreads()
    println("Done with threads")
    runBlocking { workMore() }
    }