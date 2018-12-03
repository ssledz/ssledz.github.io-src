package pl.softech.learning.fp

object HasSubTreeSolution1 extends App {

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

    def foldMapRight[B](z: B)(f : A => B)(reduce: (B, B) => B): B = this match {
      case Empty => z
      case Leaf(a) => f(a)
      case Branch(a, l, r) => {
        val accL : B = l.foldMapRight(z)(f)(reduce)
        val accR : B = r.foldMapRight(z)(f)(reduce)
        reduce(reduce(f(a), accL), accR)
      }
    }


//    def foldRight[B](z: B)(f: (A, B) => B): B = this match {
//      case Empty => z
//      case Leaf(a) => f(a, z)
//      case Branch(a, l, r) => {
//
//        val accL : B = l.foldRight(z)(f)
//        val accR : B = r.foldRight(z)(f)
//
//        f(a, accL)
//
//        ???
//
//      }
//    }

  }

  case class Leaf[A](value: A) extends Tree[A]

  case object Empty extends Tree[Nothing]

  case class Branch[A](value: A, l: Tree[A], r: Tree[A]) extends Tree[A]

  val subTree = Branch(2, Leaf(3), Leaf(4))

  val tree = Branch(1, subTree, Branch(5, Empty, Leaf(6)))

  def startsWith[A](t: Tree[A], prefix: Tree[A]): Boolean = (t, prefix) match {

    case (_, Empty) => true
    //    case (Leaf(a), Empty) => true
    //    case (Branch(a, l, r), Empty) => true

    case (Empty, _) => false

    //    case (Empty, Leaf(b)) => false
    case (Leaf(a), Leaf(b)) => a == b
    case (Branch(a, _, _), Leaf(b)) => a == b

    //    case (Empty, Branch(b, l, r)) => false
    case (Leaf(a), Branch(b, l, r)) => a == b && startsWith(Empty, l) && startsWith(Empty, r)
    case (Branch(a, l, r), Branch(b, ll, rr)) => a == b && startsWith(l, ll) && startsWith(r, rr)

  }

  def zip[A](t1: Tree[A], t2: Tree[A]): Tree[(Option[A], A)] = (t1, t2) match {

    case (Empty, Empty) => Empty
    case (Empty, Leaf(b)) => Leaf((None, b))
    case (Empty, Branch(b, l, r)) => Branch((None, b), zip(Empty, l), zip(Empty, r))

    case (Leaf(a), Empty) => Empty
    case (Leaf(a), Leaf(b)) => Leaf((Some(a), b))
    case (Leaf(a), Branch(b, l, r)) => Branch((Some(a), b), zip(Empty, l), zip(Empty, r))

    case (Branch(a, l, r), Empty) => Empty
    case (Branch(a, l, r), Leaf(b)) => Leaf((Some(a), b))
    case (Branch(a, l, r), Branch(b, ll, rr)) => Branch((Some(a), b), zip(l, ll), zip(r, rr))

  }

  def startsWith2[A](t: Tree[A], prefix: Tree[A]): Boolean =
//    zip(t, prefix).foldRight(true) {
//      (x: (Option[A], A), acc: Boolean) =>
//        acc && (x match {
//          case (Some(a), b) => a == b
//          case _ => false
//        })
//    }
  zip(t, prefix).foldMapRight(true) {
    case (Some(a), b) => a == b
    case _ => false
  }(_ && _)

  def tails[A](t: Tree[A]): List[Tree[A]] = t match {
    case Empty => List.empty
    case Leaf(_) => List(t)
    case Branch(_, l, p) => t :: tails(l) ::: tails(p)
  }

  def hasSubTree[A](t: Tree[A], st: Tree[A]): Boolean =
    tails(t).exists(tree => startsWith2(tree, st))

  println(subTree)
  println(tree)
  println(tails(tree))
  println(hasSubTree(tree, subTree))

  println(zip(tree, subTree))
  println(startsWith2(tree, subTree))

}
