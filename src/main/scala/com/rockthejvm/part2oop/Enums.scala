package com.rockthejvm.part2oop

object Enums {

  // automatically sealed
  enum Permissions {
    case READ, WRITE, EXECUTE, NONE

    // fields and methods
    def openDocument(): Unit =
      if(this == READ) println("opening document...")
      else println("reading not allowed")
  }

  val somePermissions: Permissions = Permissions.EXECUTE

  // constructor args
  enum PermissionsWithBits(val bits: Int) {
    case READ extends PermissionsWithBits(4) // 100
    case WRITE extends PermissionsWithBits(2) // 010
    case EXECUTE extends PermissionsWithBits(1) // 001
    case NONE extends PermissionsWithBits(0) // 000
  }

  val permissionWithBits = PermissionsWithBits.EXECUTE
  val permissionsBits = permissionWithBits.bits

  // companion object
  object PermissionsWithBits {
    def fromBits(bits: Int): PermissionsWithBits = // whatever
      PermissionsWithBits.NONE
  }


  // standard API
  val somePermissionsOrdinal = somePermissions.ordinal
  val allPermissions = PermissionsWithBits.values // array of values
  val readPermission: Permissions = Permissions.valueOf("READ") // possible because Permissions does not take constructor argumetns

  def main(args: Array[String]): Unit = {
    somePermissions.openDocument()

    println(permissionsBits) // 1

    println(somePermissionsOrdinal) // 0 is the ordinal index of READ
    println(allPermissions)
    println(readPermission)
  }
}
