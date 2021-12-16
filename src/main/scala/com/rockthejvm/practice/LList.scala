package com.rockthejvm.practice

import scala.annotation.tailrec

// single linked list
abstract class LList[A] {
  def head: A
  def tail: LList[A]
  def isEmpty: Boolean
  // add at the front of the list
  def add(element: A): LList[A] = Cons(element, this) // immutability

  def map[B](transformer: A => B): LList[B]
  def filter(predicate: A => Boolean): LList[A]
  def withFilter(predicate: A => Boolean): LList[A] = filter(predicate)
  def flatMap[B](transformer: A => LList[B]): LList[B]

  def concat(anotherList: LList[A]): LList[A]
  infix def ++(anotherList: LList[A]): LList[A] = concat(anotherList) // this.concat(anotherList)

  def reverse: LList[A]

  def foreach(action: A => Unit): Unit
  def sort(comparator: (A, A) => Int): LList[A]
  def zipWith[B, T](list: LList[T], zip: (A, T) => B): LList[B]
  def foldLeft[B](start: B)(operator: (B, A) => B): B

  def copy: LList[A]
  def length: Int
}

object LList {
  @tailrec
  def find[A](list: LList[A], predicate: A => Boolean): A = {
    if (list.isEmpty) throw new NoSuchElementException
    else if (predicate(list.head)) list.head
    else find(list.tail, predicate)
  }
}

case class Empty[A]() extends LList[A] {
  override def head: A = throw new NoSuchElementException
  override def tail: LList[A] = throw new NoSuchElementException
  override def isEmpty: Boolean = true

  override def map[B](transformer: A => B): LList[B] = Empty()
  override def filter(predicate: A => Boolean): LList[A] = this
  override def flatMap[B](transformer: A => LList[B]): LList[B] = Empty()

  override def concat(anotherList: LList[A]) = anotherList

  override def reverse = this

  override def foreach(action: A => Unit): Unit = ()
  override def sort(comparator: (A, A) => Int): LList[A] = this
  override def zipWith[B, T](list: LList[T], zip: (A, T) => B) =
    if (!list.isEmpty) throw new IllegalArgumentException("Zipping lists of non equal length")
    else Empty()
  override def foldLeft[B](start: B)(operator: (B, A) => B): B = start

  override def copy: LList[A] = this

  override def length: Int = 0

  override def toString: String = "[]"
}

// we have overriden the accessor method tail with a value
case class Cons[A](value: A, override val tail: LList[A]) extends LList[A] {
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
      else inner(remainder.tail, Cons[B](transformer(remainder.head), acc))

    inner(this, Empty()).reverse
  }

  // filter
  override def filter(predicate: A => Boolean): LList[A] = {
    @tailrec
    def inner(remainder: LList[A], acc: LList[A]): LList[A] =
      if (remainder.isEmpty) acc
      else if (predicate(remainder.head) == false) inner(remainder.tail, acc)
      else inner(remainder.tail, Cons[A](remainder.head, acc))

    inner(this, Empty()).reverse
  }

  // flatmap
  override def flatMap[B](transformer: A => LList[B]): LList[B] = {
    @tailrec
    def inner(remainder: LList[A], acc: LList[B]): LList[B] =
      if (remainder.isEmpty) acc
      else inner(remainder.tail, transformer(remainder.head).reverse.concat(acc))

    inner(this, Empty()).reverse
  }

  // concat
  override def concat(anotherList: LList[A]): LList[A] = {
    @tailrec
    def inner(remainder: LList[A], acc: LList[A]): LList[A] =
      if(remainder.isEmpty) acc
      else inner(remainder.tail, Cons[A](remainder.head, acc))

    inner(this.reverse, anotherList)
  }

  // reverse (and type inference)
  override def reverse: LList[A] = {
    @tailrec
    def inner(remainder: LList[A], acc: LList[A]): LList[A] =
      if(remainder.isEmpty) acc
      else inner(remainder.tail, Cons(remainder.head, acc))

    inner(this, Empty())
  }

  // foreach
  override def foreach(action: A => Unit): Unit = {
    @tailrec
    def inner(remainder: LList[A]): Unit =
      if(remainder.isEmpty) ()
      else {
        action(remainder.head)
        inner(remainder.tail)
      }

    inner(this)
  }

  // sort
  override def sort(comparator: (A, A) => Int): LList[A] = {
    @tailrec
    def move(el: A, remainder: LList[A], acc: LList[A] = Empty()): LList[A] =
      if(remainder.isEmpty) Cons(el, acc).reverse
      else if(comparator(el, remainder.head) <= 0) acc.reverse.concat(Cons(el, remainder))
      else move(el, remainder.tail, Cons(remainder.head, acc))

    @tailrec
    def inner(remainder: LList[A], acc: LList[A] => LList[A] = identity): LList[A] =
      if(remainder.isEmpty) acc(Empty())
      else inner(remainder.tail, orderedTail => acc(move(remainder.head, orderedTail)))

    inner(this)
  }

  override def zipWith[B, T](list: LList[T], zip: (A, T) => B): LList[B] = {
    if (this.length != list.length) throw new IllegalArgumentException("Zipping lists of non equal length")

    @tailrec
    def inner(thisRemainder: LList[A], listRemainder: LList[T], acc: LList[B] = Empty()): LList[B] =
      if (thisRemainder.isEmpty) acc
      else inner(thisRemainder.tail, listRemainder.tail, Cons(zip(thisRemainder.head, listRemainder.head), acc))

    inner(this, list).reverse
  }

  // foldLeft
  override def foldLeft[B](start: B)(operator: (B, A) => B): B = {
    @tailrec
    def inner(remainder: LList[A], acc: B): B =
      if(remainder.isEmpty) acc
      else inner(remainder.tail, operator(acc, remainder.head))

    inner(this, start)
  }

  // copy
  override def copy: LList[A] = {
    @tailrec
    def inner(remainder: LList[A], acc: LList[A]): LList[A] =
      if(remainder.isEmpty) acc
      else inner(remainder.tail, Cons(remainder.head, acc))

    inner(this, Empty()).reverse
  }

  // length
  override def length: Int = {
    @tailrec
    def inner(remainder: LList[A], acc: Int = 0): Int =
      if(remainder.isEmpty) acc
      else inner(remainder.tail, acc + 1)

    inner(this)
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

    val first4Numbers = Cons(1, Cons(2, Cons(3, Cons(4, Empty())))) // A inferred as Int, used for the Empty type parameter as well
    println(first4Numbers) // [1, 2, 3, 4]

    val first4Numbers_v2 = empty.add(1).add(2).add(3).add(4) // [4, 3, 2, 1]
    println(first4Numbers_v2)
    println(first4Numbers_v2.isEmpty)

    val someStrings = Cons("bird", Cons("dog", Cons("cat", Cons("crocodile", Empty()))))
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

    // find test
    println(LList.find[Int](first4Numbers, _ % 2 == 0)) // 2
    println(try {
      LList.find[Int](first4Numbers, _ % 7 == 0)
    } catch {
      case e: NoSuchElementException => "No such element"
    })

    // HOFs exercises testing
    first4Numbers.foreach(println)
    println(first4Numbers.sort((n, m) => if (n > m) 1 else -1))
    println(first4Numbers.sort((n, m) => if (n < m) 1 else -1))
    println(first4Numbers.foldLeft(0)(_ + _))

    val zippedList = first4Numbers.zipWith[String, String](someStrings, (number, string) => s"$number-$string")
    println(zippedList)
  }
}