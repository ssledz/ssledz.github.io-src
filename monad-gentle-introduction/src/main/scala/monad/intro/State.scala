package monad.intro

case class State[S, A](run: S => (S, A)) {

  def flatMap[B](f: A => State[S, B]): State[S, B] = ???

  def map[B](f: A => B): State[S, B] = flatMap(a => State.pure(f(a)))

}

object State {

  def pure[S, A](a: A): State[S, A] = State(s => (s, a))

}
