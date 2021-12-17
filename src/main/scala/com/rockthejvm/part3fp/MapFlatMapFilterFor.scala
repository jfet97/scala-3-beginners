package com.rockthejvm.part3fp

object MapFlatMapFilterFor {

  // standard library list (single linked list)
  val aList = List(1, 2, 3) // 1 -> 2 -> 3 -> Nil

  val firstElement = aList.head
  var restOfElements = aList.tail

  // map
  val anIncrementedList = aList.map(_ + 1)

  // filter
  val onlyOddNumbers = aList.filter(_ % 2 != 0)

  // flatMap
  val toPair = (x: Int) => List(x, x + 1)
  val aFlatMappedList = aList.flatMap(toPair) // [1, 2, 2, 3, 3, 4]

  // example: all the possible strings in the format "1a - black"
  val numbers = List(1, 2, 3, 4)
  val chars = List('a', 'b', 'c', 'd')
  val colors = List("black", "white", "red")
  /*
   lambda = n => chars.map(c => s"$n$c")
   [1,2,3,4].flatMap(lambda) = ["1a", "1b", "1c", "1d", "2a", "2b", "2c", "2d", ...]
   lambda(1) = chars.map(c => s"1$c") = ["1a", "1b", "1c", "1d"]
   lambda(2) = .. = ["2a", "2b", "2c", "2d"]
   lambda(3) = ..
   lambda(4) = ..
  */
  val combinations = numbers.withFilter(_ % 2 == 0).flatMap(n => chars.flatMap(c => colors.map(col => s"$n$c - $col")))
  // for each n in numbers
  //  for each c in chars
  //   for each col in colors
  //    create a s"$n$c - $col"
  //
  // without flatMap:
  // each char would be mapped into a List[String]
  // so each number would be mapped into a List[List[String]]
  // so combinations will be a List[List[List[String]]]

  // for-comprehension = same as above
  val combinationsFor = for {
    number <- numbers if number % 2 == 0 // generator (equivalent to flatMap)
    char <- chars // generator (equivalent to flatMap)
    color <- colors // generator (equivalent to map because it is the last)
  } yield s"$number$char - $color" // an expression


  // for-comprehensions with Unit
  numbers.foreach(println)
  for {
    num <- numbers
  } println(num)

  /**
   * Exercises
   * 1. LList supports for comprehensions?
   * 2. A small collection of AT MOST ONE element - Maybe[A]
   *  - map
   *  - flatMap
   *  - filter
   */

  // 1
  import com.rockthejvm.practice.{Cons, Empty, LList}
  val lSimpleNumbers: LList[Int] = Cons(1, Cons(2, Cons(3, Empty())))
  // map, flatMap, filter
  val incrementedNumbers = lSimpleNumbers.map(_ + 1)
  val filteredNumbers = lSimpleNumbers.filter(_ % 2 == 0)
  val toPairLList: Int => LList[Int] = (x: Int) => Cons(x, Cons(x + 1, Empty()))
  val flatMappedNumbers = lSimpleNumbers.flatMap(toPairLList)

  // map + flatMap (+ withFilter) = for comprehensions + (if guards)
  val combinationNumbers = for {
    number <- lSimpleNumbers if number % 2 == 0
    char <- Cons('a', Cons('b', Cons('c', Empty())))
  } yield s"$number-$char"

  def main(args: Array[String]): Unit = {
    println(combinations)
    println(combinationsFor)

    println(combinationNumbers)
  }

}
