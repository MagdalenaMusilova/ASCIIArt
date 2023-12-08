package ImageSavers

import Images.ASCIIArt

import java.io.{File, PrintWriter}


class ASCIIArtSaverToFile(image: ASCIIArt, fileName : String) extends ImageSaver {
  override def SaveImage(): Boolean = {
    val file = new File(fileName + ".txt")
    val pw = new PrintWriter(file)

    (0 until image.height).foreach(y =>
      pw.write(image.GetLine(y).mkString("") + "\n")
    )
    pw.close()
    true
  }
}
