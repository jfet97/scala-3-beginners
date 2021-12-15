package com.rockthejvm.part2oop

object AnonymousClasses {

  abstract class Animal {
    def eat(): Unit
  }

  class SomeAnimal extends Animal {
    override def eat(): Unit = println("I'm a weird animal")
  }

  val someAnimal = new SomeAnimal

  // for short-lived classes

  // anonymous class, the compiler create something like
  /*
    class AnonymousClasses.AnonClass$1 extends Animal {
      override def ..
    }
  */
  // then it instantiates it
  val someAnimal_v2 = new Animal {
    override def eat(): Unit = println("I'm a weird animal")
  }

  // can be used with classes, abstract classes and traits


  def main(args: Array[String]): Unit = {

  }



}
