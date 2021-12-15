package com.rockthejvm.practice

import scala.annotation.tailrec

// single l
abstract class LList[A] {
  def head: A
  def tail: LList[A]
  def isEmpty: Boolean
  // add at the front of the list
  def add(element: A): LList[A] = new Cons(element, this) // immutability
}

class Empty[A] extends LList[A] {
  override def head: A = throw new NoSuchElementException
  override def tail: LList[A] = throw new NoSuchElementException
  override def isEmpty: Boolean = true

  override def toString: String = "[]"
}

// we have overriden the accessor method tail with a value
class Cons[A](value: A, override val tail: LList[A]) extends LList[A] {
  override def head: A = value
  override def isEmpty: Boolean = false

  override def toString: String = {
    @tailrec
    def concatenator(remainder: LList[A], acc: String): String =
      if (remainder.isEmpty) acc
      else concatenator(remainder.tail, s"${if (acc != "") s"$acc, " else acc}${remainder.head}")

    s"[${concatenator(this, "")}]"
  }
}

object LListTest {
  def main(args: Array[String]): Unit = {
    val empty = new Empty[Int]
    println(empty.isEmpty)
    println(empty)

    val first3Numbers = Cons(1, Cons(2, Cons(3, empty)))
    println(first3Numbers)

    val first3Numbers_v2 = empty.add(1).add(2).add(3) // [3,2,1]
    println(first3Numbers_v2)
    println(first3Numbers_v2.isEmpty)

    val someStrings = Cons("dog", Cons("cat", Cons("crocodile", Empty())))
    println(someStrings)
  }
}