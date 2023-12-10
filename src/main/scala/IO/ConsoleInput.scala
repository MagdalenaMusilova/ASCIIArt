package IO

import scala.io.StdIn.readLine

class ConsoleInput extends Input {
  override def ReadLine(): String = {
    readLine()
  }
}
