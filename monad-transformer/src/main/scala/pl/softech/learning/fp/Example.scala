package pl.softech.learning.fp

import pl.softech.learning.fp.MonadInstances.ErrorOr

import scala.util.Try

object Example extends App {

  def doSomething1(): ErrorOr[List[String]] = {
    Right(List("1", "2", "3"))
  }

  def doSomething2(): ErrorOr[List[String]] = {
    Left("An Error")
  }

  def doSomething3(): ErrorOr[List[String]] = {
    Right(List("1", "2", "s"))
  }

  def parseInt(s: String): ErrorOr[Int] = Try(Integer.parseInt(s)).toEither.left.map(_.toString)

  def parseIntL(s: String): ErrorOr[List[Int]] = parseInt(s).map(List(_))

  import MonadInstances._

  val mE: Monad[ErrorOr] = Monad[ErrorOr]
  val mL: Monad[List] = Monad[List]


  //  mE.flatMap(doSomething1())(xs => Right(mL.map(xs)(x => Integer.parseInt(x))))


  def addNumbers(xs: ErrorOr[List[String]], ys: ErrorOr[List[String]]): ErrorOr[List[((Int, Int), Int)]] = {
    val res: ErrorOr[List[Either[String, ((Int, Int), Int)]]] = for {

      l1 <- xs
      l2 <- ys

    } yield {

      for {

        s1 <- l1
        s2 <- l2

      } yield {

        for {

          x <- parseInt(s1)
          y <- parseInt(s2)

        } yield ((x, y), x + y)
      }

    }
    res.right.flatMap { xs =>

      val z: ErrorOr[List[((Int, Int), Int)]] = Right(List.empty[((Int, Int), Int)])

      (xs.foldLeft(z) { (acc, x) =>
        for {
          ys <- acc
          y <- x
        } yield y :: ys

      }).right.map(_.reverse)

    }
  }

  println(addNumbers(doSomething1(), doSomething1()))
  println(addNumbers(doSomething1(), doSomething2()))
  println(addNumbers(doSomething1(), doSomething3()))

  def addNumbers2(xs: ErrorOr[List[String]], ys: ErrorOr[List[String]]): ListT[ErrorOr, ((Int, Int), Int)] = for {

    s1 <- ListT(xs)
    s2 <- ListT(ys)
    x <- ListT(parseIntL(s1))
    y <- ListT(parseIntL(s2))

  } yield ((x, y), x + y)

  println(addNumbers2(doSomething1(), doSomething1()).value)
  println(addNumbers2(doSomething1(), doSomething2()).value)
  println(addNumbers2(doSomething1(), doSomething3()).value)

}
