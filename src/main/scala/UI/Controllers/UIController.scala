package UI.Controllers

import UI.Commands.ConvertImageData
trait UIController {
  def ShowHelp() : Unit
  def Stop() : Unit
  def ConvertImage(command : ConvertImageData) : Unit
}
