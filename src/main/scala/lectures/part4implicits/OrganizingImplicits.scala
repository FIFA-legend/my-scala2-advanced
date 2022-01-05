package lectures.part4implicits

object OrganizingImplicits extends App {

  implicit def reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  // implicit val normalOrdering: Ordering[Int] = Ordering.fromLessThan(_ < _)
  println(List(1, 4, 5, 3, 2).sorted)

  // scala.Predef

  /*
    Implicits (used as implicit parameters):
    - val/var
    - object
    - accessor methods = defs with no parentheses
   */

  // Exercise
  case class Person(name: String, age: Int)

  val persons = List(
    Person("Steve", 30),
    Person("Amy", 22),
    Person("John", 66),
  )

//  object Person {
//    implicit val personsOrdering: Ordering[Person] = Ordering.fromLessThan(_.name < _.name)
//  }

  // implicit val ageOrdering: Ordering[Person] = Ordering.fromLessThan(_.age < _.age)
  // println(persons.sorted)

  /*
    Implicit scope
    - normal scope = LOCAL SCOPE
    - imported scope
    - companions of all types involved in the method signature
      - List
      - Ordering
      - all the types involved = A or any supertype
   */
  // def sorted[B >: A](implicit ord: Ordering[B]): List[B]

  /*
    Best practices
    - define implicit if there is a single possible value for it
    then define the implicit in the companion
    - if there are many possible values for it but a single good one
    then define the good implicit in the companion
   */

  object AlphabeticNameOrdering {
    implicit val personsOrdering: Ordering[Person] = Ordering.fromLessThan(_.name < _.name)
  }

  object AgeOrdering {
    implicit val ageOrdering: Ordering[Person] = Ordering.fromLessThan(_.age < _.age)
  }

  import AlphabeticNameOrdering._
  println(persons.sorted)

  /*
    Exercise.

    - totalPrice = most used (50%)
    - by unit count = 25%
    - by unit price = 25%

   */
  case class Purchase(nUnits: Int, unitPrice: Double)

  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((p1, p2) =>
      p1.nUnits * p1.unitPrice > p2.nUnits * p2.unitPrice
    )
  }

  object UnitCountOrdering {
    implicit val unitCountOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.nUnits > _.nUnits)
  }

  object UnitPriceOrdering {
    implicit val unitPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.unitPrice > _.unitPrice)
  }

}
