package com.rockthejvm.part2oop

object OOBasics {

  // takes args (constructor arguments) like a function, but they don't become fields of the
  // Person class unless they are preceeded by the `val` keyword
  class Person(val name: String, age: Int) {
    // class body, we can write code, add fields and methods

    // fields
    val allCaps = name.toUpperCase()

    // methods
    def greet(name: String): String =
      s"${this.name} says: Hi, $name"

    // OVERLOADING
    def greet(): String = // the signature differs (different args and/or args' types)
      s"Hi, everyone, my name is $name" // here name refers to this.name

    // aux constructors (you can only call another constructor)
    def this(name: String) =
      this(name, 0)

    def this() =
      this("Jane Doe")

  }

  val aPerson: Person = new Person("John", 26)
  val john = aPerson.name
  val jojnUppercased = aPerson.allCaps
  //  aPerson.age X `age` is not a field

  /**
  Exercise: imagine we're creating a backend for a book publishing house.
  Create a Novel and a Writer class.
  Writer: first name, surname, year
    - method fullname
  Novel: name, year of release, author
    - authorAge
    - isWrittenBy(author)
    - copy (new year of release) = new instance of Novel
   */

  class Writer(val firstName: String, val surname: String, val year: Int) {
    def fullname: String = s"$firstName $surname"
  }

  class Novel(val name: String, val yearOfRelease: Int, val author: Writer) {
    def authorAge: Int = this.yearOfRelease - author.year
    def isWrittenBy(author: Writer): Boolean = author == this.author // checked by reference
    def copy(newYearOfRelease: Int): Novel = new Novel(this.name, newYearOfRelease, this.author)
  }

  /**
   * Exercise #2: an immutable counter class
   * - constructed with an initial count
   * - increment/decrement => NEW instance of counter
   * - increment(n)/decrement(n) => NEW instance of counter
   * - print()
   *
   * Benefits:
   * + well in distributed environments
   * + easier to read and understand code
   */


  class Counter(val c: Int = 0) {

    // increment methods
    def increment(n: Int): Counter = {
      val inc = if(this.c + n >= 0) this.c + n else 0
      new Counter(inc)
    }

    def increment(): Counter =
      increment(1)

    // decrement methods
    def decrement(n: Int): Counter =
      increment(-n)

    def decrement(): Counter =
      increment(-1)

    def print(): Counter = {
      println(s"Current count: $c")
      this
    }
  }


  def main(args: Array[String]): Unit = {

    println(aPerson.greet("Andrea"))
    println(aPerson.greet())

    val charlesDickens = new Writer("Charles", "Dickerns", 1812)
    val charlesDickensCloned = new Writer("Charles", "Dickerns", 1812)
    val charlesDickensImpostor = new Writer("Charles", "Dickerns", 1812)
    val novel = new Novel("Great Expectations", 1861, charlesDickens)

    println(novel.isWrittenBy(charlesDickens))
    println(novel.isWrittenBy(charlesDickensCloned))
    println(novel.isWrittenBy(charlesDickensImpostor))


    val counter = new Counter()
    counter.print() // 0
    counter.increment().print().increment().print() // 1, then 2
    counter.increment() // always returns new instances
    counter.print() // 0 because a counter is immutable

    counter.increment(10).print() // 10
    counter.increment(20000).print() // 20000

  }


}
