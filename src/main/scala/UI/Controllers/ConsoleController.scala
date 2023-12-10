package UI.Controllers

import ASCIIConvertor.SequenceASCIIConvertor
import IO.Output
import ImageExporters.FileImageExporter
import ImageFilters.InvertFilter
import ImageLoaders.FileImageLoader
import UI.Commands.CommandTypes.{CONVERTIMAGE, CommandType, EXIT, HELP}
import UI.Commands.ConvertImageCommandTypes.{CONVERTOR, ConvertImageCommandType, EXPORTER, FILTER, LOADER}
import UI.Commands.{CICommand, Command, ConvertImageData}

class ConsoleController(output : Output) extends UIController {
  def CommandsByType : Map[CommandType, Vector[Command]] = Map(
    EXIT -> Vector(Command("--exit", Vector.empty, "Lists all available commands")),
    HELP -> Vector(Command("--help", Vector.empty, "Turns off program")),
    CONVERTIMAGE -> CICommandsByType.values.flatten.toVector
  )
  def CICommandsByType : Map[ConvertImageCommandType, Vector[Command]] = Map(
    LOADER -> Vector(new CICommand[FileImageLoader]("--image", Vector("[pathToFile]"), "Loads image stored in file", new FileImageLoader(""))),
    EXPORTER -> Vector(new CICommand[FileImageExporter]("--export-to-file", Vector("[pathToFile]"), "Stores image in file",new FileImageExporter(""))),
    CONVERTOR -> Vector(new CICommand[SequenceASCIIConvertor]("--sequence-shader", Vector("sequence"), "Uses given sequence as shader for ASCII art sequence", new SequenceASCIIConvertor(""))),
    FILTER -> Vector(new CICommand[InvertFilter]("--invert", Vector(), "Invert the colors of the image", new InvertFilter))
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
