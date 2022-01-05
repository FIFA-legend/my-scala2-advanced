package lectures.part5ts

object Variance extends App {

  trait Animal
  class Dog extends Animal
  class Cat extends Animal
  class Crocodile extends Animal

  // what is variance?
  // "inheritance" - type substitution of generics

  class Cage[T]

  // yes - covariance
  class CCage[+T]
  val cCage: CCage[Animal] = new CCage[Cat]

  // no - invariance
  class ICage[T]
  // val iCage: ICage[Animal] = new ICage[Cat]
  // val x: Int = "Hello

  // hell no - opposite = contravariance
  class XCage[-T]
  val xCage: XCage[Cat] = new XCage[Animal]

  class InvariantCage[T](animal: T) // invariant

  // covariant positions
  class CovariantCage[+T](val animal: T) // COVARIANT POSITION

  // class ContravariantCage[-T](val animal: T)
  /*
    val catCage: ContravariantCage[Cat] = new ContravariantCage[Animal](new Crocodile)
   */

  // class CovariantVariableCage[+T](var animal: T) // types of vars are in CONTRAVARIANT POSITION
  /*
    val ccage: CovariantVariableCage[Animal] = new CovariantVariableCage[Cat](new Cat)
    ccage.animal = new Crocodile
   */

  // class ContravariantVariableCage[-T](var animal: T) // also in COVARIANT POSITION
  /*
    val catCage: ContravariantVariableCage[Cat] = new ContravariantVariableCage[Animal](new Crocodile)
   */

  class InvariantVariableCage[T](var animal: T) // ok

//  trait AnotherCovariantCage[+T] {
//    def addAnimal(animal: T) // CONTRAVARIANT POSITION
//  }
  /*
    val ccage: AnotherCovariantCage[Animal] = new AnotherCovariantCage[Dog]
    ccage.add(new Cat)
   */

  class AnotherContravariantCage[-T] {
    def addAnimal(animal: T) = true
  }
  val acc: AnotherContravariantCage[Cat] = new AnotherContravariantCage[Animal]
  acc.addAnimal(new Cat)
  class Kitty extends Cat
  acc.addAnimal(new Kitty)

  class MyList[+A] {
    def add[B >: A](element: B): MyList[B] = new MyList[B] // widening the type
  }

  val emptyList = new MyList[Kitty]
  val animals = emptyList.add(new Kitty)
  val moreAnimals = animals.add(new Cat)
  val evenMoreAnimals = moreAnimals.add(new Dog)

  // METHOD ARGUMENTS ARE IN CONTRAVARIANT POSITION.

  // return types
  class PetShop[-T] {
    // def get(isItAPuppy: Boolean): T // METHOD RETURN TYPES ARE IN COVARIANT POSITION
    /*
      val catShop = new PetShop[Animal] {
        def get(isItAPuppy: Boolean): Animal = new Cat
      }

      val dogShop: PetShop[Dog] = catShop
      dogShop.get(true) // EVIL CAT!
     */

    def get[S <: T](isItAPuppy: Boolean, defaultAnimal: S): S = defaultAnimal
  }

  val shop: PetShop[Dog] = new PetShop[Animal]
  // val evilCat = shop.get(true, new Cat)
  class TerraNova extends Dog
  val bigFurry = shop.get(isItAPuppy = true, new TerraNova)

  /*
    Big rule
      - method arguments are in CONTRAVARIANT position
      - return types are in COVARIANT position
   */

  /**
   * 1. Invariant, covariant, contravariant
   *  Parking[T](things List[T]) {
   *    park(vehicle: T)
   *    impound(vehicles: List[T])
   *    checkVehicles(conditions: String): List[T]
   *  }
   *
   * 2. Used someone else's API: IList[T]
   * 3. Parking = monad!
   *    - flatMap
   */
  class Vehicle
  class Bike extends Vehicle
  class Car extends Vehicle

  class IList[T]

  class IParking[T](vehicles: List[T]) {
    def park(vehicle: T): IParking[T] = ???
    def impound(vehicles: List[T]): IParking[T] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => IParking[S]): IParking[S] = ???
  }

  class CParking[+T](vehicles: List[T]) {
    def park[TT >: T](vehicle: TT): CParking[TT] = ???
    def impound[TT >: T](vehicles: List[TT]): CParking[TT] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => CParking[S]): CParking[S] = ???
  }

  class XParking[-T](vehicles: List[T]) {
    def park(vehicle: T): XParking[T] = ???
    def impound(vehicles: List[T]): XParking[T] = ???
    def checkVehicles[TT <: T](condition: String): List[TT] = ???

    def flatMap[R <: T, S](f: R => XParking[S]): XParking[S] = ???
  }

  /*
    Rule of thumb
    - use covariance = COLLECTION OF THINGS
    - use contravariance = GROUP AF ACTIONS
   */

  class CParking2[+T](vehicles: IList[T]) {
    def park[TT >: T](vehicle: TT): CParking2[TT] = ???
    def impound[TT >: T](vehicles: IList[TT]): CParking2[TT] = ???
    def checkVehicles[TT >: T](conditions: String): IList[TT] = ???
  }

  class XParking2[-T](vehicles: IList[T]) {
    def park(vehicle: T): XParking2[T] = ???
    def impound[TT <: T](vehicles: IList[TT]): XParking2[TT] = ???
    def checkVehicles[TT <: T](condition: String): IList[TT] = ???
  }

  // flatMap

}
