package com.rockthejvm.part2oop

object PreventingInheritance {

  class Person(name: String) {
    final def enjoyLife(): Int = 42
  }

  final class Adult(name: String) extends Person(name) {
    // override def enjoyLife() = 999 X
  }

  //  class Elder(name: String) extends Adult(name) X

  // sealing a type hierarchy: inheritance only permitted inside this file
  sealed class Guitar(nStrings: Int)
  class ElectricGuitar(nStrings: Int) extends Guitar(nStrings)
  class AcousticGuitar extends Guitar(6)

  // no modifier = inheritance not encouraged

  // encourage inheritance (good pratice), maarked for extension
  open class ExtensibleGuitar(nStrings: Int)

  def main(args: Array[String]): Unit = {

  }
}
