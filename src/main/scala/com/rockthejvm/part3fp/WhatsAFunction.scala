package com.rockthejvm.part3fp

object WhatsAFunction {

  trait MyFunction[A, B] {
    def apply(arg: A): B
  }

  // anon class
  val doubler = new MyFunction[Int, Int] {
    override def apply(arg: Int): Int = arg * 2
  }

  val meaningOfLife = 42
  val meaningDoubled = doubler(meaningOfLife) // same as doubler.apply(meaningOfLife), because of `apply`

  // function types
  val doublerStandard = new Function1[Int, Int] { // [Arg, Res]
    override def apply(arg: Int): Int = arg * 2
  }
  val meaningDoubled_v2 = doublerStandard(meaningOfLife)

  // (Int, Int) => Int === Function2[Int, Int, Int]
  val adder = new Function2[Int, Int, Int] { // [Arg1, Arg2, Res]
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }

  val anAddition = adder(2, 67)

  // all functions are instances of FunctionX with apply methods


  /**
   * Exercise
   * 1. A function which takes 2 strings and concatenates them
   * 2. Define a function which takes an Int as an argument and returns another function as result
   */

  val concatenator = new Function2[String, String, String] {
    override def apply(s1: String, s2: String): String = s1 + s2
  }

  val adder_v2 = new Function1[Int, Function1[Int, Int]] {
    override def apply(n: Int): Int => Int = new Function1[Int, Int] {
      override def apply(m: Int): Int = n+m
    }
  }

  // same as
  val adder_v3: Int => Int => Int = n => m => n + m

  val threePlusFive = adder_v2(3)(5)
  val threePlusFive_v2 = adder_v3(5)(3)


  // function values != methods
  // def are methods of classes
  // functions values are instances of FunctionX

  def main(args: Array[String]): Unit = {
    println(concatenator("I love ", "Scala"))
    println(threePlusFive)
    println(threePlusFive_v2)
  }
}
