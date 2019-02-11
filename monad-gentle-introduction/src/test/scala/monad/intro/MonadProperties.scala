package monad.intro

import org.scalacheck.Properties


// monad laws
// https://wiki.haskell.org/Monad_laws

// 1. Left identity: return a >>= f ≡ f a
// 2. Right identity: m >>= return ≡ m
// 3. Associativity: (m >>= f) >>= g ≡ m >>= (\x -> f x >>= g)

object MonadProperties extends Properties("Monad") {
  include(ListMonadProperties)
}
