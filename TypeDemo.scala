/**
 * Copyright 2012 Michael N. Gagnon, public domain license
 *
 * Tested with Scala 2.9.2
 *
 * > scalac TypeDemo.scala 
 * > scala TypeDemo
 * OptionTypeclass(false)
 * OptionTypeclass(true)
 *
 */

import annotation.implicitNotFound

trait BoxBase {
  type V
}

case class Box[X]() extends BoxBase {
  type V = X
}

object OptionTypeclass {
  implicit def nonOption[C <: BoxBase, V <: C#V] = OptionTypeclass[C, V](false)
  implicit def option[C <: BoxBase, OptV <: Option[C#V]] = OptionTypeclass[C, OptV](true)
}

@implicitNotFound(msg = "OptionTypeclass[Box[X], T] is only defined when T == X, or T == " +
  "Option[X]; therefore the compiler cannot find implicit value for OptionTypeclass[${BoxT}, ${T}]")
case class OptionTypeclass[BoxT, T](val option : Boolean)

object TypeDemo {
  def main(args: Array[String]) {
    println(implicitly[OptionTypeclass[Box[Int], Int]])
    println(implicitly[OptionTypeclass[Box[Int], Option[Int]]])

    // Line below cause implicitNotFound error
    // println(implicitly[OptionTypeclass[Box[Int], Option[Double]]])
  }
}