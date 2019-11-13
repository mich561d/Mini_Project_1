package dk.cphbusiness.coroutines.path

class Path<T>(val first: T, val rest: Path<T>?) : Iterable<T> {
  /*
  override fun iterator(): Iterator<T> = iterator {
    yield(first)
    if (rest != null) yieldAll(rest)
    }
  */

  override fun iterator() = Steps(this)

  /**/
  class Steps<T>(private var rest: Path<T>?) : Iterator<T> {

    override fun hasNext() = rest != null

    override fun next(): T {
      val next = rest!!.first // throws null pointer exception if list is empty
      rest = rest?.rest
      return next
      }

    // override fun next() = rest!!.first.also { rest = rest?.rest }

    }
  /**/

  fun print() {
    println(first)
    rest?.print()
    }

  }

fun <T> pathOf(vararg steps: T): Path<T>? =
    steps.foldRight(null) { step: T, acc: Path<T>? -> Path<T>(step, acc) }

fun main() {
  // val names = Path("Anders", Path("Tobias", Path("Ib", null)))
  val names = pathOf("Anders", "Tobias", "Ib")
  val ages = pathOf(7, 9, 13)
  ages?.forEach { println(it) }
  names?.forEach { println(it) }
  // for (name in names) println(name)
  names?.print()
  }

