package com.rockthejvm.part1basics

object CBNvsCBV {

  // CBV (Call By Value) = arguments are evaluated before function invocation
  def aFunction(arg: Int): Int = arg + 1
  val aComputation = aFunction(23 + 67) // 23 + 67 is calculated before calling the function

  // CBN (Call By Name) = arguments are passed literally without being evaluated
  // Arguments are evaluated only when are required or referred to
  def aByNameFunction(arg: => Int): Int = arg + 1
  val anotherComputation = aByNameFunction(23 + 67) // 23 + 67 is not evaluated before the function call


  def printTwiceByValue(x: Long): Unit = {
    println("By value: " + x)
    println("By value: " + x)
  }

  /*
    - delayed evaluation of the argument
    - argument is evaluated every time it is used
    - if an argument isn't used, it will never be evaluated
  */
  def printTwiceByName(x: => Long): Unit = {
    println("By value: " + x)
    println("By value: " + x)
  }

  def indef(): Int = indef() + 1
  def projectFirst(x: Any, y: => Any) = x


  def main(args: Array[String]): Unit = {
    println(aComputation)
    println(anotherComputation)

    printTwiceByValue(System.nanoTime()) // same value appears twice because it is evaluated before the function call
    printTwiceByName(System.nanoTime()) // different values, System.nanoTime() is evaluated two times
    /**
     * println("By value: " + System.nanoTime())
     * println("By value: " + System.nanoTime())
     */

    println(projectFirst(42, indef())) // no crash
  }
}
