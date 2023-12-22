package Main

import IO.{ConsoleInput, ConsoleOutput}
import UI.Views.ConsoleView

object Main extends App {
  val input = new ConsoleInput(args)
  val output = new ConsoleOutput
  try {
    new ConsoleView(input, output).Start()
  } catch {
    case e: Throwable =>
      output.PrintLn("Failed to convert image")
      output.PrintLn(s"Error: ${e.getMessage}")
  }
}