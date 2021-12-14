package com.rockthejvm.part1basics

object ValuesAndTypes {
  
  // values (cannot be reassigned)
  val meaningOfLife: Int = 42
  
  // type inference
  val anInteger = 67 // : Int is optional
  
  // common types
  val aBoolean : Boolean = true
  val aChar: Char = 'a'
  val aString: String = "Scala"
  val anInt: Int = -34 // 4 bytes
  val aShort: Short = 1233 // 2 bytes
  val aLong: Long = 1234423532423L // 8 bytes
  val aFloat: Float = 2.4f // 4 bytes
  val aDouble: Double = 3.14 // 8 bytes
  
  def main(args: Array[String]): Unit = {
    
  }
}
