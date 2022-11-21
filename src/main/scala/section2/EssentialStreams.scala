package section2

import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, DataStream}

object EssentialStreams {

  /**
   * Flink App Template
   *
   * 1. Get the Execution Environment
   * 2. Get a Data Stream
   * 3. Define computations
   * 4. Call env.execute()
   */
  def applicationTemplate() = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    /**
     * Import Type Information implicits
     */
    import org.apache.flink.streaming.api.scala._
    val simpleNumberStream: DataStream[Int] = env.fromElements(1, 2, 3, 4)
    simpleNumberStream.print()

    env.execute()
  }


  /**
   * Transformations
   */
  def demoTransformations() = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment


    /**
     * Changing env Parallelism
     */
    println(s"Current parallelism: ${env.getParallelism}")
    env.setParallelism(2)
    println(s"Current parallelism: ${env.getParallelism}")


    import org.apache.flink.streaming.api.scala._
    val numbers = env.fromElements(1, 2, 3, 4, 5)


    val doubledNumbers = numbers.map(_ * 2)
    /**
     * This transformation will use 4 threads
     */
    val expandedNumbers = numbers
      .flatMap(number => List(number, number + 1))
      .setParallelism(4)
    val filteredNumbers = numbers.filter(_ % 2 == 0)


    /**
     * The sink will use 3 threads (3 files)
     */
    val finalData = expandedNumbers.writeAsText("output/expanded_stream")
    finalData.setParallelism(3)


    env.execute()
  }


  def main(args: Array[String]): Unit = {
    //    applicationTemplate()
    demoTransformations()
  }
}
