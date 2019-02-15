package monad.intro

import monad.intro.MonadInstances._
import org.scalacheck.{Arbitrary, Gen, Properties}

object StateMonadProperties extends Properties("StateMonad") {

  type StateM[A] = State[Int, A]

  include(StateMMonadProperties)

  case object StateMMonadProperties extends AbstractMonadProperties[String, Int, Int, StateM]("FixState") {

    override implicit val arbA: Arbitrary[String] = Arbitrary(Gen.alphaStr)

    override implicit val arbM: Arbitrary[StateM[String]] = Generators.stateArbitrary

    override implicit val arbF: Arbitrary[String => StateM[Int]] = Arbitrary(Gen.oneOf(Seq(x => State.pure(x.length))))

    override implicit val arbG: Arbitrary[Int => StateM[Int]] = Arbitrary(Gen.oneOf(Seq(x => State.pure(x + 1))))
  }

}


