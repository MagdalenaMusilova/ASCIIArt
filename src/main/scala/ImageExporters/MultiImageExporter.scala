package ImageExporters

import Images.ASCIIArt

/**
 * Exports ASCII art to multiple outputs
 */
class MultiImageExporter(exporter1: ImageExporter, exporter2: ImageExporter) extends ImageExporter {
  override def Export(image: ASCIIArt): Unit = {
    exporter1.Export(image)
    exporter2.Export(image)
  }
}
