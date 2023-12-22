package UI.Controllers

import IO.Output
import UI.Commands.CommandTypes.{CONVERTIMAGE, CommandType, HELP}
import UI.Commands.ConvertImageCommandTypes.{CONVERTOR, ConvertImageCommandType, EXPORTER, FILTER, LOADER}
import UI.Commands.ExactCommandType.{BRIGHTNESSFILTER, DEFAULTCONVERTOR, FILEEXPORTER, FILELOADER, FIXRATIO, FLIPFILTER, INVERTFILTER, OUTPUTEXPORTER, RANDOMLOADER, RANGECONVERTOR, ROTATEFILTER, SCALEFILTER, SEQUENCECONVERTOR}
import UI.Commands.{Command, ConvertImageData, ExactCommandType}

class ConsoleController(output: Output) extends UIController {
  /**
   * All commands sorted by their general type
   */
  def CommandsByType: Map[CommandType, Seq[Command]] = Map(
    HELP -> Seq(Command("--help", Seq.empty, "Turns off program", ExactCommandType.HELP)),
    CONVERTIMAGE -> CICommandsByType.values.flatten.toSeq
  )

  /**
   * Sorted commands used for converting image
   */
  def CICommandsByType: Map[ConvertImageCommandType, Seq[Command]] = Map(
    LOADER -> Seq(
      Command("--image", Seq("[pathToFile]"), "Loads image stored in file", FILELOADER),
      Command("--image-random", Seq(), "Creates random image", RANDOMLOADER)
    ),
    EXPORTER -> Seq(
      Command("--output-file", Seq("[pathToFile]"), "Stores image in file", FILEEXPORTER),
      Command("--output-console", Seq(), "Prints image to console", OUTPUTEXPORTER)
    ),
    CONVERTOR -> Seq(
      Command("--table", Seq("[tableName]"), "Uses predefined table to convert image to ASCII art", DEFAULTCONVERTOR),
      Command("--custom-table", Seq("[characters]"), "Uses given sequence to convert image to ASCII art", SEQUENCECONVERTOR),
      Command("--nonlinear-table", Seq("[pathToFile]"), "Uses non-linear table defined in the given file to convert image to ASCII art", RANGECONVERTOR)
    ),
    FILTER -> Seq(
      Command("--rotate", Seq("[degrees]"), "Rotate image by given degrees", ROTATEFILTER),
      Command("--scale", Seq("[scaleValue]"), "Scales/shrinks image by given amount", SCALEFILTER),
      Command("--invert", Seq(), "Invert the colors of the image", INVERTFILTER),
      Command("--flip", Seq("[axis]"), "Flip image on x or y axis", FLIPFILTER),
      Command("--brightness", Seq("[brightnessValue]"), "Increases/decreases brightness of image by given value", BRIGHTNESSFILTER),
      Command("--fix-ratio", Seq(), "Fixes the fact that pixel should be 1:1, but a character is 1:2", FIXRATIO)
    )
  )

  /**
   * Prints all options to output
   */
  override def ShowHelp(): Unit = {
    output.PrintLn("Welcome to ASCII art convertor!")
    output.PrintLn("Commands for image conversion (order doesn't matter):")
    output.PrintLn("   Image source (exactly 1):")
    output.PrintLines(PrintCommands(CICommandsByType(LOADER)), "   ")
    output.PrintLn("   Image export (at least 1):")
    output.PrintLines(PrintCommands(CICommandsByType(EXPORTER)), "   ")
    output.PrintLn("   ASCII art shader (at most 1, if empty default):")
    output.PrintLines(PrintCommands(CICommandsByType(CONVERTOR)), "   ")
    output.PrintLn("   Image filters (unlimited, can be empty):")
    output.PrintLines(PrintCommands(CICommandsByType(FILTER)), "   ")
    output.PrintLn("Other commands:")
    output.PrintLines(PrintCommands(CommandsByType(HELP)), "   ")
    output.PrintLn("")
    output.PrintLn("If you upload ASCII art range shader from a file, each value needs to be on a separate line and values needs to be in the following format:")
    output.PrintLn("min..max->char")
    output.PrintLn("So for example:")
    output.PrintLn("0..100->X")
    output.PrintLn("100.256->Y")
  }

  /**
   * Prints all of the given commands
   */
  private def PrintCommands(commands: Seq[Command]): Seq[String] = {
    val bestPadding = commands.maxBy(x => x.SizeOfCommand + 4).SizeOfCommand
    commands.map(c => c.ToOneLine(bestPadding))
  }

  /**
   * Loads, converts, filters and exports to ASCII art an image.
   */
  override def ConvertImage(command: ConvertImageData): Unit = {
    val bitmapImage = command.imageLoader.Get()
    val image = command.ASCIIConvertor.GetASCIIArt(bitmapImage)
    val editedImage = command.imageFilter.EditImage(image)
    command.imageExporter.Export(editedImage)
  }
}
