package ImageExporters

import Images.ASCIIArt

object EmptyImageExporter extends ImageExporter {
  override def Export(image: ASCIIArt) : Unit = {
    throw new Exception("No exporter chosen")
  }
}
