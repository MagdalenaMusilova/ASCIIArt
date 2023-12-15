package UI.Views

import IO.{StringInput, StringOutput}
import org.scalatest.FunSuite
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.Span

import java.io.File
import scala.concurrent.duration.DurationInt
import scala.io.Source
import scala.language.postfixOps

class ConsoleViewTest extends FunSuite with TimeLimitedTests{
  override def timeLimit: Span = 10 seconds

  test("Stop"){
    val input = new StringInput
    val output = new StringOutput
    val consoleView = new ConsoleView(input, output)

    input.WriteLine(Vector("--exit"))
    consoleView.Start()
    true

  }

  test("HelpConvertExit"){
    val input = new StringInput
    val output = new StringOutput
    val consoleView = new ConsoleView(input, output)

    input.WriteLine(Vector("--help"))
    input.WriteLine(Vector("--image", "loaderTest2.png", "--output-file", "tmp.txt", "--custom-table", "XIO", "--invert"))
    input.WriteLine(Vector("--exit"))

    consoleView.Start()

    val source = Source.fromFile("tmp.txt")
    val res = source.getLines.toVector
    source.close()
    new File("tmp.txt").delete()

    val expectedRes = Vector(
      "XOI",
      "OXO",
      "IIX"
    )
    assert(res.equals(expectedRes))
  }
}
