package com.rockthejvm.part4power

import com.rockthejvm.practice.*

object AllThePatterns {

  object MySingleton

  // 1 - constants
  val someValue: Any = "Scala" // Any to make the compiler ignore the fact that we match a String with other stuff
  val constants: String = someValue match {
    case 42 => "a number"
    case "Scala" => "THE Scala"
    case true => "the truth"
    case MySingleton => "a singleton object"
  }


  // 2 - match anything
  val matchAnythingVar: String = 2 + 3 match {
    case something => s"I've matched anything, it's $something" // can use the matched value
  }
  val matchAnything: String = someValue match {
    case _ => "I can match anything at all" // cannot use the matched value
  }


  // 3 - tuples
  val aTuple: (Int, Int) = (1,4)
  val matchTuple: String = aTuple match {
    case (1, somethingElse) => s"A tuple with 1 and $somethingElse"
    case (something, 2) => "A tuple with 2 as its second field"
  }

  // pattern matching structures can be NESTED

  val nestedTuple: (Int, (Int, Int)) = (1, (2, 3))
  val matchNestedTuple: String = nestedTuple match {
    case (_, (2, v)) => "A nested tuple ..."
  }


  // 4 - case classes
  val aList: LList[Int] = Cons(1, Cons(2, Empty()))
  val matchList: String = aList match {
    case Empty() => "an empty list"
    case Cons(head, Cons(_, tail)) => s"a non-empty list starting with $head"
  }

  val anOption: Option[Int] = Option(2)
  val matchOption: String = anOption match {
    case None => "an empty option"
    case Some(value) => s"non-empty, got $value"
  }


  // 5 - list patterns
  val aStandardList = List(1,2,3,42)
  val matchStandardList: String = aStandardList match {
    case List(1, _, _, _) => "list with 4 elements, first is 1"
    case List(1, _*) => "list starting with 1" // variable number of elements after 1 (same as 1 :: tail)
    case List(1, 2, _) :+ 42 => "list starting with 1, 2 and ending with 42"
    case head :: tail => "deconstructed list"
  }


  // 6 - type specifiers
  val unknown: Any = 2
  val matchTyped: String = unknown match {
    case anInt: Int => s"I matched an int, I can add 2 to it: ${anInt + 2}"
    case aString: String => "I matched a String"
    case _: Double => "I matched a double I don't care about"
  }


  // 7 - name binding
  val bindingNames: String = aList match {
    case Cons(head, rest @ Cons(_, tail)) => s"Can use $rest"
  }


  // 8 - chained patterns: same code for multiple cases (patterns)
  val multiMatch: String = aList match {
    case Empty() | Cons(0, _) => "an empty list to me"
    case _ => "anything else"
  }


  // 9 - if guards
  val secondElementSpecial: String = aList match {
    case Cons(_, Cons(specialElement, _)) if specialElement > 5 => "second element is big enough"
    case _ => "anything else"
  }


  /**
  Example: does this make sense?
   */
  val aSimpleInt = 45

  // anti-pattern: convoluted, hard to read
  val isEven_bad: Boolean = aSimpleInt match {
    case n if n % 2 == 0 => true
    case _ => false
  }

  // anti-pattern 2: if (condition) true else false
  val isEven_bad_v2: Boolean = if (aSimpleInt % 2 == 0) true else false

  // better - return the boolean expression, it has everything you need!
  val isEven: Boolean = aSimpleInt % 2 == 0


  /**
   * Exercise (trick)
   */
  val numbers: List[Int] = List(1,2,3,4)
  val numbersMatch: String = numbers match {
    case listOfStrings: List[String] => "a list of strings"
    case listOfInts: List[Int] => "a list of numbers"
  }

  /*
    pattern matching runs at runtime
    - uses reflection to inspect the real type of a variable at runtime
    - but generic types are erased at runtime (type erasure)
        List[String] => List
        List[Int] => List
        Function1[Int, String] => Function1
        etc.
   */

  def main(args: Array[String]): Unit = {
    println(numbersMatch)
  }
}