package UI.Views

import IO.{StringInput, StringOutput}
import org.scalatest.FunSuite
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.Span

import java.io.File
import scala.concurrent.duration.DurationInt
import scala.io.Source
import scala.language.postfixOps

class ConsoleViewTest extends FunSuite with TimeLimitedTests {
  override def timeLimit: Span = 10 seconds

  test("ConsoleViewTest") {
    val input = new StringInput
    val output = new StringOutput
    val consoleView = new ConsoleView(input, output)

    input.WriteLine(Seq("--image", "loaderTest2.png", "--output-file", "tmp.txt", "--custom-table", "XIO", "--invert"))

    consoleView.Start()

    val source = Source.fromFile("tmp.txt")
    val res = source.getLines.toSeq
    source.close()
    new File("tmp.txt").delete()

    val expectedRes = Seq(
      "XOI",
      "OXO",
      "IIX"
    )
    assert(res.equals(expectedRes))
  }

  test("ConsoleViewTestUnknownCommand") {
    val input = new StringInput
    val output = new StringOutput
    val consoleView = new ConsoleView(input, output)

    input.WriteLine(Seq("--image", "loaderTest2.png", "--output-file", "tmp.txt", "--custom-table", "XIO", "--invert", "--IDK"))

    intercept[Exception] {
      consoleView.Start()
    }
  }

  test("ConsoleViewTestInvalidArgument") {
    val input = new StringInput
    val output = new StringOutput
    val consoleView = new ConsoleView(input, output)

    input.WriteLine(Seq("--image", "loaderTest2.png", "--output-file", "tmp.txt", "--custom-table", "XIO", "--invert", "--flip", "S"))

    intercept[Exception] {
      consoleView.Start()
    }
  }

  test("ConsoleViewTestMissingCommand") {
    val input = new StringInput
    val output = new StringOutput
    val consoleView = new ConsoleView(input, output)

    input.WriteLine(Seq("--output-file", "tmp.txt", "--custom-table", "XIO", "--invert"))

    intercept[Exception] {
      consoleView.Start()
    }
  }

  test("ConsoleViewTestNoFilter") {
    val input = new StringInput
    val output = new StringOutput
    val consoleView = new ConsoleView(input, output)

    input.WriteLine(Seq("--image", "loaderTest2.png", "--output-file", "tmp.txt", "--custom-table", "XIO"))

    consoleView.Start()

    val source = Source.fromFile("tmp.txt")
    val res = source.getLines.toSeq
    source.close()
    new File("tmp.txt").delete()

    val expectedRes = Seq(
      "OXI",
      "XOX",
      "IIO"
    )
    assert(res.equals(expectedRes))
  }

  test("ConsoleViewTestMultipleArguments") {
    val input = new StringInput
    val output = new StringOutput
    val consoleView = new ConsoleView(input, output)

    input.WriteLine(Seq(
      "--image", "loaderTest2.png",
      "--output-file", "tmp.txt", "--output-file", "tmp2.txt",
      "--custom-table", "XIO",
      "--invert", "--flip", "X", "--scale", "2"))

    consoleView.Start()

    val source1 = Source.fromFile("tmp.txt")
    val source2 = Source.fromFile("tmp2.txt")
    val res1 = source1.getLines.toSeq
    val res2 = source2.getLines.toSeq
    source1.close()
    source2.close()
    new File("tmp.txt").delete()
    new File("tmp2.txt").delete()

    val expectedRes = Seq(
      "IIIIXX",
      "IIIIXX",
      "OOXXOO",
      "OOXXOO",
      "XXOOII",
      "XXOOII",
    )
    assert(res1.equals(expectedRes) && res2.equals(expectedRes))
  }
}
