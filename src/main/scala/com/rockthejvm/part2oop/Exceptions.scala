package com.rockthejvm.part2oop

object Exceptions {

  val aString: String = null // aString.length crashes with a NullPointerException

  // 1 - throw exception
  // val aWeirdValue: Int = throw new NullPointerException // returns Nothing

  // type Throwable
  //  Error, e.g. StackOverflowError (stuff of the JVM)
  //  Exception, an error in our logic, e.g. NullPointerException

  def getInt(withExceptions: Boolean): Int =
    if (withExceptions) throw new NullPointerException // RuntimeException("No int for you!")
    else 42


  // Any is the lowest common ancestor between Int and String
  val potentialFail = try {
    // code that might fail
    getInt(true)
  } catch {
    // order matches, most specific first!
    case e: NullPointerException => 35
    case e: RuntimeException => "54" // returns 54 if a RuntimeException is caught
  } finally {
    // code executed no matter what
    // useful for closing resourses

    // Unit here, the finally clause does not return
    // anything useful, never
  }

  // custom exceptions
  class MyException extends RuntimeException {
    // fields or methods
    override def getMessage: String = "MY EXCEPTION"
  }

  val myException = new MyException
  // I can throw it


  def main(args: Array[String]): Unit = {
    println(potentialFail)
  }
}
