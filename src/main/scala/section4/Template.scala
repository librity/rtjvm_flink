package section4

import org.apache.flink.streaming.api.scala._

object Template {
  /**
   * Boilerplate
   */
  val env = StreamExecutionEnvironment.getExecutionEnvironment
  val data = env.fromElements(1 to 1000: _*)
  data.print()
  env.execute()


  def main(args: Array[String]): Unit = {

  }
}
