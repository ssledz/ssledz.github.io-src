package monad.intro

import scala.annotation.tailrec

trait List[+A] {

  def :::[B >: A](xs: List[B]): List[B] = this match {
    case head :: tail => ::(head, tail.:::(xs))
    case Nil => xs
  }

  def flatMap[B](f: A => List[B]): List[B] = this match {
    case head :: tail => tail.flatMap(f) ::: f(head)
    case Nil => Nil
  }

  def map[B](f: A => B): List[B] = flatMap(a => List.pure(f(a)))

  override def toString: String = {
    @tailrec
    def go(xs: List[A], acc: String): String = xs match {
      case h :: t => go(t, acc + "," + h)
      case Nil => acc
    }

    val res = go(this, "")

    if (res.isEmpty) "()" else "(" + res.tail + ")"
  }
}

object List {

  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil
    else ::(xs.head, apply(xs.tail: _*))


  def empty[A]: List[A] = Nil

  def pure[A](x: A): List[A] = ::(x, Nil)
}

case class ::[A](head: A, tail: List[A]) extends List[A] {

  override def equals(obj: Any): Boolean = {

    @tailrec
    def eq(l: List[A], r: List[A]): Boolean = (l, r) match {
      case (hl :: tl, hr :: tr) if hl == hr => eq(tl, tr)
      case (Nil, Nil) => true
      case _ => false
    }

    obj match {
      case xs: List[A] => eq(this, xs)
      case _ => false
    }

  }
}

case object Nil extends List[Nothing]
