package pl.softech.learning.fp

case class ListT[F[_], A](value: F[List[A]]) {

  def flatMap[B](f: A => ListT[F, B])(implicit m: Monad[F]): ListT[F, B] = flatMapF(a => f(a).value)

  def flatMapF[B](f: A => F[List[B]])(implicit m: Monad[F]): ListT[F, B] = ListT(m.flatMap(value) { as =>
    as.foldLeft(m.pure(List.empty[B])) { (acc, a) =>
      m.flatMap(acc) { bs =>
        m.flatMap(f(a))(bst => m.pure(bs ::: bst ))
      }
    }
  })

  def map[B](f: A => B)(implicit m: Monad[F]): ListT[F, B] = ListT(m.map(value) { x => x.map(f) })

}

