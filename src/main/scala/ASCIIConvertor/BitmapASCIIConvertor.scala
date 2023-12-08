package ASCIIConvertor

import Images.{ASCIIArt, BitmapImage}

class BitmapASCIIConvertor(image : BitmapImage) extends ASCIIConvertor {
  override def GetASCIIArt(shader : Map[Range, Char]): ASCIIArt = {
    val values : Vector[Vector[Int]] =
      (0 until image.height)
        .map(y => (0 until image.width)
          .map(x =>
            (image.getAt(x,y).red * 0.3 + image.getAt(x,y).green * 0.59 + image.getAt(x,y).blue * 0.11).toInt
          ).toVector).toVector
    new ASCIIArt(values, shader)
  }
}
