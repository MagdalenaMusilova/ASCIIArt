package ImageExporters
import IO.Output
import Images.ASCIIArt

/**
 * Exports ASCII art to given output (stream)
 * @param out Output stream
 */
class OutputImageExporter(out : Output) extends ImageExporter {
  override def Export(image: ASCIIArt): Unit = {
    val lines =
      (0 until image.height).map(y =>
        (0 until image.width).map(x =>
          image.GetAt(x, y)
        ).mkString("")
      )
    out.PrintLines(lines)
  }
}
