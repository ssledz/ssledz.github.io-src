package learning.monad.example

import learning.monad.example.DataModule._

object MonadOptionSession extends App {

  val xs = List("11", "22", "0", "9", "9", null)
  val ys = List("11", "0", "33", "3", "3", "1")
  val zs = List("0", "22", "33", "2", "-3", "2")

  val data = flatten(xs.zip(ys).zip(zs))

  def pipeline = data
    .map(z => (DivModule.div _).tupled(z))

  val value = pipeline

  println(value)

}
