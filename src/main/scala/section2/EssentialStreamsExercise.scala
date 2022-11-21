package section2

import org.apache.flink.api.common.serialization.SimpleStringEncoder
import org.apache.flink.core.fs.Path
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink
import org.apache.flink.streaming.api.scala._

object EssentialStreamsExercise {

  /**
   * Exercise 1 - FizzBuzz
   *
   * 1. Take a stream of 100 natural numbers
   * 2. For every number:
   *    - if divisible by 3 => "fizz"
   *    - if divisible by 5 => "buzz"
   *    - if divisible by 3 and 5 => "fizzbuzz"
   *
   * 3. Print/write "fizzbuzz" numbers to a file
   */
  def fizzBuzzExercise() = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment


    val numbers = (1 to 100).toList
    val stream = env.fromCollection(numbers)


    val fb = stream
      .map { number =>
        val byThree = number % 3 == 0
        val byFive = number % 5 == 0

        val word = if (byThree && byFive) "fizzbuzz"
        else if (byThree) "fizz"
        else if (byFive) "buzz"
        else ""

        (number, word)
      }
      .filter(_._2 == "fizzbuzz")
    //    fb.print()
    fb.writeAsText("output/fizzbuzz")


    env.execute()
  }


  /**
   * Daniel's Solution
   */
  case class FizzBuzzResult(number: Long, output: String)

  def danielsFizzBuzz() = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val stream = env.fromSequence(1, 100)


    val fb = stream
      .map[FizzBuzzResult] { (number: Long) =>
        number match {
          case n if n % 3 == 0 && n % 5 == 0 => FizzBuzzResult(n, "fizzbuzz")
          case n if n % 3 == 0 => FizzBuzzResult(n, "fizz")
          case n if n % 5 == 0 => FizzBuzzResult(n, "buzz")
          case n => FizzBuzzResult(n, s"$n")
        }
      }
      //      .map { number =>
      //        val byThree = number % 3 == 0
      //        val byFive = number % 5 == 0
      //
      //        val output =
      //          if (byThree && byFive) "fizzbuzz"
      //          else if (byThree) "fizz"
      //          else if (byFive) "buzz"
      //          else number.toString
      //
      //        FizzBuzzResult(number, output)
      //      }
      .filter(_.output == "fizzbuzz")
      .map(_.number)


    //    fb.print()
    //    fb.writeAsText("output/fizzbuzz").setParallelism(1)
    /**
     * Write to file with a Sink
     */
    fb.addSink(
      StreamingFileSink.forRowFormat(
        new Path("output/streaming_sink"),
        new SimpleStringEncoder[Long]("UTF-8"),
      ).build()
    ).setParallelism(1)


    env.execute()
  }


  def main(args: Array[String]): Unit = {
    //    fizzBuzzExercise()
    danielsFizzBuzz()
  }
}
