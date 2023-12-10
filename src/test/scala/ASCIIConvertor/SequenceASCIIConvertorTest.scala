package ASCIIConvertor

import Images.{BitmapImage, RGBColor}
import org.scalatest.FunSuite

class SequenceASCIIConvertorTest extends FunSuite{
  test("SequenceASCIIConvertorTest") {
    val values: Vector[RGBColor] = (0 until 11)
      .map(i => 255.min((i * (256d / 10)).toInt))
      .map(j => RGBColor(j, j, j)).toVector
    val image = new BitmapImage(Vector(values))
    val convertor = new SequenceASCIIConvertor("@%#*+=-:. ")
    val res = convertor.GetASCIIArt(image)
    assert((0 until res.width).forall(x => res.GetAt(x, 0) == "@%#*+=-:.  ".charAt(x)))
  }
}
