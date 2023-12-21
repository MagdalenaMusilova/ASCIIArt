package ImageExporters

import Images.ASCIIArt

/**
 * Exports ASCII art
 */
trait ImageExporter {
  /**
   * Exports ASCII art
   * @param image ASCII art to export
   */
  def Export(image : ASCIIArt) : Unit
}