package com.rockthejvm.part3fp

object TuplesMaps {

  // tuples = finite ordered heterogeneous "lists" / group of values under the same "big" value
  val aTuple: (Int, String) = (2, "rock the jvm") // Tuple2[Int, String] == (Int, String)
  val firstField: Int = aTuple._1
  val aCopiedTuple: (Int, String) = aTuple.copy(_1 = 54)

  // tuples of 2 elements are considered associations (syntactic sugar)
  val aTuple_v2: (Int, String) = 2 -> "rock the jvm" // IDENTICAL to (2, "rock the jvm")

  // maps: keys -> values
  val aMap = Map()

  val phonebook: Map[String, Int] = Map(
    "Jim" -> 555,
    "Daniel" -> 789,
    "Jane" -> 123
  ).withDefaultValue(-1)

  // core APIs
  val phonebookHasDaniel: Boolean = phonebook.contains("Daniel")
  // apply == get
  val marysPhoneNumber: Int = phonebook("Mary") // crash with an exception unless `withDefaultValue` is used

  // add a pair
  val newPair: (String, Int) = "Mary" -> 678
  val newPhonebook: Map[String, Int] = phonebook + newPair // new map

  // remove a key
  val phoneBookWithoutDaniel: Map[String, Int] = phonebook - "Daniel" // new map

  // list -> map
  val linearPhonebook = List(
    "Jim" -> 555,
    "Daniel" -> 789,
    "Jane" -> 123
  )
  val phonebook_v2: Map[String, Int] = linearPhonebook.toMap // available only if linearPhonebook is a list of associations (A -> B)

  // map -> linear collection
  val linearPhonebook_v2: List[(String, Int)] = phonebook.toList // toSeq, toVector, toArray, toSet

  // map, flatMap, filter

  // which one will be the value? 123 or 999?
  // Map("Jim" -> 123, "jiM" -> 999) => Map("JIM" -> ????)
  val aProcessedPhonebook: Map[String, Int] = phonebook.map(pair => (pair._1.toUpperCase(), pair._2))

  // filtering keys
  val noJs: Map[String, Int] = phonebook.view.filterKeys(!_.startsWith("J")).toMap

  // mapping values
  val prefixNumbers: Map[String, String] = phonebook.view.mapValues(number => s"0255-$number").toMap

  // other collections can create maps
  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  val nameGroupings: Map[Char, List[String]] = names.groupBy(name => name.charAt(0)) // Map[Char, List[String]]
  // the result of the function is used as key
  // each value is a List[String] which contains all the strings in names for which the function returns the same result


  def main(args: Array[String]): Unit = {
    println(phonebook)
    println(phonebookHasDaniel)
    println(marysPhoneNumber)
    println(linearPhonebook_v2)
    println(aProcessedPhonebook)
    println(nameGroupings)
  }
}