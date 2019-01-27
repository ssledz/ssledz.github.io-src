package pl.softech.learning.fp

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

case class CMonad[F[_], G[_], A](value: F[G[A]]) {

  //  def flatMapF[B](f: A => F[G[B]])(implicit F: Monad[F], G: Monad[G]): CMonad[F, G, B] =
  //    CMonad[F, G, B] {
  //      F.flatMap(value) { ga: G[A] =>
  //        val gb: G[B] = G.flatMap(ga) { a: A =>
  //          val fgb: F[G[B]] = f(a)
  //          ???
  //        }
  //        F.pure(gb)
  //      }
  //    }

  def flatMapF[B](f: A => F[G[B]])(implicit F: Monad[F], G: Monad[G]): CMonad[F, G, B] =
    CMonad[F, G, B] {
      F.flatMap(value) { ga: G[A] =>
        val gfgb: G[F[G[B]]] = G.map(ga) { a: A =>
          f(a)
        }
        ???
      }
    }

  def flatMap[B](f: A => CMonad[F, G, B])(implicit F: Monad[F], G: Monad[G]): CMonad[F, G, B] =
    flatMapF(a => f(a).value)

  def map[B](f: A => B): CMonad[F, G, B] = ???

}

object CMonadExample extends App {

  import MonadInstances._
  import OptionTExample._

  import scala.concurrent.ExecutionContext.Implicits.global

  def findStreetByLogin(login: String): CMonad[Future, Option, String] =
    for {
      user <- CMonad(findUserByLogin(login))
      address <- CMonad(findAddressByUserId(user.id))
    } yield address.street

  val res = Await.result(findStreetByLogin("ss").value, Duration.Inf)

  println(res)

}
