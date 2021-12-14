package com.rockthejvm.part1basics

import scala.annotation.tailrec

object DefaultArgs {

  @tailrec
  def sumUntilTailRec(m: Int, acc: Int = 0): Int =
    if(m <= 0) acc
    else sumUntilTailRec(m - 1, acc + m)
    
  val sumUntil100 = sumUntilTailRec(100) // additional arg passed automatically
  
  // for when you use a function most of the time with the same values
  def savePicture(dirPath: String, name: String, format: String = "jpg", width: Int = 1920, height: Int = 1080): Unit =
    println(s"Saving picture in format $format in path $dirPath")
  
  
  def main(args: Array[String]): Unit = {
    savePicture("/users/jfet/photos", "myphoto") // default args are injected
    savePicture("/users/jfet/photos", "myphoto", "png") // pass explicit different values for default args
    savePicture("/users/jfet/photos", "myphoto", width = 800, height = 600) // named arguments
    savePicture("/users/jfet/photos", "myphoto", height = 600, width = 800) // named arguments swapped
  }
}
