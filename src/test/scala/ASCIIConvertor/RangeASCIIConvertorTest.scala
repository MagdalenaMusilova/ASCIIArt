package ASCIIConvertor

import Images.{ASCIIArt, BitmapImage, RGBColor}
import org.scalatest.{FunSuite, ScalaTestVersion}

class RangeASCIIConvertorTest extends FunSuite{
  test("RangeASCIIConvertorTest") {
    val white : RGBColor = new RGBColor(255,255,255)
    val grey : RGBColor = new RGBColor(128,128,128)
    val black : RGBColor = new RGBColor(0,0,0)
    val values = Seq(
      Seq(black,grey,black,grey,black),
      Seq(white,grey,black,grey,white),
      Seq(white,grey,white,grey,white),
      Seq(black,white,grey,white,black),
      Seq(black,grey,white,white,black)
    )
    val bitmap = new BitmapImage(values)
    val shader: Map[Range, Char] = Map(
      (0 until 2) -> 'X',
      (2 until 255) -> 'I',
      (255 until 256) -> 'O',
    )
    val convertor : RangeASCIIConvertor = new RangeASCIIConvertor(shader)
    val res = convertor.GetASCIIArt(bitmap)

    val expectedRes = Seq(
      Seq('X','I','X','I','X'),
      Seq('O','I','X','I','O'),
      Seq('O','I','O','I','O'),
      Seq('X','O','I','O','X'),
      Seq('X','I','O','O','X')
    )
    assert(
      (0 until 5).forall(y =>
        (0 until 5). forall(x =>
          res.GetAt(x,y) == expectedRes(y)(x)))
    )
  }

  test("RangeASCIIConvertorTestNotCoveringShader"){
    val image = new BitmapImage(Seq(Seq(RGBColor(0, 0, 0))))
    val notCoveringShader = Map(
      (0 until 100) -> 'O',
      (100 until 230) -> 'X'
    )
    intercept[Exception] {
      new RangeASCIIConvertor(notCoveringShader).GetASCIIArt(image)
    }
  }

  test("RangeASCIIConvertorTestOverlappingShader"){
    val image = new BitmapImage(Seq(Seq(RGBColor(0, 0, 0))))
    val overlappingShader = Map(
      (0 to 120) -> 'O',
      (120 until 256) -> 'X'
    )
    intercept[Exception] {
      new RangeASCIIConvertor(overlappingShader).GetASCIIArt(image)
    }
  }

  test("RangeASCIIConvertorTestNegativeValueShader"){
    val image = new BitmapImage(Seq(Seq(RGBColor(0, 0, 0))))
    val negativeValuesShader = Map(
      (-10 until 256) -> 'X'
    )
    intercept[Exception] {
      new RangeASCIIConvertor(negativeValuesShader).GetASCIIArt(image)
    }
  }

  test("RangeASCIIConvertorTestTooBigValueShader"){
    val image = new BitmapImage(Seq(Seq(RGBColor(0, 0, 0))))
    val tooBigValuesShader = Map(
      (0 until 257) -> 'X'
    )
    intercept[Exception] {
      new RangeASCIIConvertor(tooBigValuesShader).GetASCIIArt(image)
    }
  }

  test("RangeASCIIConvertorTestEmptyImage") {
    val image = new BitmapImage(Seq())
    val shader = Map(
      (0 until 256) -> 'X'
    )
    val convertor = new RangeASCIIConvertor(shader)
    val res = convertor.GetASCIIArt(image)
    assert(res.height == 0 && res.width == 0)
  }
}
