package learning.monad.example

object DataModule {

  def flatten[A](xs: List[((A, A), A)]): List[(A, A, A)] = for {
    ((x1, x2), x3) <- xs
  } yield (x1, x2, x3)

  def map3[A, B](x: (A, A, A))(f: A => B): (B, B, B) = x match {
    case (x1, x2, x3) => (f(x1), f(x2), f(x3))
  }

}
