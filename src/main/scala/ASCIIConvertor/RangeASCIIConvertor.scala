package ASCIIConvertor

import Images.{ASCIIArt, BitmapImage}

/**
 * Converts image to ASCII art with non-linear shader table.
 *
 * @param shader What ranges of pixel values should be translated to what character
 */
class RangeASCIIConvertor(shader: Map[Range, Char]) extends ASCIIConvertor {
  override def GetASCIIArt(image: BitmapImage): ASCIIArt = {
    val values =
      (0 until image.height).map(y =>
        (0 until image.width).map(x =>
          (image.GetAt(x, y).red * 0.3 + image.GetAt(x, y).green * 0.59 + image.GetAt(x, y).blue * 0.11).round.toInt
        )
      )
    new ASCIIArt(values, shader)
  }
}
