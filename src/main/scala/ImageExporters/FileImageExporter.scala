package ImageExporters

import Images.ASCIIArt

import java.io.{File, PrintWriter}

/**
 * Exports image to a file
 *
 * @param fileName Name of the file in which ASCII art will be stored.
 */
class FileImageExporter(fileName: String) extends ImageExporter {
  override def Export(image: ASCIIArt): Unit = {
    val file = new File(fileName)
    val pw = new PrintWriter(file)

    if (image.height > 0) {
      (0 until image.height).foreach(y => {
        val line = (0 until image.width).map(x => image.GetAt(x, y)).mkString("")
        pw.write(line + "\n")
      })
    } else {
      pw.write("")
    }
    pw.close()
  }
}
