package monad.intro

import scala.annotation.tailrec

trait Eq[A] {

  def ===(x: A, y: A): Boolean

}

object Eq {
  def apply[A](implicit eq: Eq[A]): Eq[A] = eq

  implicit class EqOps[A](a: A) {
    def ===(b: A)(implicit eq: Eq[A]): Boolean = eq === (a, b)
  }

}

object EqInstances {

  implicit def listInstance[A]: Eq[List[A]] = new Eq[List[A]] {
    override def ===(x: List[A], y: List[A]): Boolean = {
      @tailrec
      def eq(l: List[A], r: List[A]): Boolean = (l, r) match {
        case (hl :: tl, hr :: tr) if hl == hr => eq(tl, tr)
        case (Nil, Nil) => true
        case _ => false
      }

      eq(x, y)
    }
  }

}

object TestEq extends App {

  import EqInstances._
  import Eq._

  println(List[String]("") === List[String](""))

}