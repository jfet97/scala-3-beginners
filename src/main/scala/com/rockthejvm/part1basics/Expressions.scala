package com.rockthejvm.part1basics

object Expressions {

  // expressions can be evaluated to a value
  val meaningOfLife: Int = 40 + 2

  // math: =, -, *, /, bitwise ops
  val mathExpression = 2 + 3 * 4

  // comparison expressions: <, <=, >, >=, ==, !=
  val equalityTest = 1 == 2

  //  boolean expressions: !, ||, &&
  val nonEqualityTest = !equalityTest

  // instructions vs expresssions (preferred in scala)
  // instructions are executed, expressions are evaluated

  // ifs are expressions
  val aCondition = true
  val anIfExpression = if (aCondition) 45 else 123

  // code blocks
  val aCodeBlock = {
    // local values
    val localValue = 76
    localValue + 54 // the last expression gives the value of the entire block
  }

  // everything is an expression (unit () come se non ci fosse un domani)
  val yetAnotherValue: Unit = println("Scala")
  val theUnit: Unit = ()


  def main(args: Array[String]): Unit = {
    println(yetAnotherValue)
  }
}
