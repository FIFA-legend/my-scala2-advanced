package lectures.part5ts

object RockingInheritance extends App {

  // convenience
  trait Writer[T] {
    def write(value: T): Unit
  }

  trait Closeable {
    def close(status: Int): Unit
  }

  trait GenericStream[T] {
    // some methods
    def foreach(f: T => Unit): Unit
  }

  def processStream[T](stream: GenericStream[T] with Writer[T] with Closeable): Unit = {
    stream.foreach(println)
    stream.close(0)
  }

  // diamond problem
  trait Animal {
    def name: String
  }

  trait Lion extends Animal {
    override def name: String = "lion"
  }

  trait Tiger extends Animal {
    override def name: String = "tiger"
  }

  class Mutant extends Lion with Tiger

  val m = new Mutant
  println(m.name)

  /*
    Mutant
    extends Animal with { override def name: String = "lion" }
    with { override def name: String = "tiger" }

    LAST OVERRIDE GETS PICKED
   */

  // the super problem + type linearization
  trait Cold {
    def print = println("Cold")
  }

  trait Green extends Cold {
    override def print: Unit = {
      println("Green")
      super.print
    }
  }

  trait Blue extends Cold {
    override def print: Unit = {
      println("Blue")
      super.print
    }
  }

  class Red {
    def print = println("Red")
  }

  class White extends Red with Green with Blue {
    override def print: Unit = {
      println("White")
      super.print
    }
  }

  val color = new White
  color.print

}
