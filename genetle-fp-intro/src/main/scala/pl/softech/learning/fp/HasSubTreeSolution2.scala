package pl.softech.learning.fp

object HasSubTreeSolution2 extends App {

  /*
  *
  *                     1                          2
  *                    / \                        / \
  *                   2   5       hasSubTree     3   4
  *                  / \   \
  *                 3   4   6
  *
  *
  */

  sealed trait Tree[+A] {

    def map[B](f: A => B): Tree[B] = this match {
      case Nil => Nil
      case Leaf(x) => Leaf(f(x))
      case Branch(x, l, r) => Branch(f(x), l.map(f), r.map(f))
    }

    def zipAll[B](t: Tree[B]): Tree[(Option[A], Option[B])] = (this, t) match {
      case (Leaf(x), Leaf(y)) => Leaf(Some(x) -> Some(y))
      case (Leaf(x), Branch(y, _, _)) => Leaf(Some(x) -> Some(y))
      case (Nil, _) => Nil
      case (Branch(x, xl, xr), Branch(y, yl, yr)) => Branch(Some(x) -> Some(y), xl.zipAll(yl), xr.zipAll(yr))
      case (Branch(x, l, r), Leaf(y)) => Branch(Some(x) -> Some(y), l.zipAll(Nil), r.zipAll(Nil))
      case (t, Nil) => t.map(x => (Some(x), None))
    }

    def foldRight[B](z: B)(m: A => B)(f: (B, B) => B): B = this match {
      case Nil => z
      case Leaf(x) => f(z, m(x))
      case Branch(x, l, r) => f(m(x), f(l.foldRight(z)(m)(f), r.foldRight(z)(m)(f)))
    }

  }

  case class Leaf[A](x: A) extends Tree[A]

  case class Branch[A](x: A, l: Tree[A], r: Tree[A]) extends Tree[A]

  case object Nil extends Tree[Nothing]

  object Tree {
    def empty[A]: Tree[A] = Nil
  }

  val sub = Branch(2, Leaf(3), Leaf(4))
  val t = Branch(1, sub, Branch(5, Nil, Leaf(6)))

  def hasSubTree(t: Tree[Int], sub: Tree[Int]): Boolean =
    tails(t).exists(startsWith(_, sub))

  def startsWith(t: Tree[Int], prefix: Tree[Int]): Boolean =
    t.zipAll(prefix).foldRight(true) {
      case (Some(x), Some(y)) => x == y
      case (None, Some(_)) => false
      case _ => true
    }(_ && _)

  def tails(t: Tree[Int]): List[Tree[Int]] = t match {
    case Nil => List.empty
    case Leaf(_) => List(t)
    case Branch(_, l, r) => t :: tails(l) ::: tails(r)
  }

  println(t)
  println(sub)
  println(t.zipAll(sub))
  println(tails(t))
  println(hasSubTree(t, sub))
  println(hasSubTree(t, Nil))
  println(hasSubTree(t, Leaf(11)))
  println(hasSubTree(t, Branch(222, Leaf(11), Nil)))

  println(t.foldRight(0)(identity)(_ + _))
}
