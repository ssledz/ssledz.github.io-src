package pl.softech.learning.fp

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

case class ComposedMonad[F[_], G[_], A](value: F[G[A]]) {

//  def flatMapF[B](f: A => F[G[B]])(implicit F: Monad[F], G: Monad[G]): ComposedMonad[F, G, B] = ComposedMonad[F, G, B] {
//    F.flatMap(value) { ga : G[A] =>
//      val gb : G[B] = G.flatMap(ga) { a : A =>
//        val fgb : F[G[B]] = f(a)
//        ???
//      }
//      F.pure(gb)
//    }
//  }

  def flatMapF[B](f: A => F[G[B]])(implicit F: Monad[F], G: Monad[G]): ComposedMonad[F, G, B] = ComposedMonad[F, G, B] {
    F.flatMap(value) { ga : G[A] =>
      val gfgb : G[F[G[B]]] = G.map(ga) { a : A =>
        f(a)
      }
      ???
    }
  }

  def flatMap[B](f: A => ComposedMonad[F, G, B])(implicit F: Monad[F], G: Monad[G]): ComposedMonad[F, G, B] =
    flatMapF(a => f(a).value)

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
