package com.rockthejvm.part1basics

import scala.annotation.tailrec

object Recursion {

  // "repetition" = recursion

  def sumUntil(n: Int): Int =
    if (n <= 0) 0
    else n + sumUntil(n - 1)

  def sumUntil_v2(n: Int): Int = {
    // validate the tail-recursiveness of the function
    @tailrec
    def sumUntilTailRec(m: Int, acc: Int = 0): Int =
      if(m <= 0) acc
      else sumUntilTailRec(m - 1, acc + m) // tail-call recursion

    sumUntilTailRec(n)
  }

  def sumNumbersBetween(a: Int, b: Int): Int =
    if (a > b) 0
    else a + sumNumbersBetween(a + 1, b)

  def sumNumbersBetween_v2(a: Int, b: Int): Int = {
    @tailrec
    def sumNumbersBetweenTailRec(curr: Int, acc: Int = 0): Int =
      if(curr > b) acc
      else sumNumbersBetweenTailRec(curr + 1, acc + curr)

    sumNumbersBetweenTailRec(a)
  }

  /**
   * Exercises
   * 1. Concatenate a string n times
   * 2. Fibonacci function
   * 3. Is isPrime function tail recursive or not? Mine it is :D
   */

  @tailrec
  def stringNTimes(str: String, n: Int, acc: String = ""): String =
    if(n <= 0) acc
    else stringNTimes(str, n - 1, acc + str)

  @tailrec
  def fibonacci(n: Int, prev: Int = 1, curr: Int = 1): Int =
    if(n == 0) prev
    else fibonacci(n - 1, curr, curr + prev)


  def main(args: Array[String]): Unit = {
    println(sumUntil_v2(20000))
    println(stringNTimes("Scala", 5))
    println(fibonacci(5))
  }
}
