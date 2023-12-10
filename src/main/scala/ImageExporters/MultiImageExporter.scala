package ImageExporters
import Images.ASCIIArt

class MultiImageExporter(exporter1 : ImageExporter, exporter2 : ImageExporter) extends ImageExporter {
  override def Export(image: ASCIIArt) : Unit = {
    exporter1.Export(image)
    exporter2.Export(image)
  }
}
