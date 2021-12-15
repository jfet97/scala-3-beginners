package com.rockthejvm.practice

import scala.annotation.tailrec

// single linked list
abstract class LList[A] {
  def head: A
  def tail: LList[A]
  def isEmpty: Boolean
  // add at the front of the list
  def add(element: A): LList[A] = new Cons(element, this) // immutability

  def map[B](transformer: A => B): LList[B]
  def filter(predicate: A => Boolean): LList[A]
  def flatMap[B](transformer: A => LList[B]): LList[B]

  def concat(anotherList: LList[A]): LList[A]
  infix def ++(anotherList: LList[A]): LList[A] = concat(anotherList) // this.concat(anotherList)

  def reverse: LList[A]
}

class Empty[A] extends LList[A] {
  override def head: A = throw new NoSuchElementException
  override def tail: LList[A] = throw new NoSuchElementException
  override def isEmpty: Boolean = true

  override def map[B](transformer: A => B): LList[B] = new Empty[B]
  override def filter(predicate: A => Boolean): LList[A] = this
  override def flatMap[B](transformer: A => LList[B]): LList[B] = new Empty[B]

  override def concat(anotherList: LList[A]) = anotherList

  override def reverse = this

  override def toString: String = "[]"
}

// we have overriden the accessor method tail with a value
class Cons[A](value: A, override val tail: LList[A]) extends LList[A] {
  override def head: A = value
  override def isEmpty: Boolean = false

  // toString
  override def toString: String = {
    @tailrec
    def inner(remainder: LList[A], acc: String): String =
      if (remainder.isEmpty) acc
      else inner(remainder.tail, s"${if (acc != "") s"$acc, " else acc}${remainder.head}")

    s"[${inner(this, "")}]"
  }

  // map
  override def map[B](transformer: A => B): LList[B] = {
    @tailrec
    def inner(remainder: LList[A], acc: LList[B]): LList[B] =
      if (remainder.isEmpty) acc
      else inner(remainder.tail, new Cons[B](transformer(remainder.head), acc))

    inner(this, new Empty[B]).reverse
  }

  // filter
  override def filter(predicate: A => Boolean): LList[A] = {
    @tailrec
    def inner(remainder: LList[A], acc: LList[A]): LList[A] =
      if (remainder.isEmpty) acc
      else if (predicate(remainder.head) == false) inner(remainder.tail, acc)
      else inner(remainder.tail, new Cons[A](remainder.head, acc))

    inner(this, new Empty[A]).reverse
  }

  // flatmap
  override def flatMap[B](transformer: A => LList[B]): LList[B] = {
    @tailrec
    def inner(remainder: LList[A], acc: LList[B]): LList[B] =
      if (remainder.isEmpty) acc
      else inner(remainder.tail, transformer(remainder.head).reverse.concat(acc))

    inner(this, new Empty[B]).reverse
  }

  // concat
  override def concat(anotherList: LList[A]): LList[A] = {
    @tailrec
    def inner(remainder: LList[A], acc: LList[A]): LList[A] =
      if(remainder.isEmpty) acc
      else inner(remainder.tail, new Cons[A](remainder.head, acc))

    inner(this.reverse, anotherList)
  }

  // reverse (and type inference)
  override def reverse: LList[A] = {
    @tailrec
    def inner(remainder: LList[A], acc: LList[A]): LList[A] =
      if(remainder.isEmpty) acc
      else inner(remainder.tail, new Cons(remainder.head, acc))

    inner(this, new Empty)
  }
}

object LListTest {
  def main(args: Array[String]): Unit = {

    val empty = new Empty[Int]
    println(empty.isEmpty)
    println(empty)

    // variance
    // val empty_v2: Empty[Int] = new Empty[Nothing] X
    // val empty_v3: Empty[Nothing] = new Empty[Int] X

    val first4Numbers = Cons(1, Cons(2, Cons(3,   Cons(4,  new Empty)))) // A inferred as Int, used for the Empty type parameter as well
    println(first4Numbers) // [1, 2, 3, 4]

    val first4Numbers_v2 = empty.add(1).add(2).add(3).add(4) // [4, 3, 2, 1]
    println(first4Numbers_v2)
    println(first4Numbers_v2.isEmpty)

    val someStrings = Cons("dog", Cons("cat", Cons("crocodile", Empty())))
    println(someStrings)

    println("")
    println("map, filter, flatMap tests:")

    // uses anon classes
    val evenPredicate = new Function1[Int, Boolean] {
      override def apply(element: Int) =
        element % 2 == 0
    }

    val doubler = new Function1[Int, Int] {
      override def apply(value: Int) = value * 2
    }

    val doublerList = new Function1[Int, LList[Int]] {
      override def apply(value: Int) =
        Cons(value, Cons(value + 1, Empty()))
    }

    // map testing
    val numbersDoubled = first4Numbers.map(doubler)
    val numbersDoubled_v2 = first4Numbers.map(x => x * 2)
    val numbersDoubled_v3 = first4Numbers.map(_ * 2) // _ is the implicit parameter
    println(numbersDoubled)

    val numbersNested = first4Numbers.map(doublerList)
    val numbersNested_v2 = first4Numbers.map(value => Cons(value, Cons(value + 1, Empty())))
    println(numbersNested)

    // filter testing
    val onlyEvenNumbers = first4Numbers.filter(evenPredicate) // [2]
    val onlyEvenNumbers_v2 = first4Numbers.filter(elem => elem % 2 == 0)
    val onlyEvenNumbers_v3 = first4Numbers.filter(_ % 2 == 0)
    println(onlyEvenNumbers)

    // test concatenation
    val listInBothWays = first4Numbers ++ first4Numbers_v2
    println(listInBothWays)

    // test flatMap
    val flattenedList = first4Numbers.flatMap(doublerList)
    val flattenedList_v2 = first4Numbers.flatMap(value => Cons(value, Cons(value + 1, Empty())))
    println(flattenedList)
  }
}