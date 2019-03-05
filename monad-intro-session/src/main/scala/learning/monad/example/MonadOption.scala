package learning.monad.example

import learning.monad.example.OptionModule._

object MonadOption extends App {

  val xs = List("11", "22", "0", "9", "9", null)
  val ys = List("11", "0", "33", "3", "3", "1")
  val zs = List("0", "22", "33", "2", "-3", "2")

  def flatten[A](xs: List[((A, A), A)]): List[(A, A, A)] = for {
    ((x1, x2), x3) <- xs
  } yield (x1, x2, x3)

  def map3[A, B](x: (A, A, A))(f: A => B): (B, B, B) = x match {
    case (x1, x2, x3) => (f(x1), f(x2), f(x3))
  }

  val data = flatten(xs.zip(ys).zip(zs))

  def pipeline = data
    .map(z => (DivModule.lift(DivModule.div, -1)).tupled(z))
    .filter(_ >= 0)

  val value = pipeline

  println(value)

  def pipeline2 = data
    .map(x => map3(x)(Option.pure))
    .map(z => (DivModuleWithOption.div _).tupled(z))
    .filter(_.isNonEmpty)

  val value2 = pipeline2

  println(value2)

}

object DivModule {

  def parse(x: String): Double = {

    if (x == null) {
      throw new IllegalArgumentException("arg can't be null")
    }

    x.toDouble

  }

  def div(x: String, y: String, z: String): Double = {

    if (x == null || y == null || z == null) {
      throw new IllegalArgumentException("(x | y | z) can't be null")
    }

    val xx = parse(x)

    val yy = parse(y)

    val zz = parse(z)

    if (yy == 0 || zz == 0) {
      throw new IllegalArgumentException("y or z can't be 0")
    }

    xx / yy / zz

  }

  type Fun3 = (String, String, String) => Double

  def lift(f: Fun3, defaultValue: Double): Fun3 = (x, y, z) =>
    try {
      f(x, y, z)
    } catch {
      case e: Throwable => defaultValue
    }


}

object DivModuleWithOption {

  import OptionModule._

  def parse(x: Option[String]): Option[Double] = x.flatMap { str =>
    try {
      Some(str.toDouble)
    } catch {
      case e: Throwable => None
    }
  }

  def div(x: Option[String], y: Option[String], z: Option[String]): Option[Double] = {
    def zeroToNone(n: Double) = if (n == 0) None else Some(n)

    for {
      xx <- parse(x)
      yy <- parse(y).flatMap(zeroToNone)
      zz <- parse(z).flatMap(zeroToNone)
    } yield xx / yy / zz
  }

}

object OptionModule {

  sealed trait Option[+A] {

    def flatMap[B](f: A => Option[B]): Option[B] = this match {
      case Some(a) => f(a)
      case None => None
    }

    def map[B](f: A => B): Option[B] = flatMap(a => Option.pure(f(a)))

    def isEmpty: Boolean

    def isNonEmpty: Boolean = !isEmpty

  }

  object Option {

    def pure[A](a: A): Option[A] = if (a == null) None else Some(a)

  }

  case class Some[A](get: A) extends Option[A] {
    override def isEmpty: Boolean = false
  }

  case object None extends Option[Nothing] {
    override def isEmpty: Boolean = true
  }


}