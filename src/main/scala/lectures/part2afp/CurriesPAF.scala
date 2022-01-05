package lectures.part2afp

object CurriesPAF extends App {

  // curried functions
  val superAdder: Int => Int => Int =
    x => y => x + y

  val add3 = superAdder(3) // Int => Int = y => 3 + y
  println(add3(5))
  println(superAdder(3)(5)) // curried function

  // METHOD!
  def curriedAdder(x: Int)(y: Int): Int = x + y // curried method

  val add4: Int => Int = curriedAdder(4)
  // lifting = ETA-EXPANSION

  // functions != methods (JVM limitation)
  def inc(x: Int) = x + 1
  List(1, 2, 3).map(inc) // ETA-expansion

  // Partial function applications
  val add5 = curriedAdder(5) _ // Int => Int

  // EXERCISE
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleMethod(x: Int, y: Int) = x + y
  def curriedMethod(x: Int)(y: Int) = x + y

  // add7: Int => Int = y => 7 + y
  // as many different implementations of add7 using the above
  val add7 = simpleAddFunction(7, _)
  val add7_1 = simpleMethod(7, _)
  val add7_2 = curriedMethod(7)(_) // PAF
  val add7_3: Int => Int = curriedAdder(7)
  val add7_4 = (x: Int) => simpleAddFunction(7, x) // simplest
  val add7_5 = simpleAddFunction.curried(7)

  // underscores are powerful
  def concatenator(a: String, b: String, c: String) = a + b + c
  val insertName = concatenator("Hello, I'm ", _, ", how are you?") // x: String => concatenator("hello", x, "how are you")
  println(insertName("Nikita"))

  val fillInTheBlanks = concatenator("Hello, ", _, _) // (x, y) => concatenator("Hello", x, y)
  println(fillInTheBlanks("Nikita", " Scala is awesome"))

  // EXERCISES
  /*
    1. Process a list of numbers and return their string representations with different format
       Use the %4.2f, %8.6g and %14.12f with a curried formatter function
   */

  val list = List(2.3, Math.E, Math.PI)
  def format(num: Double)(formatter: String) = {
    formatter.format(num)
  }
  val format1 = format(_: Double)("%4.2f")
  val format2 = format(_: Double)("%8.6g")
  val format3 = format(_: Double)("%14.12f")

  list.map(format1).foreach(println)
  list.map(format2).foreach(println)
  list.map(format3).foreach(println)

  /*
    2. Difference between
      - functions vs methods
      - parameters: by-name vs 0-lambda
   */
  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  /*
    calling byName and byFunction
    - int
    - method
    - parenMethod
    - lambda
    - PAF
   */
  byName(23) // ok
  byName(method) // ok
  byName(parenMethod())
  byName(parenMethod) // ok but beware ==> byName(parenMethod())
  // byName(() => 42) // not ok
  byName((() => 42)())
  // byName(parenMethod _) // not ok

  // byFunction(45) // not ok
  // byFunction(method) // not ok!!! does not do ETA-expansion
  byFunction(parenMethod) // compiler does ETX-expansion
  byFunction(() => 46) // works
  byFunction(parenMethod _) // also works
}
