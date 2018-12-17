package pl.softech.learning.fp

trait Monad[M[_]] {

  def pure[A](x: A): M[A]

  def flatMap[A, B](xs: M[A])(f: A => M[B]): M[B]

  def map[A, B](xs: M[A])(f: A => B): M[B] = flatMap(xs)(x => pure(f(x)))

}

object Monad {

  def apply[M[_]](implicit m: Monad[M]): Monad[M] = m

}

object MonadInstances {

  implicit val optionInstance: Monad[Option] = new Monad[Option] {

    override def pure[A](x: A): Option[A] = Option(x)

    override def flatMap[A, B](xs: Option[A])(f: A => Option[B]): Option[B] = xs.flatMap(f)
  }

  implicit val listInstance: Monad[List] = new Monad[List] {

    override def pure[A](x: A): List[A] = List(x)

    override def flatMap[A, B](xs: List[A])(f: A => List[B]): List[B] = xs.flatMap(f)

  }

}