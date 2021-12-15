package com.rockthejvm.part3fp

object AnonymousFunctions {

  // syntax sugars

  val doubler: Int => Int = new Function1[Int, Int] {
    override def apply(n: Int) = n * 2
  }

  // lambdas = anonymous function instances
  val doubler_v2: Int => Int = (n:Int) => n * 2
  val doubler_v3: Int => Int = n => n * 2

  // zero-arg functions
  val justDoSomething: () => Int = () => 45
  val anInvocation = justDoSomething()

  // alt syntac with curly braces
  val stringToInt = (str: String) => {
    // implementation: clode block
    str.toInt
  }

  val stringToInt_v2 = { (str: String) =>
    // implementation: clode block
    str.toInt
  }


  // type inference
  val doubler_v3: Int => Int = x => x * 2 // type inferred by compiler
  val adder: (Int, Int) => Int = (x, y) => x + y

  // shortest lambdas
  val doubler_v4: Int => Int = _ * 2 // x => x * 2
  val adder_v2: (Int, Int) => Int = _ + _ // (x, y) => x + y
  // each underscore is a different argument, you can't reuse them


  def main(args: Array[String]): Unit = {
    println(justDoSomething)
    println(justDoSomething())

    println(stringToInt("4"))
    println(stringToInt_v2("2"))
  }

}
