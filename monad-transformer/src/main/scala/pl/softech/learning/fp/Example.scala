package pl.softech.learning.fp

object Example extends App {

  def doSomething(): Option[List[String]] = {
    Some(List("1", "2", "3"))
  }

  import MonadInstances._

  val mO: Monad[Option] = Monad[Option]
  val mL: Monad[List] = Monad[List]


  mO.flatMap(doSomething())(xs => Some(mL.map(xs)(x => Integer.parseInt(x))))


  val numbers = for {

    l1 <- doSomething()
    l2 <- doSomething()

  } yield {

    for {

      n1 <- l1
      n2 <- l2

    } yield ((n1, n2), Integer.parseInt(n1) + Integer.parseInt(n2))

  }

  println(numbers)

  val numbers2 = for {

    n1 <- ListT(doSomething())
    n2 <- ListT(doSomething())

  } yield ((n1, n2), Integer.parseInt(n1) + Integer.parseInt(n2))

  println(numbers2.value)

}
