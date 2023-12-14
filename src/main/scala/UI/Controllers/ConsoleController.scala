package UI.Controllers

import ASCIIConvertor.{DefaultASCIIConvertor, RangeASCIIConvertor, SequenceASCIIConvertor}
import IO.Output
import ImageExporters.{FileImageExporter, OutputImageExporter}
import ImageFilters.{BrightnessFilter, FlipFilter, InvertFilter, RotateFilter, ScaleFilter}
import ImageLoaders.{FileImageLoader, RandomImageLoader}
import UI.Commands.CommandTypes.{CONVERTIMAGE, CommandType, EXIT, HELP}
import UI.Commands.ConvertImageCommandTypes.{CONVERTOR, ConvertImageCommandType, EXPORTER, FILTER, LOADER}
import UI.Commands.{CICommand, Command, ConvertImageData}

import scala.util.hashing.Hashing.Default

class ConsoleController(output : Output) extends UIController {
  def CommandsByType : Map[CommandType, Vector[Command]] = Map(
    EXIT -> Vector(Command("--exit", Vector.empty, "Lists all available commands")),
    HELP -> Vector(Command("--help", Vector.empty, "Turns off program")),
    CONVERTIMAGE -> CICommandsByType.values.flatten.toVector
  )
  def CICommandsByType : Map[ConvertImageCommandType, Vector[Command]] = Map(
    LOADER -> Vector(
      new CICommand[FileImageLoader]("--image", Vector("[pathToFile]"), "Loads image stored in file", new FileImageLoader("")),
      new CICommand[RandomImageLoader]("--image-random", Vector(), "Creates random image", new RandomImageLoader())
    ),
    EXPORTER -> Vector(
      new CICommand[FileImageExporter]("--output-file", Vector("[pathToFile]"), "Stores image in file", new FileImageExporter("")),
      new CICommand[OutputImageExporter]("--output-console", Vector(), "Prints image to console", new OutputImageExporter(output))
    ),
    CONVERTOR -> Vector(
      new CICommand[DefaultASCIIConvertor]("--table", Vector("[tableName]"), "Uses predefined values to convert image to ASCII art", new DefaultASCIIConvertor()),
      new CICommand[SequenceASCIIConvertor]("--custom-table", Vector("[characters]"), "Uses given sequence to convert image to ASCII art", new SequenceASCIIConvertor("")),
      new CICommand[RangeASCIIConvertor]("--nonlinear-table", Vector("[pathToFile]"), "Uses non-linear table defined in the given file to convert image to ASCII art", new RangeASCIIConvertor(Map()))
    ),
    FILTER -> Vector(
      new CICommand[RotateFilter]("--rotate", Vector("[degrees]"), "Rotate image by given degrees", new RotateFilter(0)),
      new CICommand[ScaleFilter]("--scale", Vector("[scaleValue]"), "Scales/shrinks image by given amount", new ScaleFilter(0)),
      new CICommand[InvertFilter]("--invert", Vector(), "Invert the colors of the image", new InvertFilter),
      new CICommand[FlipFilter]("--flip", Vector("[axis]"), "Flip image on x or y axis", new FlipFilter(true)),
      new CICommand[BrightnessFilter]("--brightness", Vector("[brightnessValue]"), "Increases/decreases brightness of image by given value", new BrightnessFilter(0))
    )
  )

  private def CommandToOneLine(commands : Vector[Command]) : Vector[String] = {
    val bestPadding = commands.maxBy(x => x.RecommendedPadding).RecommendedPadding
    commands.map(c => c.ToOneLine(bestPadding))
  }

  override def ShowHelp(): Unit = {
    output.PrintLn("Welcome to ASCII art convertor!")
    output.PrintLn("Commands for image conversion (order doesn't matter):")
    output.PrintLn("   Image source (exactly 1):")
    output.PrintLines(CommandToOneLine(CICommandsByType(LOADER)), "   ")
    output.PrintLn("   Image export (at least 1):")
    output.PrintLines(CommandToOneLine(CICommandsByType(EXPORTER)), "   ")
    output.PrintLn("   ASCII art shader (at most 1, if empty default):")
    output.PrintLines(CommandToOneLine(CICommandsByType(CONVERTOR)), "   ")
    output.PrintLn("   Image filters (unlimited, can be empty):")
    output.PrintLines(CommandToOneLine(CICommandsByType(FILTER)), "   ")
    output.PrintLn("Other commands:")
    output.PrintLines(CommandToOneLine(CommandsByType(HELP).concat(CommandsByType(EXIT))), "   ")
  }

  override def Stop(): Unit = {
    output.PrintLn("Exiting program...")
  }

  override def ConvertImage(command: ConvertImageData): Unit = {
    val bitmapImage = command.imageLoader.Get()
    val image = command.ASCIIConvertor.GetASCIIArt(bitmapImage)
    val editedImage = command.imageFilter.EditImage(image)
    command.imageExporter.Export(editedImage)
  }
}
