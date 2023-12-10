package ImageExporters

import Images.ASCIIArt

trait ImageExporter {
  def Export(image : ASCIIArt) : Unit
}