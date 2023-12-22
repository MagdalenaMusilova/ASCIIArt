package Images

/**
 * Image made of RGB color pixels
 *
 * @param value The image represented as 2D array
 */
class BitmapImage(value: Seq[Seq[RGBColor]]) {
  def GetAt(x: Int, y: Int): RGBColor = {
    if (!(0 until width).contains(x) ||
      !(0 until height).contains(y)) {
      throw new ArrayIndexOutOfBoundsException()
    }
    value(y)(x)
  }

  def height: Int = value.length

  def width: Int = {
    if (value.isEmpty) {
      0
    } else {
      value.head.length
    }
  }

  if (!value.forall(_.length == value.head.length)) {
    throw new Exception("Image has varying widths")
  }
}
