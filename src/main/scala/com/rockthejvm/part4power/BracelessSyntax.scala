package com.rockthejvm.part4power

object BracelessSyntax {

  // if - expressions
  val anIfExpression: String = if (2 > 3) "bigger" else "smaller"

  // java-style
  val anIfExpression_v2: String =
    if (2 > 3) {
      "bigger"
    } else {
      "smaller"
    }

  // compact
  val anIfExpression_v3: String =
    if (2 > 3) "bigger"
    else "smaller"

  // scala 3
  val anIfExpression_v4: String =
    if 2 > 3 then // <- `then` keyword
      "bigger" // higher indentation than the if part
    else
      "smaller"

  val anIfExpression_v5: String =
    if 2 > 3 then
      val result = "bigger"
      result
    else
      val result = "smaller"
      result

  // scala 3 one-liner
  val anIfExpression_v6: String = if 2 > 3 then "bigger" else "smaller"


  // for comprehensions
  val aForComprehension: List[String] = for {
    n <- List(1,2,3)
    s <- List("black", "white")
  } yield s"$n$s"

  // scala 3
  val aForComprehension_v2: List[String] =
    for
      n <- List(1,2,3)
      s <- List("black", "white")
    yield s"$n$s"


  // pattern matching
  val meaningOfLife = 42
  val aPatternMatch: String = meaningOfLife match {
    case 1 => "the one"
    case 2 => "double or nothing"
    case _ => "something else"
  }

  // scala 3
  val aPatternMatch_v2: String =
    meaningOfLife match
      case 1 => "the one"
      case 2 => "double or nothing"
      case _ => "something else"

  // scala 3 again
  val aPatternMatch_v3: String = meaningOfLife match
    case 1 => "the one"
    case 2 => "double or nothing"
    case _ => "something else"


  // methods without braces
  def computeMeaningOfLife(arg: Int): Int = // significant indentation starts here - think of it like a phantom code block
    val partialResult = 40





    partialResult + 2 // still part of the method implementation!


  // class definition with significant indentation (same for traits, objects, enums etc)
  class Animal: // compiler expects the body of Animal, the colon : is mandatory
    def eat(): Unit =
      println("I'm eating")
    end eat // can make the code more readable

    def grow(): Unit =
      println("I'm growing")

    // 3000 more lines of code
  end Animal // not mandatory, can be used with if, match, for, methods, classes, traits, enums, objects


  // anonymous classes
  val aSpecialAnimal: Animal = new Animal:
    override def eat(): Unit = println("I'm special")

  // significant indentation = strictly larger indentation than the previous line
  // 3 spaces + 2 tabs > 2 spaces + 2 tabs
  // 3 spaces + 2 tabs > 3 spaces + 1 tab
  // 3 tabs + 2 spaces ??? 2 tabs + 3 spaces (compiler confused)
  //
  // ==> do not mix tabs and spaces


  def main(args: Array[String]): Unit = {
    println(computeMeaningOfLife(78))
  }
}