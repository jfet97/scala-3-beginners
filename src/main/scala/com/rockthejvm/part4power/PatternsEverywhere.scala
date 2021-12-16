package com.rockthejvm.part4power

object PatternsEverywhere {

  // big idea #1: catches are actually matches

  val potentialFailure: Any = try {
    // code
  } catch {
    // variable patterns with type guards
    case e: RuntimeException => "runtime ex"
    case npe: NullPointerException => "npe"
    case _ => "some other exception"
  }

  // same as
  /*
    try { .. code }
    catch (e) {
      e match {
        case e: RuntimeException => "runtime ex"
        case npe: NullPointerException => "npe"
        case _ => "some other exception"
      }
    }
   */


  // big idea #2: for comprehensions (generators) are based on pattern matching

  val aList = List(1,2,3,4)
  val evenNumbers: List[Int] = for {
    n <- aList if n % 2 == 0
  } yield 10 * n

  val tuples = List((1,2), (3,4))
  val filterTuples: List[Int] = for {
    (first, second) <- tuples if first < 3
  } yield second * 100


  // big idea #3: new syntax (python-like)

  val aTuple: (Int, Int, Int) = (1,2,3)

  val (a, b, c) = aTuple
  val head :: tail = tuples

  def main(args: Array[String]): Unit = {

  }
}