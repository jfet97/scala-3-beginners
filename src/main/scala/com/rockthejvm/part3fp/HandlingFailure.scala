package com.rockthejvm.part3fp

import scala.util.{Failure, Random, Success, Try}

object HandlingFailure {

  // Try = a potentially failed computation


  // preferred way
  val aTry: Try[Int] = Try(42)
  val aFailedTry: Try[Int] = Try(throw new RuntimeException) // call by name :)

  // alt construction
  val aTry_v2: Try[Int] = Success(42)
  val aFailedTry_v2: Try[Int] = Failure(new RuntimeException)


  // main API

  val checkSuccess: Boolean = aTry.isSuccess
  val checkFailure: Boolean = aTry.isFailure
  val aChain: Try[Int] = aFailedTry.orElse(aTry)

  // map, flatMap, filter, for comprehensions
  val anIncrementedTry: Try[Int] = aTry.map(_ + 1)
  val aFlatMappedTry: Try[String] = aTry.flatMap(mol => Try(s"My meaning of life is $mol"))
  val aFilteredTry: Try[Int] = aTry.filter(_ % 2 == 0) // Success(42) (or fail with NoSuchElementException)


  // WHY: avoid unsafe APIs which can THROW exceptions

  def unsafeMethod(): String =
    throw new RuntimeException("No string for you, buster!")

  // defensive: try-catch-finally
  val stringLengthDefensive: Int = try {
    val aString = unsafeMethod()
    aString.length
  } catch {
    case e: RuntimeException => -1
  }

  // purely functional
  val stringLengthPure: Int = Try(unsafeMethod()).map(_.length).getOrElse(-1)


  // DESIGN

  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException("No string for you, buster!"))
  def betterBackupMethod(): Try[String] = Success("Scala")
  val stringLengthPure_v2: Try[Int] = betterUnsafeMethod().map(_.length)
  val aSafeChain: Try[Int] = betterUnsafeMethod().orElse(betterBackupMethod()).map(_.length)


  /**
   * Exercise:
   *   obtain a connection,
   *   then fetch the url,
   *   then print the resulting HTML
   */
  val host = "localhost"
  val port = "8081"
  val myDesiredURL = "rockthejvm.com/home"

  class Connection {
    val random = new Random()

    def get(url: String): String = {
      if (random.nextBoolean()) "<html>Success</html>"
      else throw new RuntimeException("Cannot fetch page right now.")
    }

    def getSafe(url: String): Try[String] =
      Try(get(url))
  }

  object HttpService {
    val random = new Random()

    def getConnection(host: String, port: String): Connection =
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("Cannot access host/port combination.")

    def getConnectionSafe(host: String, port: String): Try[Connection] =
      Try(getConnection(host, port))
  }

  // defensive style
  val finalHtml: Any = try {
    val conn = HttpService.getConnection(host, port)
    val html = try {
      conn.get(myDesiredURL)
    } catch {
      case e: RuntimeException => s"<html>${e.getMessage}</html>"
    }
  } catch {
    case e: RuntimeException => s"<html>${e.getMessage}</html>"
  }

  // purely functional approach
  val maybeConn: Try[Connection] = Try(HttpService.getConnection(host, port))
  val maybeHtml: Try[String] = maybeConn.flatMap(conn => Try(conn.get(myDesiredURL)))
  val finalResult: String = maybeHtml.fold(e => s"<html>${e.getMessage}</html>", identity)

  // for-comprehension
  val maybeHtml_v2: Try[String] = for {
    conn <- HttpService.getConnectionSafe(host, port)
    html <- conn.getSafe(myDesiredURL)
  } yield html
  val finalResult_v2: String = maybeHtml.fold(e => s"<html>${e.getMessage}</html>", identity)


  def main(args: Array[String]): Unit = {
    println(finalResult)
  }
}