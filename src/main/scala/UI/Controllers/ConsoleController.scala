package UI.Controllers

import IO.Output
import UI.Commands.CommandTypes.{CONVERTIMAGE, CommandType, EXIT, HELP}
import UI.Commands.ConvertImageCommandTypes.{CONVERTOR, ConvertImageCommandType, EXPORTER, FILTER, LOADER}
import UI.Commands.ExactCommandType.{BRIGHTNESSFILTER, DEFAULTCONVERTOR, FILEEXPORTER, FILELOADER, FIXRATIO, FLIPFILTER, INVERTFILTER, OUTPUTEXPORTER, RANDOMLOADER, RANGECONVERTOR, ROTATEFILTER, SCALEFILTER, SEQUENCECONVERTOR}
import UI.Commands.{Command, ConvertImageData, ExactCommandType}

class ConsoleController(output : Output) extends UIController {
  def CommandsByType : Map[CommandType, Vector[Command]] = Map(
    HELP -> Vector(Command("--help", Vector.empty, "Turns off program", ExactCommandType.HELP)),
    EXIT -> Vector(Command("--exit", Vector.empty, "Lists all available commands", ExactCommandType.EXIT)),
    CONVERTIMAGE -> CICommandsByType.values.flatten.toVector
  )
  def CICommandsByType : Map[ConvertImageCommandType, Vector[Command]] = Map(
    LOADER -> Vector(
      Command("--image", Vector("[pathToFile]"), "Loads image stored in file", FILELOADER),
      Command("--image-random", Vector(), "Creates random image", RANDOMLOADER)
    ),
    EXPORTER -> Vector(
      Command("--output-file", Vector("[pathToFile]"), "Stores image in file", FILEEXPORTER),
      Command("--output-console", Vector(), "Prints image to console", OUTPUTEXPORTER)
    ),
    CONVERTOR -> Vector(
      Command("--table", Vector("[tableName]"), "Uses predefined table to convert image to ASCII art", DEFAULTCONVERTOR),
      Command("--custom-table", Vector("[characters]"), "Uses given sequence to convert image to ASCII art", SEQUENCECONVERTOR),
      Command("--nonlinear-table", Vector("[pathToFile]"), "Uses non-linear table defined in the given file to convert image to ASCII art", RANGECONVERTOR)
    ),
    FILTER -> Vector(
      Command("--rotate", Vector("[degrees]"), "Rotate image by given degrees", ROTATEFILTER),
      Command("--scale", Vector("[scaleValue]"), "Scales/shrinks image by given amount", SCALEFILTER),
      Command("--invert", Vector(), "Invert the colors of the image", INVERTFILTER),
      Command("--flip", Vector("[axis]"), "Flip image on x or y axis", FLIPFILTER),
      Command("--brightness", Vector("[brightnessValue]"), "Increases/decreases brightness of image by given value", BRIGHTNESSFILTER),
      Command("--fix-ratio", Vector(), "Fixes the fact that pixel should be 1:1, but a character is 1:2", FIXRATIO)
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
