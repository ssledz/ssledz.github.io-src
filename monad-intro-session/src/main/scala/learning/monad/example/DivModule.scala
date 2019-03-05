package learning.monad.example

object DivModule {

  def parse(x: String): Double = {

    if (x == null) {
      throw new IllegalArgumentException("arg can't be null")
    }

    x.toDouble

  }

  def div(x: String, y: String, z: String): Double = {

    if (x == null || y == null || z == null) {
      throw new IllegalArgumentException("(x | y | z) can't be null")
    }

    val xx = parse(x)

    val yy = parse(y)

    val zz = parse(z)

    if (yy == 0 || zz == 0) {
      throw new IllegalArgumentException("y or z can't be 0")
    }

    xx / yy / zz

  }

  type Fun3 = (String, String, String) => Double

  def lift(f: Fun3, defaultValue: Double): Fun3 = (x, y, z) =>
    try {
      f(x, y, z)
    } catch {
      case e: Throwable => defaultValue
    }


}

