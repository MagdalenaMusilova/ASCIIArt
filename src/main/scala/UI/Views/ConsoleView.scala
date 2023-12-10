package UI.Views

import ASCIIConvertor.{ASCIIConvertor, DefaultASCIIConvertor, EmptyASCIIConvertor, RangeASCIIConvertor, SequenceASCIIConvertor}
import IO.{Input, Output}
import ImageExporters.{EmptyImageExporter, FileImageExporter, ImageExporter, MultiImageExporter, OutputImageExporter}
import ImageFilters.{FlipFilter, ImageFilter, InvertFilter, MultiFilter}
import ImageLoaders.{EmptyImageLoader, FileImageLoader, ImageLoader}
import ShaderLoaders.FileShaderLoader
import UI.Commands.CommandTypes.{CONVERTIMAGE, CommandType, EXIT, HELP, OTHER}
import UI.Commands.ConvertImageCommandTypes.{CONVERTOR, ConvertImageCommandType, EXPORTER, FILTER, LOADER}
import UI.Commands.{CICommand, ConvertImageData}
import UI.Controllers.ConsoleController

import scala.collection.mutable.ArrayBuffer

class ConsoleView(input : Input, output : Output) {
  private var running = true
  private val controller = new ConsoleController(output)

  def Start(): Unit = {
    while (running) {
      val textCommands = GroupTextInput(input.ReadLine())
      val commandType = GetInputType(textCommands)
      commandType match {
        case HELP => ShowHelp()
        case EXIT => Stop()
        case CONVERTIMAGE => ConvertImage(textCommands)
      }
    }
    controller.Stop()
  }

  private def GroupTextInput(text: String): Vector[Vector[String]] = {
    val splitText = text.trim.split(" ")
    val res = new ArrayBuffer[ArrayBuffer[String]]()
    splitText.foreach(word =>
      if (word.startsWith("--")) {
        res += ArrayBuffer(word)
      } else if (res.nonEmpty) {
        res(res.size - 1) += word
      } else {
        throw new Exception("Unknown argument")
      }
    )
    res.map(x => x.toVector).toVector
  }

  private def GetInputType(commands: Vector[Vector[String]]): CommandType = {
    if (commands.isEmpty) {
      throw new Exception("No input")
    }
    val userInput = commands.head.head
    val res = controller.CommandsByType.find(x => x._2.exists(c => c.command == userInput))

    if (res.isDefined) {
      res.get._1
    } else {
      OTHER
    }
  }

  private def Stop(): Unit = {
    running = false
    controller.Stop()
  }

  private def ShowHelp(): Unit = {
    controller.ShowHelp()
  }

  private def ConvertImage(textInput: Vector[Vector[String]]): Unit = {
    val command = LoadConvertImageInput(textInput)
    controller.ConvertImage(command)
  }

