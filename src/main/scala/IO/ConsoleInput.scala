package IO

import scala.io.StdIn.readLine

class ConsoleInput(initArguments: Seq[String]) extends Input {
  override def ReadInputArgs(): Seq[String] = {
    initArguments
  }
}
