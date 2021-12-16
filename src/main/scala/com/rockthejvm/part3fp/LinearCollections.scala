package com.rockthejvm.part3fp
import scala.util.Random

object LinearCollections {

  // Seq = well-defined ordering + indexing
  def testSeq(): Unit = {
    val aSequence = Seq(4,3,2,1) // Seq is a trait, the constructor uses an implementation (List)

    // main API: index an element
    val thirdElement = aSequence(3) // overrides `apply`

    // other methods
    var reversed = aSequence.reverse
    val concatenation = aSequence ++ Seq(5,6,7)
    val sortedSequence = aSequence.sorted // implicit order, the one of natural numbers
    val anIncrementedSequence = aSequence.map(_ + 1) // [5,4,3,2]
    val aFlatMappedSequence = aSequence.flatMap(x => Seq(x, x + 1)) // [4,5,2,3,3,4,1,2]
    val aFilteredSequence = aSequence.filter(_ % 2 == 0) // [4,2]
    val sum = aSequence.foldLeft(0)(_ + _) // 10
    val stringRep = aSequence.mkString("(", " -> ", ")") // (4 -> 3 -> 2 -> 1)

    println("Seq:")
    println(aSequence)
    println(concatenation)
    println(sortedSequence)
    println(anIncrementedSequence)
    println(aFlatMappedSequence)
    println(aFilteredSequence)
    println(sum)
    println(stringRep)
    println("")
  }

  // lists
  def testLists(): Unit = {
    val aList = List(1,2,3)

    // same API as Seq
    val firstElement = aList.head
    val rest = aList.tail

    // appending and prepending (the + goes on the side of the element to add)
    val aBiggerList = 0 +: aList :+ 4
    val prepending = 0 :: aList // :: equivalent to Cons in our LList (prepending)

    // utility methods
    val scalax5 = List.fill(5)("Scala")

    println("List:")
    println(aList)
    println(aBiggerList)
    println(prepending)
    println(scalax5)
    println("")
  }

  // ranges
  def testRanges(): Unit = {
    var aRange = 1 to 10 // lazy evaluated
    val aNonInclusiveRange = 1 until 10 // 10 not included

    // same Seq API
    (1 to 10).foreach(_ => println("Scala")) // the ten numbers aren't being hold in memory together
  }

  // arrays
  def testArrays(): Unit = {
    val anArray = Array(1,2,3,4,5,6) // int[] on the JVM

    // most Seq APIs are available, but arrays ARE NOT Seqs

    // convert an array into a Seq
    val aSequence: Seq[Int] = anArray.toIndexedSeq

    // arrays are mutable
    anArray.update(2, 30) // no new array is allocated
  }

  // vectors = fast Seqs for a large amount of data
  def testVectors(): Unit = {
    val aVector = Vector(1,2,3,4,5,6)
    // the same Seq API
  }

  // sets
  def testSets(): Unit = {
    val aSet = Set(1,2,3,4,5,4) // no ordering guaranteed
    // equals + hashCode = hash set

    // main API: test if in the set
    val contains3 = aSet.contains(3) // true
    val contains3_v2 = aSet(3) // same: true (apply == contains)

    // adding/removing
    val aBiggerSet = aSet + 4 // [1,2,3,4,5]
    val aSmallerSet = aSet - 4 // [1,2,3,5]

    // concatenation == union
    val anotherSet = Set(4,5,6,7,8)
    val muchBiggerSet = aSet.union(anotherSet)
    val muchBiggerSet_v2 = aSet ++ anotherSet // same
    val muchBiggerSet_v3 = aSet | anotherSet // same

    // difference
    val aDiffSet = aSet.diff(anotherSet)
    val aDiffSet_v2 = aSet -- anotherSet // same

    // intersection
    val anIntersection = aSet.intersect(anotherSet) // [4,5]
    val anIntersection_v2 = aSet & anotherSet // same
  }


  // Vector vs List
  def smallBenchmark(): Unit = {
    val maxRuns = 1000
    val maxCapacity = 1000000

    def getWriteTime(collection: Seq[Int]): Double = {
      val random = new Random()

      // contains maxRuns elements each of those is the tames it takes to update the collection
      val times = for {
        i <- 1 to maxRuns // 1 to maxRuns is a range, we can use for-comprehension on them
      } yield {
        // yield the amount of time
        val index = random.nextInt(maxCapacity)
        val element = random.nextInt()

        val currentTime = System.nanoTime()
        val updatedCollection = collection.updated(index, element)
        System.nanoTime() - currentTime
      }

      // compute average (sum method available if the elements are addable)
      times.sum * 1.0 / maxRuns
    }

    val numbersList = (1 to maxCapacity).toList
    val numbersVector = (1 to maxCapacity).toVector

    println(getWriteTime(numbersList))
    println(getWriteTime(numbersVector))
  }

  def main(args: Array[String]): Unit = {
    testSeq()
    testLists()

    smallBenchmark()
  }


}
