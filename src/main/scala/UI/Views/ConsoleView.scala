package UI.Views

import ASCIIConvertor._
import IO.{Input, Output}
import ImageExporters._
import ImageFilters._
import ImageLoaders.{FileImageLoader, RandomImageLoader}
import ShaderLoaders.FileShaderLoader
import UI.Commands.CommandTypes.CommandType
import UI.Commands.ConvertImageCommandTypes.{CONVERTOR, ConvertImageCommandType, EXPORTER, FILTER, LOADER}
import UI.Commands.ExactCommandType.{BRIGHTNESSFILTER, DEFAULTCONVERTOR, FILEEXPORTER, FILELOADER, FIXRATIO, FLIPFILTER, INVERTFILTER, OUTPUTEXPORTER, RANDOMLOADER, RANGECONVERTOR, ROTATEFILTER, SCALEFILTER, SEQUENCECONVERTOR}
import UI.Commands.{CommandTypes, ConvertImageData}
import UI.Controllers.ConsoleController

import scala.collection.mutable.ArrayBuffer

/**
 * Runs the whole program
 * @param input Where to get input from
 * @param output Where should output be printed to
 */
class ConsoleView(input: Input, output: Output) {
  private val controller = new ConsoleController(output)

  def Start(): Unit = {
    val textInput = input.ReadInputArgs()
    val textCommands = GroupTextInput(textInput)
    val commandType = GetInputType(textCommands)
    commandType match {
      case CommandTypes.HELP => ShowHelp()
      case CommandTypes.CONVERTIMAGE => ConvertImage(textCommands)
    }
  }

  /**
   * Groups raw text input so that a command and its arguments are together
   * @param textInput Individual words from input
   * @return Sequence of commands with their arguments
   */
  private def GroupTextInput(textInput: Seq[String]): Seq[Seq[String]] = {
    val res = new ArrayBuffer[ArrayBuffer[String]]()
    textInput.foreach(word =>
      if (word.startsWith("--")) {
        res += ArrayBuffer(word)
      } else if (res.nonEmpty) {
        res(res.size - 1) += word
      } else {
        throw new Exception("Unknown argument")
      }
    )
    res.map(i => i.toSeq).toSeq
  }

  /**
   * Get what kind of command is on input
   * @param commands Grouped commands from input
   */
  private def GetInputType(commands: Seq[Seq[String]]): CommandType = {
    if (commands.isEmpty) {
      throw new Exception("No input")
    }
    val userInput = commands.head.head
    val res = controller.CommandsByType.find(x => x._2.exists(c => c.command == userInput))

    if (res.isDefined) {
      res.get._1
    } else {
      CommandTypes.OTHER
    }
  }

  private def ShowHelp(): Unit = {
    controller.ShowHelp()
  }

  /**
   * Converts image according to given commands
   * @param textInput Grouped commands
   */
  private def ConvertImage(textInput: Seq[Seq[String]]): Unit = {
    val command = LoadConvertImageInput(textInput)
    controller.ConvertImage(command)
  }

  /**
   * Loads ConvertImageData from given commands
   * @param textCommands Grouped commands
   */
  private def LoadConvertImageInput(textCommands: Seq[Seq[String]]): ConvertImageData = {
    val sortedCommands = SortConvertImageInput(textCommands)
    val convertImageData = new ConvertImageData
    LoadLoader(sortedCommands(LOADER), convertImageData)
    LoadExporters(sortedCommands(EXPORTER), convertImageData)
    LoadConverter(sortedCommands(CONVERTOR), convertImageData)
    LoadFilter(sortedCommands(FILTER), convertImageData)
    convertImageData
  }

  /**
   * Groups commands to loaders, exporters, convertors and filters
   */
  private def SortConvertImageInput(textCommands: Seq[Seq[String]])
  : Map[ConvertImageCommandType, Seq[Seq[String]]] = {
    val res: Map[ConvertImageCommandType, ArrayBuffer[Seq[String]]] = Map(
      LOADER -> ArrayBuffer(),
      EXPORTER -> ArrayBuffer(),
      CONVERTOR -> ArrayBuffer(),
      FILTER -> ArrayBuffer()
    )
    textCommands.foreach(text => {
      val tmp = controller.CICommandsByType.find(com => com._2.exists(c => c.command == text.head))
      if (res.isEmpty) {
        throw new Exception("Unknown command: " + text.head)
      }
      res(tmp.get._1) += text
    })
    res.map(a => a._1 -> a._2.map(b => b.toSeq).toSeq)
  }

  /**
   * Updates loaders in ConvertImageData by given commands
   */
  private def LoadLoader(textCommands: Seq[Seq[String]], commandData: ConvertImageData): Unit = {
    if (textCommands.isEmpty) {
      throw new Exception("No image loaders")
    }
    if (textCommands.size > 1) {
      throw new Exception("Too many image loaders")
    }
    val textCom = textCommands.head
    val command = controller.CICommandsByType(LOADER).find(x => x.command == textCom.head).get

    if (textCom.size - 1 != command.arguments.size) {
      throw new Exception("Too many arguments for image loading")
    }
    command.exactType match {
      case FILELOADER =>
        commandData.imageLoader = new FileImageLoader(textCom(1))
      case RANDOMLOADER =>
        commandData.imageLoader = new RandomImageLoader()
      case _ =>
        throw new Exception("Chosen image loader not implemented")
    }
  }

