package com.rockthejvm.practice

import scala.annotation.tailrec

// single l
abstract class LList {
  def head: Int
  def tail: LList
  def isEmpty: Boolean
  // add at the front of the list
  def add(element: Int): LList = new Cons(element, this) // immutability
}

class Empty extends LList {
  override def head: Int = throw new NoSuchElementException
  override def tail: LList = throw new NoSuchElementException
  override def isEmpty: Boolean = true

  override def toString: String = "[]"
}

// we have overriden the accessor method tail with a value
class Cons(value: Int, override val tail: LList) extends LList {
  override def head: Int = value
  override def isEmpty: Boolean = false

  override def toString: String = {
    @tailrec
    def concatenator(remainder: LList, acc: String): String =
      if (remainder.isEmpty) acc
      else concatenator(remainder.tail, s"$acc, ${remainder.head}")

    s"[${concatenator(this, "")}]"
  }
}

object LListTest {
  def main(args: Array[String]): Unit = {
    val empty = new Empty
    println(empty.isEmpty)
    println(empty)

    val first3Numbers = Cons(1, Cons(2, Cons(3, empty)))
    println(first3Numbers)

    val first3Numbers_v2 = empty.add(1).add(2).add(3) // [3,2,1]
    println(first3Numbers_v2)
    println(first3Numbers_v2.isEmpty)
  }
}