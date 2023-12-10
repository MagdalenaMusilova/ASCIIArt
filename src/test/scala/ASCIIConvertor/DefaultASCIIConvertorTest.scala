package ASCIIConvertor

import Images.{BitmapImage, RGBColor}
import org.scalatest.FunSuite

class DefaultASCIIConvertorTest extends FunSuite{
  test("DefaultASCIIConvertorTest") {
    val values : Vector[RGBColor] = (0 until 71)
      .map(i => 255.min((i*(256d/70)).toInt))
      .map(j => RGBColor(j, j, j)).toVector
    val image = new BitmapImage(Vector(values))
    val convertor = new DefaultASCIIConvertor()
    val res = convertor.GetASCIIArt(image)
    assert((0 until res.width).forall(x =>
      res.GetAt(x,0) == "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'.  ".charAt(x)))
  }
}