  /**
   * Updates exporters in ConvertImageData by given commands
   */
  private def LoadExporters(textCommands: Seq[Seq[String]], commandData: ConvertImageData): Unit = {
    if (textCommands.isEmpty) {
      throw new Exception("No image exporter")
    }
    textCommands.foreach(textCom => {
      val command = controller.CICommandsByType(EXPORTER).find(x => x.command == textCom.head).get
      if (textCom.size - 1 != command.arguments.size) {
        throw new Exception("Too many arguments for image exporting")
      }
      command.exactType match {
        case FILEEXPORTER =>
          AddExporter(commandData, new FileImageExporter(textCom(1)))
        case OUTPUTEXPORTER =>
          AddExporter(commandData, new OutputImageExporter(output))
        case _ =>
          throw new Exception("Chosen image exporter not implemented")
      }
    })
  }

  /**
   * Add a new exporter to ConvertImageData
   */
  private def AddExporter(commandData: ConvertImageData, exporter: ImageExporter): Unit = {
    if (commandData.imageExporter == EmptyImageExporter) {
      commandData.imageExporter = exporter
    } else {
      commandData.imageExporter = new MultiImageExporter(exporter, commandData.imageExporter)
    }
  }

  /**
   * Updates convertors in ConvertImageData by given commands
   */
  private def LoadConverter(textCommands: Seq[Seq[String]], commandData: ConvertImageData): Unit = {
    if (textCommands.size > 1) {
      throw new Exception("Too many ASCII convertors")
    }
    if (textCommands.isEmpty) {
      commandData.ASCIIConvertor = DefaultASCIIConvertor
      return
    }
    textCommands.foreach(textCom => {
      val command = controller.CICommandsByType(CONVERTOR).find(x => x.command == textCom.head).get
      if (textCom.size - 1 != command.arguments.size) {
        throw new Exception("Too many arguments for ASCII convertor")
      }
      command.exactType match {
        case DEFAULTCONVERTOR =>
          if (textCom(1) == GetShaderTableName(new PaulBourkeASCIIConvertor())) {
            commandData.ASCIIConvertor = new PaulBourkeASCIIConvertor
          } else if (textCom(1) == GetShaderTableName(new ShortPaulBourkeASCIIConvertor())) {
            commandData.ASCIIConvertor = new ShortPaulBourkeASCIIConvertor
          } else if (textCom(1) == GetShaderTableName(new BlockASCIIConvertor())) {
            commandData.ASCIIConvertor = new BlockASCIIConvertor
          } else {
            throw new Exception("Unknown table name")
          }
        case SEQUENCECONVERTOR =>
          commandData.ASCIIConvertor = new SequenceASCIIConvertor(textCom(1))
        case RANGECONVERTOR =>
          val shader = new FileShaderLoader(textCom(1)).Get()
          commandData.ASCIIConvertor = new RangeASCIIConvertor(shader)
        case _ => throw new Exception("Chosen ASCII convertor not implemented")
      }
    })
  }

  /**
   * Gets shader table from instance of ASCII Convertor class
   */
  private def GetShaderTableName(instance: Any): String = {
    instance.toString.replace("asciiconvertor", "")
  }

  /**
   * Updates filters in ConvertImageData by given commands
   */
  private def LoadFilter(textCommands: Seq[Seq[String]], commandData: ConvertImageData): Unit = {
    textCommands.foreach(textCom => {
      val command = controller.CICommandsByType(FILTER).find(x => x.command == textCom.head).get
      if (textCom.size - 1 != command.arguments.size) {
        throw new Exception("Too many arguments for ASCII convertor")
      }
      command.exactType match {
        case ROTATEFILTER =>
          if (!IsInt(textCom(1))) {
            throw new Exception("Input for rotate filter isn't a number")
          }
          commandData.imageFilter = new MultiFilter(new RotateFilter(textCom(1).toInt), commandData.imageFilter)
        case SCALEFILTER =>
          if (!IsDouble(textCom(1))) {
            throw new Exception("Input for scale filter isn't a number")
          }
          commandData.imageFilter = new MultiFilter(new ScaleFilter(textCom(1).toDouble), commandData.imageFilter)
        case INVERTFILTER =>
          commandData.imageFilter = new MultiFilter(new InvertFilter, commandData.imageFilter)
        case FLIPFILTER =>
          val direction = textCom(1).toLowerCase()
          if (direction != "x" && direction != "y") throw new Exception("Unknown flip direction")
          val flipX = (direction == "x")
          commandData.imageFilter = new MultiFilter(new FlipFilter(flipX), commandData.imageFilter)
        case BRIGHTNESSFILTER =>
          if (!IsInt(textCom(1))) {
            throw new Exception("Input for brightness filter isn't a number")
          }
          commandData.imageFilter = new MultiFilter(new BrightnessFilter(textCom(1).toInt), commandData.imageFilter)
        case FIXRATIO =>
          commandData.imageFilter = new MultiFilter(new FixRatioFilter, commandData.imageFilter)
        case _ => throw new Exception("Chosen filter not implemented")
      }
    })
  }

  /**
   * Check if given text is an int value
   */
  private def IsInt(text: String): Boolean = {
    try {
      text.toInt
    } catch {
      case _: Throwable => return false
    }
    true
  }

  /**
   * Check if given text is a double value
   */
  private def IsDouble(text: String): Boolean = {
    try {
      text.toDouble
    } catch {
      case _: Throwable => return false
    }
    true
  }
}
