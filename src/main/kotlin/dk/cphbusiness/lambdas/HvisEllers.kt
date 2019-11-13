package dk.cphbusiness.lambdas

fun hvis(test: Boolean, block: () -> Unit): Boolean {
    if (test) block()
    return test
    }

infix fun Boolean.ellers(block: () -> Unit) {
    if (!this) block()
    }

fun main() {
    hvis (5 == 7) {
        println("Det var s'gu mærkeligt")
    } ellers {
        println("Pyha!")
        }
    println("DONE!")
    }