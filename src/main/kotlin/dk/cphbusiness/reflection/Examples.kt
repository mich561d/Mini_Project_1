package dk.cphbusiness.reflection

import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties

class Dog {
    fun sayHello(greeter: String) = "Vov"
    }
class Person(val id: Int, var firstName: String, var lastName: String, var age: Int) {
    fun sayHello(greeter: String) = "Hello $firstName from $greeter"
    fun sayHello(greeter: String, count: Int) = "Hello $firstName a $count times from $greeter"
    }
class Stone {
    fun dontSayHello() = "Shh..."
    }

class Address(val street: String, val city: String)


fun main() {
    val kurt = Person(7, "Kurt", "Hansen", 27) //, Address("Byvej 7", "3600 Frederikssund"))
    val rufus = Dog()
    val stone = Stone()
    /*
    val type = kurt::class

    println(type)
    for (member in type.members) {
        println(member)
        }
    println("--------------")
    type.members.forEach { println(it.name) }
    println("--------------")
    println(kurt.sayHello("Sonja"))
    println(kurt.sayHello("Sonja", 1000))
    */
    sayHelloIfPossible(kurt)
    println("=======")
    sayHelloIfPossible(rufus)
    println("=======")
    sayHelloIfPossible(stone)
    println()
    println(toJson1_5(kurt))

    printMembers(kurt)

    }


fun printMembers(what: Any) {
    what::class.java.methods.forEach { println(it) }
    what::class.members.forEach { println(it) }
    }

fun sayHelloIfPossible(some: Any) {
    val type = some::class
    println(type.simpleName)
    val saying = type.memberFunctions
        .filter { it.name == "sayHello" && it.parameters.size == 2 }
        .firstOrNull()
    if (saying == null) {
        println("Sorry no message")
        return
        }
    println(saying.call(some, "Sonja"))
    }

