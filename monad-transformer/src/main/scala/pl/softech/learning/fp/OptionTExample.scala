package pl.softech.learning.fp

import pl.softech.learning.fp.MonadInstances._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object OptionTExample extends App {

  case class User(id: Long, login: String)

  case class Address(userId: Long, street: String)

  def findUserByLogin(login: String): Future[Option[User]] = Future.successful(Some(User(1L, "ss")))

  def findAddressByUserId(userId: Long): Future[Option[Address]] = Future.successful(Some(Address(1L, "Aspekt")))

  //  def findStreetByLogin(login: String): Future[String] =
  //    for {
  //      user <- findUserByLogin(login)
  //      address <- findAddressByUserId(user.id)
  //    } yield address.street

  //  val street = for {
  //    user <- findUserByLogin("ma")
  //    address <- findAddressByUserId(user.id)
  //  } yield address.street

  //  def findStreetByLogin(login: String): Future[Option[String]] =
  //    for {
  //      maybeUser <- findUserByLogin(login)
  //      user <- maybeUser
  //      address <- findAddressByUserId(user.id)
  //    } yield address.map(_.street)

  //  def findStreetByLogin(login: String): Future[Option[String]] =
  //    findUserByLogin(login).flatMap {
  //      case Some(user) => findAddressByUserId(user.id).map(_.map(_.street))
  //      case None => Future.successful(None)
  //    }

  //  def findStreetByLogin(login: String): OptionFuture[String] =
  //    for {
  //      user <- OptionFuture(findUserByLogin(login))
  //      address <- OptionFuture(findAddressByUserId(user.id))
  //    } yield address.street

  def findStreetByLogin(login: String): OptionT[Future, String] =
    for {
      user <- OptionT(findUserByLogin(login))
      address <- OptionT(findAddressByUserId(user.id))
    } yield address.street

  val res = Await.result(findStreetByLogin("ss").value, Duration.Inf)

  println(res)

}
