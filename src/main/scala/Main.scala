package Main

import IO.{ConsoleInput, ConsoleOutput}
import UI.Views.ConsoleView

object Main extends App {
  new ConsoleView(new ConsoleInput, new ConsoleOutput).Start()
}