  private def SortConvertImageInput(textCommands: Vector[Vector[String]])
  : Map[ConvertImageCommandType, Vector[Vector[String]]] = {
    val res: Map[ConvertImageCommandType, ArrayBuffer[Vector[String]]] = Map(
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
    res.map(a => a._1 -> a._2.map(b => b.toVector).toVector)
  }

  private def LoadConvertImageInput(textCommands: Vector[Vector[String]]): ConvertImageData = {
    val sortedCommands = SortConvertImageInput(textCommands)
    val convertImageData = new ConvertImageData
    LoadLoader(sortedCommands(LOADER), convertImageData)
    LoadExporters(sortedCommands(EXPORTER), convertImageData)
    LoadConverter(sortedCommands(CONVERTOR), convertImageData)
    LoadFilter(sortedCommands(FILTER), convertImageData)
    convertImageData
  }

  private def LoadLoader(textCommands: Vector[Vector[String]], commandData: ConvertImageData): Unit = {
    if (textCommands.isEmpty) {
      throw new Exception("No image loaders")
    }
    if (textCommands.size > 1) {
      throw new Exception("Too many image loaders")
    }
    val textCom = textCommands.head
    val command = controller.CICommandsByType(LOADER).find(x => x.command == textCom.head).get.asInstanceOf[CICommand[ImageLoader]]

    command.linkedClass match {
      case _: FileImageLoader =>
        if (textCom.size != 2) throw new Exception("Too many arguments for image loading")
        commandData.imageLoader = new FileImageLoader(textCom(1))
      case _ => throw new Exception("Chosen image loader not implemented")
    }
  }

  private def LoadExporters(textCommands: Vector[Vector[String]], commandData: ConvertImageData): Unit = {
    if (textCommands.isEmpty) {
      throw new Exception("No image exporter")
    }
    textCommands.foreach(textCom => {
      val command = controller.CICommandsByType(EXPORTER).find(x => x.command == textCom.head).get.asInstanceOf[CICommand[ImageExporter]]
      command.linkedClass match {
        case _: FileImageExporter =>
          if (textCom.size != 2) throw new Exception("Too many arguments for image exporting")
          commandData.imageExporter = new MultiImageExporter(new FileImageExporter(textCom(1)), commandData.imageExporter)
        case _: OutputImageExporter =>
          if (textCom.size != 1) throw new Exception("Too many arguments for image exporting")
          commandData.imageExporter = new MultiImageExporter(new OutputImageExporter(output), commandData.imageExporter)
        case _ => throw new Exception("Chosen image exporter not implemented")
      }
    })
  }

  private def LoadConverter(textCommands: Vector[Vector[String]], commandData: ConvertImageData): Unit = {
    if (textCommands.size > 1) {
      throw new Exception("Too many ASCII convertors")
    }
    if (textCommands.isEmpty){
      commandData.ASCIIConvertor = new DefaultASCIIConvertor
      return
    }
    textCommands.foreach(textCom => {
      val command = controller.CICommandsByType(CONVERTOR).find(x => x.command == textCom.head).get.asInstanceOf[CICommand[ASCIIConvertor]]
      command.linkedClass match {
        case _: DefaultASCIIConvertor =>
          if (textCom.size != 1) throw new Exception("Too many arguments for ASCII convertor")
          commandData.ASCIIConvertor = new DefaultASCIIConvertor()
        case _: SequenceASCIIConvertor =>
          if (textCom.size != 2) throw new Exception("Too many arguments for ASCII convertor")
          commandData.ASCIIConvertor = new SequenceASCIIConvertor(textCom(1))
        case _: RangeASCIIConvertor =>
          if (textCom.size != 2) throw new Exception("Too many arguments for ASCII convertor")
          val shader = new FileShaderLoader(textCom(1)).Get()
          commandData.ASCIIConvertor = new RangeASCIIConvertor(shader)
        case _ => throw new Exception("Chosen ASCII convertor not implemented")
      }
    })
  }

  private def LoadFilter(textCommands: Vector[Vector[String]], commandData: ConvertImageData): Unit = {
    textCommands.foreach(textCom => {
      val command = controller.CICommandsByType(FILTER).find(x => x.command == textCom.head).get.asInstanceOf[CICommand[ImageFilter]]
      command.linkedClass match {
        case _: InvertFilter =>
          if (textCom.size != 1) throw new Exception("Too many arguments for invert filter")
          commandData.imageFilter = new MultiFilter(new InvertFilter, commandData.imageFilter)
        case _: FlipFilter =>
          if (textCom.size != 2) throw new Exception("Too many arguments for flip filter")
          val direction = textCom(1).toLowerCase()
          if (direction != "x" && direction != "y") throw new Exception("Unknown flip direction")
          val flipX = direction == "x"
          commandData.imageFilter = new MultiFilter(new FlipFilter(flipX), commandData.imageFilter)
        case _ => throw new Exception("Chosen filter not implemented")
      }
    })
  }
}
