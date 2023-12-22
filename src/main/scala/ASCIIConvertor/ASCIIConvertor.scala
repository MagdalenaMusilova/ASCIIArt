package ASCIIConvertor

import Images.{ASCIIArt, BitmapImage}

/**
 * Converts image to ASCII art.
 */
trait ASCIIConvertor {
  /**
   * Returns converted ASCII art
   * @param image RGB pixel image
   * @return Converted ascii art
   */
  def GetASCIIArt(image: BitmapImage): ASCIIArt
}
