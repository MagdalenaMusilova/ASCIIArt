package IO

import scala.io.StdIn.readLine

class ConsoleInput(initArguments: Vector[String]) extends Input {
  override def ReadLine(): Vector[String] = {
    initArguments
  }
}
