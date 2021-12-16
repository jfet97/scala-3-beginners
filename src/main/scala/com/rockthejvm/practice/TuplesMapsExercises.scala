package com.rockthejvm.practice

import scala.annotation.tailrec

object TuplesMapsExercises {

  /**
   * Social network = Map[String, Set[String]]
   * Friend relationships are MUTUAL.
   *
   * - add a person to the network
   * - remove a person from the network
   * - add friend relationship
   * - unfriend
   *
   * - number of friends of a person
   * - who has the most friends
   * - how many people have NO friends
   * + if there is a social connection between two people (direct or not)
   *
   * Daniel <-> Mary <-> Jane <-> Tom => there is a social connection between Daniel and Jane
   */

  def addPerson(network: Map[String, Set[String]], newPerson: String): Map[String, Set[String]] =
    network + (newPerson -> Set())

  def removePerson(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
    network - person

  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] =
    if (!network.contains(a)) throw new IllegalArgumentException(s"The person $a is not part of the network")
    else if (!network.contains(b)) throw new IllegalArgumentException(s"The person $b is not part of the network")
    else {
      val friendsOfA = network(a)
      val friendsOfB = network(b)

      // mutual add friends to the correspondent sets of friends
      // then update the network, replacing the sets for both a and b
      network + (a -> (friendsOfA + b)) + (b -> (friendsOfB + a))
    }

  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] =
    if (!network.contains(a) || !network.contains(b)) network
    else
      val friendsOfA = network(a)
      val friendsOfB = network(b)

      // mutual remove friends from the correspondent sets of friends
      // then update the network, replacing the sets for both a and b
      network + (a -> (friendsOfA - b)) + (b -> (friendsOfB - a))



  def nFriends(network: Map[String, Set[String]], person: String): Int =
    if (!network.contains(person)) -1
    else network(person).size

  def mostFriends(network: Map[String, Set[String]]): String =
    if (network.isEmpty) throw new RuntimeException("Network is empty, no-one with most friends")
    else network.foldLeft("" -> -1)((acc, curr) =>
      if(curr._2.size > acc._2) curr._1 -> curr._2.size
      else acc
    )._1

  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int =
    network.count(_._2.isEmpty)

  def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean = {
    // Graph search
    @tailrec
    def search(discoveredPeople: Set[String], consideredPeople: Set[String]): Boolean =
      if (discoveredPeople.isEmpty) false // exhausted search
      else {
        val person = discoveredPeople.head
        val personsFriends = network(person)

        if (personsFriends.contains(b)) true // social connection found
        else search(discoveredPeople - person ++ personsFriends -- consideredPeople, consideredPeople + person)
      }

    if (!network.contains(a) || !network.contains(b)) false
    else search(Set(a), Set())
  }



  def main(args: Array[String]): Unit = {
    val network = addPerson(addPerson(Map(), "Bob"), "Mary")
    println(network)
    println(friend(network, "Bob", "Mary"))
    println(unfriend(friend(network, "Bob", "Mary"), "Bob", "Mary"))

    val people = addPerson(addPerson(addPerson(Map(), "Bob"), "Mary"), "Jim")
    val simpleNet = friend(friend(people, "Bob", "Mary"), "Jim", "Mary")
    println(simpleNet)
    println(nFriends(simpleNet, "Mary")) // 2
    println(nFriends(simpleNet, "Bob")) // 1
    println(nFriends(simpleNet, "Daniel")) // -1

    println(mostFriends(simpleNet)) // Mary

    println(nPeopleWithNoFriends(addPerson(simpleNet, "Daniel"))) // 1

    println(socialConnection(simpleNet, "Bob", "Jim")) // true
    println(socialConnection(friend(network, "Bob", "Mary"), "Bob", "Mary")) // true
    println(socialConnection(addPerson(simpleNet, "Daniel"), "Bob", "Daniel")) // false

  }
}
