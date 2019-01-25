package pl.softech.learning.fp

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

case class ComposedMonad[F[_], G[_], A](value: F[G[A]]) {

  def flatMap[B](f: A => ComposedMonad[F, G, B])(implicit F: Monad[F], G: Monad[G]): ComposedMonad[F, G, B] = ???

  def map[B](f: A => B): ComposedMonad[F, G, B] = ???

}

object ComposedMonadExample extends App {

  import OptionTExample._

  import MonadInstances._
  import scala.concurrent.ExecutionContext.Implicits.global

  def findStreetByLogin(login: String): ComposedMonad[Future, Option, String] =
    for {
      user <- ComposedMonad(findUserByLogin(login))
      address <- ComposedMonad(findAddressByUserId(user.id))
    } yield address.street

  val res = Await.result(findStreetByLogin("ss").value, Duration.Inf)

  println(res)

}
