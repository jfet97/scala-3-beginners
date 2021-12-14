package com.rockthejvm.part1basics

import scala.annotation.tailrec

object Functions {

  def aFunction(a: String, b: Int): String =
    a + " " + b // ONE expression

  val aFunctionInvocation = aFunction("Scala", 999999999)

  // same thing (functions without parameters), different styles
  def aNoArgFunction() : Int = 42
  def aParameterlessFunction: Int = 45

  // recursion
  def stringConcatenation(str: String, n: Int): String =
    if(n == 0) ""
    else str + stringConcatenation(str, n-1)

  val scalax3 = stringConcatenation("Scala", 3)

  // "void" functions produce side effect without returing anything
  def aVoidFunction(aString: String): Unit =
    println(aString)

  def computeDoubleStringWithSideEffect(aString: String): String = {
    aVoidFunction(aString) // Unit
    stringConcatenation(aString, 2) // the returned value
  } // discouraged in the fp style

  // nested, local functions
  def aBigFunction(n: Int): Int = {
    def auxFunction(a: Int, b: Int): Int = a + b
    auxFunction(n, n+1)
  }

  /**
   *
   * 1. A greeting function (name, age) => "Hi my name is $name and I am $age years old"
   * 2. Factorial function
   * 3. Fibonacci function
   * 4. Test if a number is prime
   */

  // 1
  def greetings(name: String, age: Int) = s"Hi my name is $name and I am $age years old"

  // 2
  def factorial(n: Int): Int = if(n <= 0) 1 else n * factorial(n-1)

  // 3
  def fibonacci(n: Int): Int =
    if(n < 2) 1
    else fibonacci(n-1) + fibonacci(n-2)

  // 4
  def isPrime(n: Int): Boolean = {
    @tailrec
    def primalityTest(divisor: Int): Boolean =
      if(divisor < 2) true
      else if(n % divisor == 0) false
      else primalityTest(divisor - 1)

    primalityTest(n/2)
  }

  def main(args: Array[String]): Unit = {
    println(scalax3)

    println(greetings("Andrea", 24))
    println(factorial(5)) // 120
    println(fibonacci(5)) // 8 (1 1 2 3 5 8 13 counting from 0)
    println(isPrime(7919)) // true
    println(isPrime(5507)) // true
    println(isPrime(7920)) // false
    println(isPrime(6962)) // false
    println(isPrime(200001)) // false
  }
}
