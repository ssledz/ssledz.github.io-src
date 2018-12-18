package pl.softech.learning.fp

case class ListT[F[_], A](value: F[List[A]]) {

  def flatMap[B](f: A => ListT[F, B])(implicit F: Monad[F]): ListT[F, B] = flatMapF(a => f(a).value)

  def flatMapF[B](f: A => F[List[B]])(implicit F: Monad[F]): ListT[F, B] = ListT(F.flatMap(value) { as =>
    as.foldLeft(F.pure(List.empty[B])) { (acc, a) =>
      F.flatMap(acc) { bs =>
        F.flatMap(f(a))(bst => F.pure(bs ::: bst))
      }
    }
  })

  def map[B](f: A => B)(implicit m: Monad[F]): ListT[F, B] = ListT(m.map(value) { x => x.map(f) })

}

case class OptionT[F[_], A](value: F[Option[A]]) {

  def flatMap[B](f: A => OptionT[F, B])(implicit m: Monad[F]): OptionT[F, B] = flatMapF(a => f(a).value)

  def flatMapF[B](f: A => F[Option[B]])(implicit F: Monad[F]): OptionT[F, B] = OptionT(
    F.flatMap(value) { as =>
      as match {
        case Some(a) => f(a)
        case _ => F.pure(None)
      }
    }
  )

  def map[B](f: A => B)(implicit F: Monad[F]): OptionT[F, B] = OptionT(F.map(value) { x => x.map(f) })

}

