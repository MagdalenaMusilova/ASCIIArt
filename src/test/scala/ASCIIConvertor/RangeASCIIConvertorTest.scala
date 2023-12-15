package ASCIIConvertor

import Images.{ASCIIArt, BitmapImage, RGBColor}
import org.scalatest.FunSuite

class RangeASCIIConvertorTest extends FunSuite{
  test("RangeASCIIConvertorTest") {
    val white : RGBColor = new RGBColor(255,255,255)
    val grey : RGBColor = new RGBColor(128,128,128)
    val black : RGBColor = new RGBColor(0,0,0)
    val values: Vector[Vector[RGBColor]] = Vector(
      Vector(black,grey,black,grey,black),
      Vector(white,grey,black,grey,white),
      Vector(white,grey,white,grey,white),
      Vector(black,white,grey,white,black),
      Vector(black,grey,white,white,black)
    )
    val bitmap = new BitmapImage(values)
    val shader: Map[Range, Char] = Map(
      (0 until 2) -> 'X',
      (2 until 255) -> 'I',
      (255 until 256) -> 'O',
    )
    val convertor : RangeASCIIConvertor = new RangeASCIIConvertor(shader)
    val res = convertor.GetASCIIArt(bitmap)

    val expectedRes : Vector[Vector[Char]] = Vector(
      Vector('X','I','X','I','X'),
      Vector('O','I','X','I','O'),
      Vector('O','I','O','I','O'),
      Vector('X','O','I','O','X'),
      Vector('X','I','O','O','X')
    )
    assert(
      (0 until 5).forall(y =>
        (0 until 5). forall(x =>
          res.GetAt(x,y) == expectedRes(y)(x)))
    )
  }

  test("RangeASCIIConvertorTestInvalidShader") {
    val image = new BitmapImage(Vector(Vector(RGBColor(0, 0, 0))))
    val notCoveringShader = Map(
      (0 until 100) -> 'O',
      (100 until 230) -> 'X'
    )
    val overlappingShader = Map(
      (0 to 120) -> 'O',
      (120 until 256) -> 'X'
    )
    val negativeValuesShader = Map(
      (-10 until 256) -> 'X'
    )
    val tooBigValuesShader = Map(
      (0 until 257) -> 'X'
    )
    intercept[Exception] {
      new RangeASCIIConvertor(notCoveringShader).GetASCIIArt(image)
    }
    intercept[Exception] {
      new RangeASCIIConvertor(overlappingShader).GetASCIIArt(image)
    }
    intercept[Exception] {
      new RangeASCIIConvertor(negativeValuesShader).GetASCIIArt(image)
    }
    intercept[Exception] {
      new RangeASCIIConvertor(tooBigValuesShader).GetASCIIArt(image)
    }
  }

  test("RangeASCIIConvertorTestEmptyImage") {
    val image = new BitmapImage(Vector())
    val shader = Map(
      (0 until 256) -> 'X'
    )
    val convertor = new RangeASCIIConvertor(shader)
    val res = convertor.GetASCIIArt(image)
    assert(res.height == 0 && res.width == 0)
  }
}
