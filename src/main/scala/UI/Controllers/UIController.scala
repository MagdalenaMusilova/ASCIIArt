package UI.Controllers

import UI.Commands.ConvertImageData

/**
 * Contains all of the information for an UI.
 */
trait UIController {
  def ShowHelp(): Unit

  def ConvertImage(command: ConvertImageData): Unit
}
