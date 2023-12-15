package Main

import IO.{ConsoleInput, ConsoleOutput}
import UI.Views.ConsoleView

object Main extends App {
  new ConsoleView(new ConsoleInput(args.toVector), new ConsoleOutput).Start()
}