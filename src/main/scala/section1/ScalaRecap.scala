package section1

import org.apache.flink.streaming.api.scala._

import java.util.concurrent.{Executor, Executors}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object ScalaRecap {
  val aBoolean: Boolean = true
  var aVariable: Int = 41
  aVariable += 1


  val expression: String = if (2 > 3) "bigger" else "smaller"
  val theUnit: Unit = println("hello")


  class Animal

  class Cat extends Animal

  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(animal: Animal): Unit = println("Eating this poor fellow")
  }


  object MySingleton

  object Crocodile


  case class Person(name: String, age: Int)


  /**
   * Generics
   */
  class MyList[A]


  val croc = new Crocodile
  croc.eat(new Animal)
  croc eat new Animal


  val three = 1 + 2
  val threeV2 = 1.+(2)


  val incrementer: Int => Int = x => x + 1
  val incremented = incrementer(5)


  val processedList = List(1, 2, 3).map(incrementer)
  val longerList = List(1, 2, 3).flatMap(x => List(x, x + 1))


  val checkerboard = List(1, 2, 3)
    .flatMap(n =>
      List('a', 'b', 'c').map(c => (n, c))
    )


  val checkerboardV2 = for {
    n <- List(1, 2, 3)
    c <- List('a', 'b', 'c')
  } yield (n, c)


  val anOption: Option[Int] = Option(42)
  val doubleOption = anOption.map(_ * 2)


  val aTry: Try[Int] = Try(12)
  val doubleTry = aTry.map(_ * 10)


  val unkown: Any = 45
  val medal = unkown match {
    case 1 => "gold"
    case 2 => "silver"
    case 3 => "bronze"
    case _ => "no medal"
  }


  val optionDescription = anOption match {
    case Some(value) => s"Option not empty: $value"
    case None => "Empty option"
  }


  val ec: ExecutionContext = ExecutionContext
    .fromExecutorService(Executors.newFixedThreadPool(8))
  val aFuture = Future(1 + 999)(ec)
  aFuture.onComplete { t =>
    t match {
      case Success(value) => s"Balling: $value"
      case Failure(exception) => s"Dab on em: $exception"
    }
  }(ec)


  val partialFunction: PartialFunction[Try[Int], Unit] = {
    case Success(value) => s"Balling: $value"
    case Failure(exception) => s"Dab on em: $exception"
  }


  val doubleAsyncMOL: Future[Int] = aFuture.map(_ * 2)(ec)


  implicit val timeout: Int = 3000

  def setTimeout(f: () => Unit)(implicit tmout: Int) = {
    Thread.sleep(tmout)
    f()
  }

  setTimeout(() => println("Timeout!"))


  implicit class MyRichInt(number: Int) {
    def isEven: Boolean = number % 2 == 0
  }

  val is2Even = 2.isEven


  implicit def string2Person(name: String): Person = new Person(name, 57)

  val daniel: Person = "Daniel"


  def main(args: Array[String]): Unit = {

  }
}
