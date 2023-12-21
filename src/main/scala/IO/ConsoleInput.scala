package IO

import scala.io.StdIn.readLine

/**
 * Input from console
 * @param initArguments Arguments given at the start of the program (program arguments)
 */
class ConsoleInput(initArguments: Seq[String]) extends Input {
  override def ReadInputArgs(): Seq[String] = {
    initArguments
  }
}
