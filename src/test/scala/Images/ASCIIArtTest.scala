package Images

import org.scalatest.FunSuite

import scala.collection.immutable.Map

class ASCIIArtTest extends FunSuite{
  test("ASCIIArtTest") {
    val values : Seq[Seq[Int]] =
      (0 until 16)
        .map(y => (0 until 16)
          .map(x => y*16+x))
    val shader : Map[Range, Char] = Map(
      (150 until 151) -> '3',
      (10 until 100) -> '1',
      (0 until 10) -> '0',
      (100 until 150) -> '2',
      (151 until 256) -> '4'
    )
    val asciiArt = new ASCIIArt(values, shader)
    assert((0 until 10).forall(x => asciiArt.GetAt(x%16, x/16) == '0'))
    assert((10 until 100).forall(x => asciiArt.GetAt(x%16, x/16) == '1'))
    assert((100 until 150).forall(x => asciiArt.GetAt(x%16, x/16) == '2'))
    assert((150 until 151).forall(x => asciiArt.GetAt(x%16, x/16) == '3'))
    assert((151 until 256).forall(x => asciiArt.GetAt(x%16, x/16) == '4'))
  }

  test("ASCIIArtTestUnevenImage"){
    val values = Seq(
      Seq(0, 255, 200),
      Seq(0, 100)
    )
    val shader = Map((0 until 256) -> 'X')
    intercept[Exception] {
      val img = new ASCIIArt(values, shader)
    }
  }

  test("ASCIIArtTestOverlapping"){
    val values = Seq(
      Seq(0, 255, 200),
      Seq(0, 100, 100)
    )
    val shader = Map(
      (0 until 100) -> 'O',
      (100 until 201) -> 'I',
      (200 until 256) -> 'X'
    )
    intercept[Exception] {
      val img = new ASCIIArt(values, shader)
    }
  }

  test("ASCIIArtTestMissingValues") {
    val values = Seq(
      Seq(0, 255, 200),
      Seq(0, 100, 100)
    )
    val shader = Map(
      (0 until 100) -> 'O',
      (100 until 150) -> 'I',
      (200 until 256) -> 'X'
    )
    intercept[Exception] {
      val img = new ASCIIArt(values, shader)
    }
  }

  test("ASCIIArtTestNegativeShader"){
    val values = Seq(
      Seq(0, 255, 200),
      Seq(0, 100, 100)
    )
    val shaderNeg = Map(
      (-10 until 100) -> 'O',
      (100 until 200) -> 'I',
      (200 until 256) -> 'X'
    )
    intercept[Exception] {
      val img = new ASCIIArt(values, shaderNeg)
    }
  }

  test("ASCIIArtTestShaderTooBig") {
    val values = Seq(
      Seq(0, 255, 200),
      Seq(0, 100, 100)
    )
    val shaderTooBig = Map(
      (0 until 100) -> 'O',
      (100 until 200) -> 'I',
      (200 until 300) -> 'X'
    )
    intercept[Exception] {
      val img = new ASCIIArt(values, shaderTooBig)
    }
  }

  test("ASCIIArtTestShaderNegativeAndTooBig") {
    val values = Seq(
      Seq(0, 255, 200),
      Seq(0, 100, 100)
    )
    val shaderBoth = Map(
      (-1 until 100) -> 'O',
      (100 until 200) -> 'I',
      (200 until 300) -> 'X'
    )
    intercept[Exception] {
      val img = new ASCIIArt(values, shaderBoth)
    }
  }

  test("ASCIIArtTestShaderNegativePixel") {
    val values = Seq(
      Seq(0, 255, 200),
      Seq(0, 100, 100)
    )
    val shaderBoth = Map(
      (-1 until 100) -> 'O',
      (100 until 200) -> 'I',
      (200 until 300) -> 'X'
    )
    intercept[Exception] {
      val img = new ASCIIArt(values, shaderBoth)
    }
  }

  test("ASCIIArtTestNotRectangleImage") {
    val values = Seq(
      Seq(0, 255, 200),
      Seq(0, 100)
    )
    val shaderBoth = Map(
      (0 until 100) -> 'O',
      (100 until 200) -> 'I',
      (200 until 256) -> 'X'
    )
    intercept[Exception] {
      val img = new ASCIIArt(values, shaderBoth)
    }
  }

  test("ASCIIArtTestNotCoveringShader") {
    val values = Seq(
      Seq(0, 255, 200),
      Seq(0, 100, 100)
    )
    val shaderBoth = Map(
      (0 until 100) -> 'O',
      (100 until 150) -> 'I',
      (200 until 256) -> 'X'
    )
    intercept[Exception] {
      val img = new ASCIIArt(values, shaderBoth)
    }
  }

  test("ASCIIArtTestOverlappingShader") {
    val values = Seq(
      Seq(0, 255, 200),
      Seq(0, 100, 100)
    )
    val shaderBoth = Map(
      (0 until 100) -> 'O',
      (100 until 201) -> 'I',
      (200 until 256) -> 'X'
    )
    intercept[Exception] {
      val img = new ASCIIArt(values, shaderBoth)
    }
  }

  test("ASCIIArtTestOver256PixelValue") {
    val values = Seq(
      Seq(0, 255, 200),
      Seq(0, 100, 300)
    )
    val shaderBoth = Map(
      (0 until 100) -> 'O',
      (100 until 200) -> 'I',
      (200 until 256) -> 'X'
    )
    intercept[Exception] {
      val img = new ASCIIArt(values, shaderBoth)
    }
  }

  test("ASCIIArtTestNegativePixelValue") {
    val values = Seq(
      Seq(0, 255, 200),
      Seq(0, 100, -1)
    )
    val shaderBoth = Map(
      (0 until 100) -> 'O',
      (100 until 200) -> 'I',
      (200 until 256) -> 'X'
    )
    intercept[Exception] {
      val img = new ASCIIArt(values, shaderBoth)
    }
  }

  test("ASCIIArtTestInvalidIndex"){
    val values = Seq(
      Seq(0, 100, 200),
      Seq(123, 150, 250),
      Seq(255, 10, 20)
    )
    val shader = Map(
      (0 until 100) -> 'X',
      (100 until 200) -> 'I',
      (200 until 256) -> 'O'
    )
    val img = new ASCIIArt(values, shader)
    intercept[Exception]{
      img.GetAt(0,3)
    }
    intercept[Exception] {
      img.GetAt(3, 2)
    }
    intercept[Exception] {
      img.GetAt(-1, 2)
    }
    intercept[Exception] {
      img.GetAt(1, -2)
    }
    intercept[Exception] {
      img.GetAt(3, -2)
    }
    intercept[Exception] {
      img.GetAt(2, 4)
    }
    intercept[Exception] {
      img.GetAt(10, 2)
    }
    intercept[Exception] {
      img.GetAt(100, 20)
    }
  }
}
