package ASCIIConvertor

import Images.{ASCIIArt, BitmapImage}

class RangeASCIIConvertor(shader : Map[Range, Char]) extends ASCIIConvertor {
  override def GetASCIIArt(image : BitmapImage): ASCIIArt = {
    val values : Vector[Vector[Int]] =
      (0 until image.height).map(y =>
        (0 until image.width).map(x =>
            (image.GetAt(x,y).red * 0.3 + image.GetAt(x,y).green * 0.59 + image.GetAt(x,y).blue * 0.11).round.toInt
          ).toVector
      ).toVector
    new ASCIIArt(values, shader)
  }
}
