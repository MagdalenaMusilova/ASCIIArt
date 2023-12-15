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

  test("SequenceASCIIConvertorTestOneCharShader") {
    val values = (0 until 256).map(x =>
      new RGBColor(x,x,x)).toVector
    val image = new BitmapImage(Vector(values))
    val convertor = new SequenceASCIIConvertor("X")
    val res = convertor.GetASCIIArt(image)
    assert((0 until res.width).forall(x => res.GetAt(x,0) == 'X'))
  }

  test("SequenceASCIIConvertorTestEmptyImage") {
    val image = new BitmapImage(Vector())
    val convertor = new SequenceASCIIConvertor("abcd")
    val res = convertor.GetASCIIArt(image)
    assert(res.width == 0 && res.height == 0)
  }

  test("SequenceASCIIConvertorTestInvalidShader") {
    val values: Vector[Vector[RGBColor]] = Vector(Vector(RGBColor(0,10,100)))
    val image = new BitmapImage(values)
    intercept[Exception] {
      val emptyConvertor = new SequenceASCIIConvertor("")
      emptyConvertor.GetASCIIArt(image)
    }
    intercept[Exception] {
      val longShader = (0 to 256).map(_ => 'a').mkString("")
      val emptyConvertor = new SequenceASCIIConvertor(longShader)
      emptyConvertor.GetASCIIArt(image)
    }
  }
}
