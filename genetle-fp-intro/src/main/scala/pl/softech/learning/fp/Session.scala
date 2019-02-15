package pl.softech.learning.fp

object Session extends App {

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

  //ADT

  sealed trait Tree[+A]

  case class Leaf[A](value: A) extends Tree[A]

  case class Branch[A](value: A, l: Tree[A], p: Tree[A]) extends Tree[A]

  case object Empty extends Tree[Nothing]

  val st = Branch(2, Leaf(3), Leaf(4))
  val t = Branch(1, st, Branch(5, Empty, Leaf(6)))

  def tails[A](t: Tree[A]): List[Tree[A]] = t match {
    case Empty => List.empty
    case Leaf(_) => List(t)
//    case Branch(a, l, r) => List(t) ++ tails(l) ++ tails(r)
    case Branch(a, l, r) => t :: tails(l) ::: tails(r)
  }

  def startsWith[A](t: Tree[A], prefix: Tree[A]): Boolean = (t, prefix) match {

    case (Empty, Empty) => true
    case (Empty, Leaf(b)) => false
    case (Empty, Branch(b, ll, rr)) => false

    case (Leaf(a), Empty) => true
    case (Leaf(a), Leaf(b)) => a == b
    case (Leaf(a), Branch(b, ll, rr)) => a == b && startsWith(Empty, ll) && startsWith(Empty, rr)

    case (Branch(a, l, r), Empty) => true
    case (Branch(a, l, r), Leaf(b)) => a == b && startsWith(l, Empty) && startsWith(r, Empty)
    case (Branch(a, l, r), Branch(b, ll, rr)) => a == b && startsWith(l, ll) && startsWith(r, rr)

  }

  def hasSubTree[A](t: Tree[A], subTree: Tree[A]): Boolean =
    tails(t).exists(tree => startsWith(tree, subTree))

  println(t)
  println(tails(t))
  println(hasSubTree(t, st))
  println(hasSubTree(t, Leaf(111)))
  println(hasSubTree(t, Empty))
  println(hasSubTree(Empty, st))

}
