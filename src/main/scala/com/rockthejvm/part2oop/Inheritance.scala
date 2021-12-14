package com.rockthejvm.part2oop

object Inheritance {

  class Animal {
    val creatureType = "wild"
    def eat(): Unit = println("nomnomnom")
  }

  class Cat extends Animal {
    def crunch() = {
      eat()
      println("crunch, crunch")
    }
  }

  class Person(val name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }

  class Adult(name: String, age: Int, idCard: String) extends Person(name) // must specify super constructor


  // overriding
  class Dog extends Animal { // a Dog "is an" Animal
    override val creatureType = "domestic"
    override def eat(): Unit = {
      println("mmm, I like this bone")
      super.eat()
    }

    override def toString = "a dog"
  }

  // overload vs overriding
  class Crocodile extends Animal {
    override val creatureType = "very wild"
    override def eat(): Unit = println("I can eat anything, I'm a croc")

    // this is an overload because the signature differs from the one in the Animal class
    def eat(animal: Animal): Unit = println("I'm eating this poor fella")
  }

  val cat = new Cat
  val dog = new Dog

  // subtype polymorphism
  val dog2: Animal = new Dog

  def main(args: Array[String]): Unit = {
    cat.eat()
    cat.crunch()

    dog.eat()
    println(dog) // println(dog.toString)

    dog2.eat() // the Dog's method will be called (the most specific will be called)
  }
}
