package com.rockthejvm.part2oop
import scala.language.postfixOps

object MethodNotation {

  class Person(val name: String, val age: Int, favoriteMovie: String) {

    // infix
    infix def likes(movie: String): Boolean =
      movie == favoriteMovie

    infix def +(person: Person): String =
      s"${this.name} is hanging out with ${person.name}"

    infix def +(nickname: String): Person =
      new Person(s"$name $nickname", age, favoriteMovie)

    infix def !!(progLanguage: String): String =
      s"$name wonders how can $progLanguage be so cool!"

    // prefix (the space before the : is important), no args
    // unary ops supported: -, +, ~, !
    def unary_- : String =
      s"$name's alther ego"

    def unary_+ : Person =
      new Person(name, age + 1, favoriteMovie)

    // postfix notation, do not takes args nor parenthesis (heavily discouraged)
    def isAlive: Boolean = true

    // apply
    def apply(): String =
      s"Hi, my name is $name and I really enjoy $favoriteMovie"

    def apply(n: Int): String =
      s"$name watched $favoriteMovie $n time${if (n != 1) "s" else ""}"
  }

  val mary = new Person("Mary", 34, "Inception")
  val john = new Person("John", 36, "Fight Club")

  /**
   *  Exercises
   *  - a + operator on the Person class that returns a person with a nickname
   *    mary + "the rockstar" => new Person("Mary the rockstar", _, _)
   *  - a UNARY + operator that increases the person's age
   *    +mary => new Person(_, _, age + 1)
   *  - an apply method with an int arg
   *    mary.apply(2) => "Mary watched Inception 2 times"
   */

  def main(args: Array[String]): Unit = {
    println(mary.likes("Fight Club"))
    println(mary likes "Fight Club") // infix notation, only for methods with ONE argument

    println(mary.+(john))
    println(mary + john)

    println(2 + 3)
    println(2.+(3))

    println(mary !! "Scala")
    println(mary.!!("Scala"))

    println(-mary) // prefix
    println(mary.unary_-)

    println(mary isAlive) // postfix
    println(mary.isAlive)

    // apply is special
    println(mary.apply())
    println(mary())



    // exercises
    val maryWithNickname = mary + "the rockstar"
    println(maryWithNickname.name)

    val maryOlder = +mary
    println(maryOlder.age) // 35

    println(mary(10))
    println(mary(1))


  }
}
