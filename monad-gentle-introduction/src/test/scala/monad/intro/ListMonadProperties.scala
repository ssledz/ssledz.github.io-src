package monad.intro

import org.scalacheck._

object ListMonadProperties extends Properties("ListMonad") {

  import MonadInstances._
  import Prop.forAll

  property("Left identity: return a >>= f ≡ f a") = forAll { (a: String, b: String, c: String) =>

    val f: String => List[String] = x => List(x, x + b, x + c)

    val g: String => List[Int] = x => List(x.length)

    Monad[List].pure(a).flatMap(f) == f(a)
  }

  property("Right identity: m >>= return ≡ m") = forAll { (a: String, b: String, c: String) =>

    val l = List(a, b, c)

    l.flatMap(Monad[List].pure) == l
  }

  property("Associativity: (m >>= f) >>= g ≡ m >>= (\\x -> f x >>= g)") = forAll { (a: String, b: String, c: String) =>

    val f: String => List[String] = x => List(x, x + b, x + c)

    val g: String => List[Int] = x => List(x.length)

    val l = List(a, b, c)

    l.flatMap(f).flatMap(g) == l.flatMap(x => f(x).flatMap(g))
  }

}
