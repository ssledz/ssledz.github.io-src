package monad.intro

import org.scalacheck.Arbitrary

trait Generators {
  implicit def listArbitrary[A](implicit arb: Arbitrary[scala.List[A]]): Arbitrary[List[A]] = Arbitrary(
    arb.arbitrary.map(l => l.foldLeft(List.empty[A])((xs, x) => ::(x, xs)))
  )
}

object Generators extends Generators
