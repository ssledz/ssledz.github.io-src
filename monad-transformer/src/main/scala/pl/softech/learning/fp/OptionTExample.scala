package pl.softech.learning.fp

import pl.softech.learning.fp.MonadInstances._

import scala.concurrent.Future

object OptionTExample extends App {

  case class User(id: Long, login: String)

  case class Address(userId: Long, street: String)

  def findUserByLogin(login: String): Future[Option[User]] = Future.successful(Some(User(1L, "ss")))

  def findAddressByUserId(userId: Long): Future[Option[Address]] = Future.successful(Some(Address(1L, "Aspekt")))

  //  def findStreetByLogin(login: String): Function[String] =
  //    for {
  //      user <- findUserByLogin(login)
  //      address <- findAddressByUserId(user.id)
  //    } yield address.street

  //  val street = for {
  //    user <- findUserByLogin("ma")
  //    address <- findAddressByUserId(user.id)
  //  } yield address.street

  //  def findStreetByLogin(login : String) : Function[Option[String]] =
  //    for {
  //      user <- findUserByLogin(login)
  //      address <- findAddressByUserId(user.map(_.id).getOrElse(-1))
  //    } yield address.map(_.street)

  val street = for {
    user <- OptionT(findUserByLogin("ss"))
    address <- OptionT(findAddressByUserId(user.id))
  } yield address.street

  println(street.value)

}
