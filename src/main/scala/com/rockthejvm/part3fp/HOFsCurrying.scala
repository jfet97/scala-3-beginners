package com.rockthejvm.part3fp

import scala.annotation.tailrec

object HOFsCurrying {

  // higher order functions
  val aHof: (Int, (Int => Int)) => Int = (n, f) => f(n)
  val anotherHof: Int => (Int => Int) = x => (y => y + 2 * x)
  val anotherHof_v2: Int => Int => Int = x => y => y + 2 * x


  // more examples

  // f(f(f(...(f(m)))))
  @tailrec
  def nTimes(f: Int => Int, n: Int, m: Int): Int =
    if (n <= 0) m
    else nTimes(f, n - 1, f(m))

  val plusOne = (n: Int) => n + 1
  val tenThousand = nTimes(plusOne, 10000, 0)

  /*
  nTimes_v2(po, 3) =
    (m: Int) => nTimes_v2(po, 2)(po(m)) === po(po(po(m)))

  nTimes_v2(po, 2) =
    (m: Int) => nTimes_v2(po, 1)(po(m)) === po(po(m))

  nTimes_v2(po, 1) =>
    (m: Int) => nTimes_v2(po, 0)(po(m)) === po(m)

  nTimes_v2(po, 0) = (m: Int) => m
 */
  def nTimes_v2(f: Int => Int, n: Int): (m: Int) => Int =
    if (n <= 0) m => m
    else m => nTimes_v2(f, n - 1)(f(m))
  // it is not tail recursive (seems to be a JVM limitation)
  // Tail-call optimization is limited to situations where a method or nested function calls
  // itself directly as its last operation, without going through a function value or
  // some other intermediary.

  val plusOneHundred = nTimes_v2(plusOne, 100) // po(po(po... risks SO if the arg is too big :(
  val oneHundred = plusOneHundred(0)


  // currying = HOFs returning function instances
  val superAdder: Int => Int => Int = (x: Int) => (y: Int) => x + y
  val add3: Int => Int = superAdder(3)
  val invokeSuperAdder = superAdder(3)(100) // 103

  // curried methods = methods with multiple arg list
  def curriedFormatter(fmt: String)(x: Double): String = fmt.format(x)

  val standardFormat: (Double => String) = curriedFormatter("%4.2f") // (x: Double) => "%4.2f".format(x)
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f") // (x: Double) => "%10.8f".format(x)

  def main(args: Array[String]): Unit = {
    println(tenThousand)
    println(oneHundred)
    println(standardFormat(Math.PI))
    println(preciseFormat(Math.PI))
  }
}
