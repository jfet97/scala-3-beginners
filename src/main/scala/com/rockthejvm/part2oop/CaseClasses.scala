package com.rockthejvm.part2oop

object CaseClasses {

  // lightweight data structures (plain data)
  case class Person(name: String, age: Int) {
    // fields and methods
  }

  // 1 - class constructor's args are now automatically fields (without the need of prefixing them with `val`)
  val andrea = new Person("Andrea", 24)
  val andreaAge = andrea.age

  // 2 - toString, equals and hashCode (useful for collections as sets and maps) are automatically overriden
  val andreaToString = andrea.toString // Person(Andrea,24)
  val andreaDuped = new Person("Andrea", 24)
  val isSameAndrea = andrea == andreaDuped // calls equals, is true

  // 3 - utility methods
  val andreaYounger = andrea.copy(age = 20) // uses andrea fields as default values

  // 4 - the companion object is automatically generated
  val thePersonSingleton = Person
  val andrea_v2 = Person("Andrea", 24) // "constructor" because the apply method is automatically generated to create Person instances

  // 5 - serialization
  // use-case: Akka

  // 6 - case classes have extractor patterns for PATTERN MATCHING

  // case class CCWithNoARgs X not allowed
  // because the equals method uses the values of the arguments, so no arguments means no difference => all instances are equal => no sense

  case object UnitedKingdom {
    // fields and methods
    def name: String = "The UK of GB and NI"
  }
  // has properties 2 and 5


  case class CCWithArgListNoArgs() // legal, without so much sense
  // useful for generics, i.e. the Empty list
  val singletonInstance = CCWithArgListNoArgs()

  // abstract classes cannot be case classes

  def main(args: Array[String]): Unit = {
    println(andrea)
    println(isSameAndrea)
  }
}
