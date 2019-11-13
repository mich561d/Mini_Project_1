package dk.cphbusiness.lambdas

fun d1(x: Int) = 4*x

val d2: (Int) -> Int = { x -> x*x }

fun printMapped(items: List<Int>, mapped: (Int) -> Int) {
    println("--------")
    for (item in items) println(mapped(item))
    }

fun List<Int>.mapped(mapped: (Int) -> Int) = printMapped(this, mapped)

fun List<Int>.map(mapped: (Int) -> Int): List<Int> {
    val result = mutableListOf<Int>()
    for (item in this) result.add(mapped(item))
    return result
    }

fun testMapped() {
    println(d1(5))
    println(d2(7))
    val items = listOf(7, 9, 13)
    printMapped(items, d2)
    printMapped(items, { value -> value/2 })
    printMapped(items, { it/2 })
    printMapped(items) { it/2 }

    items.mapped(d2)
    items.mapped({ 7*it })
    items.mapped() { 7*it }
    items.mapped { 7*it }

    printMapped(items, ::d1)

    }

fun main() {
    // testMapped()
    val items = listOf(7, 9, 13)
    val r1 = items
        .map( { item -> 3*item } )
        .map( { item -> item/2 } )

    val r2 = items
        .map( { 3*it } )
        .map( { it/2 } )

    val r3 = items
        .map() { 3*it }
        .map() { it/2 }

    val r4 = items
        .map { 3*it }
        .map { it/2 }
    println(r3)
    }