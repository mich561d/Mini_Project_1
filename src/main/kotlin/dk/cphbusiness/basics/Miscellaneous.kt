package dk.cphbusiness.basics

class MySingleton private constructor ( ) {
    val databaseName = "DB"
    // object Xyz {
    companion object {
        val instance: MySingleton by lazy {
            println("Completely new instance")
            MySingleton()
            }
        }
    }

object MySmarterSingleton {
    val databaseName: String = "SmartDB"
    }

class Pet(val data: MutableMap<String, Any?>) {
    var name: String by data
    var age: Int by data
    var owner: String by data
    }

fun testSingleTons() {
    // val s = MySingleton.Xyz.instance
    val s = MySingleton.instance
    println(s.databaseName)
    println(s.databaseName)

    println(MySmarterSingleton.databaseName)
    }

fun String.repeat(count: Int): String {
    var t = ""
    var c = count
    while (c-- > 0) t += this
    return t
    }

infix fun String.pointsAt(item: Any?): Pair<String, Any?> {
    return Pair(this, item)
    }

fun main() {
    // testSingleTons()

    val otherData = HashMap<String, Any?>()
    otherData["name"] = "Ninus"
    otherData["age"] = 5
    otherData["owner"] = "Sonja"
    // val data = mutableMapOf<String, Any?>("name".pointsAt("Ninus"), "age" pointsAt 5)
    // data["owner"] = "Kurt".repeat(5)
    val data = mutableMapOf<String, Any?>("name" to "Ninus", "age" to 5, "owner".to("Kurt"))
    val pet = Pet(data)
    println("Dyrets navn er ${pet.name} og det er ${pet.age} gammelt")
    println("""
        Dyrets navn er ${pet.name}
        ------------------------------
        Alder: ${pet.age}
        Ejer:  ${pet.owner}
        """.trimIndent()
        )
    // pet.age += 1
    println(data)
    }