package applicative

import applicative.ApplicativeInstances._
import applicative.ApplicativeSyntax._
import monad.intro.Option

object OptionExample extends App {

  println((Option.pure(1), Option.pure(2)).mapN(_ + _))
  println((Option.empty[Int], Option.pure(2)).mapN(_ + _))

}
