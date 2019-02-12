package monad.intro

import org.scalacheck.Prop.forAll
import org.scalacheck._

abstract class AbstractMonadProperties[A, B, C, M[_] : Monad](name: String) extends Properties(name) {

  implicit val arbA: Arbitrary[A]

  implicit val arbM: Arbitrary[M[A]]

  implicit val arbF: Arbitrary[A => M[B]]

  implicit val arbG: Arbitrary[B => M[C]]

  private val monad = implicitly[Monad[M]]

  import monad._

  property("Left identity: return a >>= f ≡ f a") = forAll { (a: A, f: A => M[B]) =>
    (`return`(a) >>= f) == f(a)
  }

  property("Right identity: m >>= return ≡ m") = forAll { m: M[A] =>
    (m >>= `return`) == m
  }

  property("Associativity: (m >>= f) >>= g ≡ m >>= (\\x -> f x >>= g)") = forAll { (m: M[A], f: A => M[B], g: B => M[C]) =>
    ((m >>= f) >>= g) == (m >>= (x => f(x) >>= g))
  }

  private val `return`: A => M[A] = pure _

  private implicit class MonadOps[A](m: M[A]) {
    def >>=[B](f: A => M[B]): M[B] = monad.flatMap(m)(f)
  }

}
