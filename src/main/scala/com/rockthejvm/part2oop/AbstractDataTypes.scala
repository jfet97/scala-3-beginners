package com.rockthejvm.part2oop

object AbstractDataTypes {

  // cannot be instatiated
  abstract class Animal {
    // can have members or methods without implementation

    val creatureType: String // it is abstract
    def eat(): Unit // it is abstract

    // it is an "accessor method" (no args, no parenthesis)
    def preferredMeal: String = "anything" // not abstract
  }

  // val animal = new Animal X

  class Dog extends Animal {
    override val creatureType = "domestic"
    override def eat(): Unit = println("crunching this bone")

    // overriding is legal for everything
    override val preferredMeal: String = "bones" // a value can override an accessor method, ok only for methods without args
  }


  // traits describe behaviour, it is an abstract data type (similar to Java interfaces)
  trait Carnivore { // Scala 3: traits can have constructor args
    def eat(animal: Animal): Unit
    def sayHi: Unit = println("Roar")
  }

  class TRex extends Carnivore {
    override def eat(animal: Animal): Unit = println("I'm a Trex, I eat animals")
  }

  // abstract classes vs traits

  // practical difference: scala allows single class inheritance, multiple traits inheritance
  trait A
  trait B
  class C extends Dog with Carnivore with A with B {
    override def eat(animal: Animal): Unit = println("eating...")
  }

  // philosophical difference: a class describes a thing, a trait describes a behaviour




  // Any: every other type extends Any
  // AnyRef: extended automatically by each class/trait, scala.Null (the null reference)
  // AnyVal: Int, Boolean, Char, ...

  val aNonExistentAnimal: Animal = null // null extends any possible Ref
  // at the end there is scala.Nothing that extends anything, but has no inhabitants

  val noVal: scala.Nothing = throw new NullPointerException



  def main(args: Array[String]): Unit = {

  }
}
