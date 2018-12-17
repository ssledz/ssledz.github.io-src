package pl.softech.learning.fp

import pl.softech.learning.fp.MonadInstances.ErrorOr

object Example extends App {

  def doSomething1(): ErrorOr[List[String]] = {
    Right(List("1", "2", "3"))
  }

  def doSomething2(): ErrorOr[List[String]] = {
    Left("An Error")
  }

  import MonadInstances._

  val mE: Monad[ErrorOr] = Monad[ErrorOr]
  val mL: Monad[List] = Monad[List]


  mE.flatMap(doSomething1())(xs => Right(mL.map(xs)(x => Integer.parseInt(x))))


  val numbers = for {

    l1 <- doSomething1()
    l2 <- doSomething2()

  } yield {

    for {

      n1 <- l1
      n2 <- l2

    } yield ((n1, n2), Integer.parseInt(n1) + Integer.parseInt(n2))

  }

  println(numbers)

  val numbers2 = for {

    n1 <- ListT(doSomething1())
    n2 <- ListT(doSomething1())

  } yield ((n1, n2), Integer.parseInt(n1) + Integer.parseInt(n2))

  println(numbers2.value)

}
