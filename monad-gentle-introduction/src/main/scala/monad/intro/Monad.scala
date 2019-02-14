package monad.intro

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
    override def pure[A](x: A): Option[A] = Option.pure(x)

    override def flatMap[A, B](xs: Option[A])(f: A => Option[B]): Option[B] =
      xs.flatMap(f)
  }

  implicit val tryInstance: Monad[Try] = new Monad[Try] {
    override def pure[A](x: A): Try[A] = Try.pure(x)

    override def flatMap[A, B](xs: Try[A])(f: A => Try[B]): Try[B] =
      xs.flatMap(f)
  }

  implicit val listInstance: Monad[List] = new Monad[List] {
    override def pure[A](x: A): List[A] = List.pure(x)

    override def flatMap[A, B](xs: List[A])(f: A => List[B]): List[B] =
      xs.flatMap(f)
  }

  implicit def eitherInstance[A]: Monad[({type E[B] = Either[A, B]})#E] = new Monad[({type E[B] = Either[A, B]})#E] {
    override def pure[B](x: B): Either[A, B] = Either.pure(x)

    override def flatMap[B, C](xs: Either[A, B])(f: B => Either[A, C]): Either[A, C] = xs.flatMap(f)
  }

  implicit def writerInstance[L](implicit m: Monoid[L]): Monad[({type W[A] = Writer[L, A]})#W] = new Monad[({type W[A] = Writer[L, A]})#W] {
    override def pure[A](x: A): Writer[L, A] = Writer.pure(x)

    override def flatMap[A, B](xs: Writer[L, A])(f: A => Writer[L, B]): Writer[L, B] = xs.flatMap(f)
  }

  implicit def readerInstance[E]: Monad[({type R[A] = Reader[E, A]})#R] = new Monad[({type R[A] = Reader[E, A]})#R] {
    override def pure[A](x: A): Reader[E, A] = Reader.pure(x)

    override def flatMap[A, B](xs: Reader[E, A])(f: A => Reader[E, B]): Reader[E, B] = xs.flatMap(f)
  }

}
