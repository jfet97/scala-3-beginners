package com.rockthejvm.part1basics

object StringOps {

  val aString: String = "Hello, I am learning Scala and I am enjoying it so far"

  // string functions
  val secondChar = aString.charAt(1) // 'e'
  val firstWord = aString.substring(0, 5) // "Hello"
  val words = aString.split(" ") // Array("Hello,", "I", "am", ...)
  val startsWithHello = aString.startsWith("Hello") // true
  val allDashes = aString.replace(' ', '-')
  val allUppercase = aString.toUpperCase() // also toLowerCase
  val nChars = aString.length // accessor method

  // other functions
  val reversed = aString.reverse
  val aBunchOfChars = aString.take(10)
  val numberAsString = "2".toInt

  // interpolation
  val name = "Alice"
  val age = 12
  val greeting = "Hello, I'm " + name + " and I am " + age + " years old."
  val greeting_v2 = s"Hello, I'm $name and I am $age years old."
  val greeting_v3 = s"Hello, I'm $name and I will be turning ${age + 1} years old." // any scala expression into ${}

  // f-interpolation for printf like formats
  val speed = 1.2f
  val myth = f"$name can eat $speed%2.2f burgers per minute."

  // raw interpolation ignores escape sequences
  val escapes = raw"This is a \n newline"



  def main(args: Array[String]): Unit = {

    println(allDashes)

    println(reversed)
    println(aBunchOfChars)
    println(numberAsString)

    println(greeting)
    println(greeting_v2)
    println(greeting_v3)
    println(myth)
    println(escapes)


  }
}
