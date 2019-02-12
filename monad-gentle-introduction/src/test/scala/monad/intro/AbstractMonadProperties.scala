package monad.intro

import org.scalacheck.Prop.forAll
import org.scalacheck._

abstract class AbstractMonadProperties[A, B, C, M[_] : Monad](name: String) extends Properties(name) {

  implicit def arbA: Arbitrary[A]

  implicit def arbM: Arbitrary[M[A]]

  implicit val arbF: Arbitrary[A => M[B]]

  implicit val arbG: Arbitrary[B => M[C]]

  private val monad = implicitly[Monad[M]]

  property("Left identity: return a >>= f ≡ f a") = forAll { (a: A, f: A => M[B]) =>
    monad.flatMap(monad.pure(a))(f) == f(a)
  }

  property("Right identity: m >>= return ≡ m") = forAll { m: M[A] =>
    monad.flatMap(m)(monad.pure) == m
  }

  property("Associativity: (m >>= f) >>= g ≡ m >>= (\\x -> f x >>= g)") = forAll { (m: M[A], f: A => M[B], g: B => M[C]) =>
    monad.flatMap(monad.flatMap(m)(f))(g) == monad.flatMap(m)(x => monad.flatMap(f(x))(g))
  }

}
