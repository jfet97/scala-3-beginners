package com.rockthejvm.part3fp

import scala.util.Random

object Options {

  // options = "collections" with at most one value
  // goal: to work with unsafe APIs that returns null for signalling the absence of a value

  // "standard" API

  // preferred version
  val anOption: Option[Int] = Option(42)
  val anEmptyOption: Option[Int] = Option.empty

  // alt version
  val aPresentValue: Option[Int] = Some(4)
  val anEmptyOption_v2: Option[Int] = None

  val isEmpty: Boolean = anOption.isEmpty
  val innerValue: Int = anOption.getOrElse(90)
  val anotherOption: Option[Int] = Option(46)
  val aChainedOption: Option[Int] = anEmptyOption.orElse(anotherOption) // like a choice

  // map, flatMap, filter, for
  val anIncrementedOption: Option[Int] = anOption.map(_ + 1) // Some(43)
  val aFilteredOption: Option[Int] = anIncrementedOption.filter(_ % 2 == 0) // None
  val aFlatMappedOption: Option[Int] = anOption.flatMap(value => Option(value * 10)) // Some(420)


  // WHY options: work with unsafe API

  def unsafeMethod(): String = null
  def fallbackMethod(): String = "some valid result"

  // defensive style
  val stringLength: Int = {
    val potentialString = unsafeMethod()
    if (potentialString == null) -1
    else potentialString.length
  }

  // option-style: no need for null checks (the constructor takes care of null values)
  val stringLengthOption: Option[Int] = Option(unsafeMethod()).map(_.length)
  // use-case for orElse
  val someResult: Option[String] = Option(unsafeMethod()).orElse(Option(fallbackMethod()))


  // DESIGN

  def betterUnsafeMethod(): Option[String] = None
  def betterFallbackMethod(): Option[String] = Some("A valid result")
  val betterChain: Option[String] = betterUnsafeMethod().orElse(betterFallbackMethod())

  // example: Map.get
  val phoneBook = Map(
    "Daniel" -> 1234
  )
  val marysPhoneNumber: Option[Int] = phoneBook.get("Mary") // None
  // no need to crash, check for nulls or if Mary is present in the map


  /**
   * Exercise:
   *  Get the host and port from the config map,
   *    try to open a connection,
   *    print "Conn successful"
   *    or "Conn failed"
   */

  val config: Map[String, String] = Map(
    // comes from elsewhere
    "host" -> "176.45.32.1",
    "port" -> "8081"
  )
  // config.get returns an Option[String]

  class Connection {
    def connect(): String = "Connection successful"
  }

  object Connection {
    val random = new Random()

    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection)
      else None
  }

  // defensive style (in an imperative language e.g. Java)
  /*
    String host = config("host")
    String port = config("port")
    if (host != null)
      if (port != null)
        Connection conn = Connection.apply(host, port)
        if (conn != null)
          return conn.connect()
        // ... that's just the happy path, we need to add the rest of the branches
   */

  // options style
  val host: Option[String] = config.get("host")
  val port: Option[String] = config.get("port")
  val connection: Option[Connection] = host.flatMap(h => port.flatMap(p => Connection(h, p)))
  val connStatus: Option[String] = connection.map(_.connect())

  // compact version
  val connStatus_v2: Option[String] =
    config.get("host").flatMap(h =>
      config.get("port").flatMap(p =>
        Connection(h, p).map(_.connect())
      )
    )

  // for-comprehension (do notation)
  val connStatus_v3: Option[String] = for {
    h <- config.get("host")
    p <- config.get("port")
    conn <- Connection(h, p)
  } yield conn.connect()

  def main(args: Array[String]): Unit = {
    println(connStatus.getOrElse("Failed to establish connection"))
  }
}