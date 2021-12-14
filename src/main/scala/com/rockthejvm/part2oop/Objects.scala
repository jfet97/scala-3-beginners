package com.rockthejvm.part2oop

object Objects {

  // define type + the only instance of this type in one line
  object MySingleton {
    // we can define methods and fields

    val aField = 45
    def aMethod(x: Int): Int = x + 1
  }

  val theSingleton = MySingleton
  val sameSingleton = MySingleton
  val isSameSingleton = theSingleton == sameSingleton

  val theSingletonField = MySingleton.aField
  val theSingletonMethodCall = MySingleton.aMethod(99)

  // companions = class + object with the same name in the same file
  class Person(name: String) {
    def sayHi() : String = s"Hi, my name is $name"
  }

  // the companion object of the class Person (to declare static stuff, instance-independent functionality)
  object Person {
    // can access each other's private fields and methods
    val N_EYES = 2 // (constant are capitalized)
    def canFly(): Boolean = false
  }

  val mary = new Person("Mary")
  val mary_v2 = new Person("Mary")
  val marysGreeting = mary.sayHi()
  val humansCanFly = Person.canFly()
  val nEyesHuman = Person.N_EYES


  // equality
  // 1 - equality of reference: exact same instance in memory, usually defined as == but not in Scala
  val sameMary = mary eq mary_v2 // false, different instances
  val sameSingleton_v2 = MySingleton eq MySingleton // true
  // 2 - equality of "sameness", it is up to us, defined .equals in Java
  val sameMary_v2 = mary equals mary_v2 // false
  val sameMary_v3 = mary == mary_v2 // same as equals, false
  val sameSingleton_v3 = MySingleton == MySingleton // true

  // if equals is not overrided,  2 uses reference equality as well


  // objects can extend classes and traits
  object BigFoot extends Person ("Big Foot")

  // Scala application = object + def main(args: Array[String]): Unit - it is the entry point of the application

  def main(args: Array[String]): Unit = {
    println(isSameSingleton)
  }
}
