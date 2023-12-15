package ImageExporters

import Images.ASCIIArt

import java.io.{File, PrintWriter}


class FileImageExporter(fileName : String) extends ImageExporter {
  override def Export(image: ASCIIArt) : Unit = {
      val file = new File(fileName)
      val pw = new PrintWriter(file)

      (0 until image.height).foreach(y =>
        pw.write(image.GetRow(y).mkString("") + "\n")
      )
      pw.close()
  }
}
