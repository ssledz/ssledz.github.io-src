package monad.intro

trait Monoid[M] {

  def combine(x: M, y: M): M

  def empty: M

}

object MonoidInstances {

  //  implicit val listInstance: Monoid[List[_]] = new Monoid[List[_]] {
  //
  //    override def combine(x: List[_], y: List[_]): List[_] = x ::: y
  //
  //    override def empty: List[_] = List.empty
  //  }

  implicit def listInstance[A]: Monoid[List[A]] = new Monoid[List[A]] {

    override def combine(x: List[A], y: List[A]): List[A] = x ::: y

    override def empty: List[A] = List.empty
  }

}
