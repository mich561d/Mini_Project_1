package dk.cphbusiness.lambdas

import java.io.File

fun main() {
    val list = listOf(7, 9, 13, 42, 80, 144, 117, 1001, 56, 90)
    list
        .map { println("First $it"); it }
        .filter { it%2 == 0 }
        .map { println("Second $it"); it }
        .take(3)
        .forEach { println(it) }
    println("-----")
    list.asSequence()
        .map { println("First $it"); it }
        .filter { it%2 == 0 }
        .map { println("Second $it"); it }
        .take(3)
        //.toList()
        .forEach { println(it) }
    }


