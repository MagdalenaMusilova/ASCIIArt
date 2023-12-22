package ImageExporters

import Images.ASCIIArt

/**
 * Invalid exporter that doesn't export image to anywhere.
 */
object EmptyImageExporter extends ImageExporter {
  override def Export(image: ASCIIArt): Unit = {
    throw new Exception("No exporter chosen")
  }
}
