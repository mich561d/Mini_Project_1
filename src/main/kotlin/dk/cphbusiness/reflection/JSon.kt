package dk.cphbusiness.reflection

import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.full.memberProperties

fun toJson(what: Any) =
    what::class.memberProperties
        .map { """  "${it.name}": ${jsonValueOf(it.call(what))}""" }
        .joinToString(",\n", "{\n", "\n}\n")

fun toJson1_5(what: Any) =
    what::class.memberProperties
        .map { property -> """  "${property.name}": ${jsonValueOf(property.call(what))}""" }
        .joinToString(prefix = "{\n", separator = ",\n", postfix = "\n}\n")

fun toJson2(what: Any) : String {
    var json = ""
    val type = what::class
    for (property in type.memberProperties) {
        val name = property.name
        val value = property.call(what)
        if (json.isEmpty()) json = "{\n  \"$name\": ${jsonValueOf(value)}"
        else json += ",\n  \"$name\": ${jsonValueOf(value)}"
    }
    return json+"\n}\n"
}

fun jsonValueOf(value: Any?): String =
    when (value) {
        null -> "null"
        is Int -> value.toString()
        is Double -> value.toString()
        is String -> """"$value""""
        else -> toJson1_5(value)
    }

fun main() {
    val json = """
        {
          "age": 27,
          "firstName": "Kurt",
          "id": 7,
          "lastName": "Hansen"
        }
        """.trimIndent()

    val person = toInstance(Person::class, json)
    if (person is Person)
        println("${person.firstName} ${person.lastName} har id ${person.id} og er ${person.age} år gammel" )
    }

fun toInstance(type: KClass<*>, json: String): Any {
    val pairs = jsonToMap(json)

    type.memberProperties.forEach { println(it) }
    val initializer =  type.constructors.first()

    val paramaterValues = arrayOf<Any?>(null, null, null, null)
    for (parameter in initializer.parameters) {
        val name = parameter.name
        val index = parameter.index
        val type = parameter.type.classifier
        val text = pairs[name]
        if (type == null || text == null) continue
        val value = fromJsonValue(text, type)
        paramaterValues[index] = value
        }

    return initializer.call(*paramaterValues)
    }

fun fromJsonValue(text: String, type: KClassifier): Any? =
    when (type) {
        String::class -> text.substring(1, text.length - 1)
        Int::class -> text.toInt()
        Double::class -> text.toDouble()
        else -> null
        }

fun jsonToMap(json: String): MutableMap<String, String> {
    val lines = json.split("[,{}]".toRegex()).map { it.trim() }.filter { it.isNotBlank() }
    val propertyPairs = mutableMapOf<String, String>()
    for (line in lines) {
        val parts = line.split(":")
        var name = parts[0].trim()
        name = name.substring(1, name.length - 1)
        val text = parts[1].trim()
        propertyPairs[name] = text
    }
    return propertyPairs
    }
