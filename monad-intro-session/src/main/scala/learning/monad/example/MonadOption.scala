package learning.monad.example

import learning.monad.example.DataModule._


object MonadOption extends App {

  import OptionModule._

  val xs = List("11", "22", "0" , "9", "9", null)
  val ys = List("11", "0" , "33", "3", "3", "1")
  val zs = List("0" , "22", "33", "2", "-3", "2")

  val data: List[(String, String, String)] = flatten(xs.zip(ys).zip(zs))

    def pipeline: List[Double] = data
      .map((DivModule.lift(DivModule.div, -1)).tupled(_))
      .filter(_ != -1)

  val value = pipeline

  println(value)

  def pipeline2 = data
    .map(x => map3(x)(Option.pure))
    .map(z => (DivModuleWithOption.div _).tupled(z))
    .filter(_.isNonEmpty)
    .map(_.get)

  val value2 = pipeline2

  println(value2)


  object DivModuleWithOption {

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

      def get : A

    }

    object Option {

      def pure[A](a: A): Option[A] = if (a == null) None else Some(a)

    }

    case class Some[A](get: A) extends Option[A] {
      def isEmpty: Boolean = false
    }

    case object None extends Option[Nothing] {
      def isEmpty: Boolean = true

      def get : Nothing = ???
    }


  }

}

