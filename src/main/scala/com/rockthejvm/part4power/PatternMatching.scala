package com.rockthejvm.part4power

import scala.util.Random

object PatternMatching {

  // switch on steroids
  val random = new Random()
  val aValue = random.nextInt(100)

  val description = aValue match {
    // order matters!
    case 1 => "the first"
    case 2 => "the second"
    case 3 => "the third"
    case _ => s"something else: $aValue" // not mandatory but recommended
  }

  // decompose values (case classes)
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 17)

  val greeting = bob match {
    // order matters!
    case Person(n, a) if a < 18 => s"Hi, my name is $n and I'm not allowed to say my age."
    case Person(n, a) => s"Hello there, my name is $n and I'm $a years old."
    case _ => "I don't know who I am."
  }

  /*
   Patterns are matched in order: put the most specific patterns first.
   What if no cases match? MatchError
   What's the type returned? The lowest common ancestor of all types on the RHS of each branch.
  */


  // pattern matching on sealed hierarchies: the compiler checks if all the cases are covered
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Cat(meowStyle: String) extends Animal

  val anAnimal: Animal = Dog("Terra Nova")
  val animalPM = anAnimal match {
    case Dog(someBreed) => "I've detected a dog"
    case Cat(meow) => "I've detected a cat"
  }

  /**
   * Exercise
   *  show(Sum(Number(2), Number(3))) = "2 + 3"
   *  show(Sum(Sum(Number(2), Number(3)), Number(4)) = "2 + 3 + 4"
   *  show(Prod(Sum(Number(2), Number(3)), Number(4))) = "(2 + 3) * 4"
   *  show(Sum(Prod(Number(2), Number(3)), Number(4)) = "2 * 3 + 4"
   */
  sealed trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(expr: Expr): String = expr match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => s"${show(e1)} + ${show(e2)}"
    case Prod(e1, e2) => {
      def parenthesify(expr: Expr) = expr match {
        case Number(_) => show(expr)
        case Prod(_, _) => show(expr)
        case Sum(_, _) => s"(${show(expr)})"
      }

      s"${parenthesify(e1)} * ${parenthesify(e2)}"
    }
  }



  def main(args: Array[String]): Unit = {
    println(description)
    println(greeting)

    println(show(Sum(Number(2), Number(3))))
    println(show(Sum(Sum(Number(2), Number(3)), Number(4))))
    println(show(Prod(Sum(Number(2), Number(3)), Number(4))))
    println(show(Sum(Prod(Number(2), Number(3)), Number(4))))
  }
}
