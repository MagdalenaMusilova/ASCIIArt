package UI.Controllers

import ASCIIConvertor.SequenceASCIIConvertor
import IO.StringOutput
import ImageExporters.OutputImageExporter
import ImageFilters.{FlipFilter, MultiFilter}
import ImageLoaders.FileImageLoader
import UI.Commands.ConvertImageData
import org.scalatest.FunSuite

class ConsoleControllerTest extends FunSuite{
  test("ConsoleControllerTestExitHelpCommands") {
    val out = new StringOutput
    val consoleController = new ConsoleController(out)
    consoleController.ShowHelp()
    assert(out.ReadLine().nonEmpty)
    out.Flush()
    true
  }

  test("ConsoleControllerTest") {
    val consoleController = new ConsoleController(new StringOutput)
    val command = new ConvertImageData
    command.imageLoader = new FileImageLoader("loaderTest2.png")
    command.ASCIIConvertor = new SequenceASCIIConvertor("XIO")
    command.imageFilter = new MultiFilter(new FlipFilter(true), new FlipFilter(false))
    val imageOutput = new StringOutput
    command.imageExporter = new OutputImageExporter(imageOutput)
    consoleController.ConvertImage(command)

    val expectedRes = Vector(
      "OII",
      "XOX",
      "IXO"
    )
    assert((0 until 3).forall(y => imageOutput.ReadLine() == expectedRes(y)))
  }
}
