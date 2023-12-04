package Images

import org.scalatest.FunSuite

class ASCIIArtTest extends FunSuite{
  test("CorrectShading") {
    val values : Vector[Vector[Int]] =
      (0 until 16)
        .map(y => (0 until 16)
          .map(x => y*16+x).toVector).toVector
    val shader : Map[Range, Char] = Map(
      (0 until 10) -> '0',
      (10 until 100) -> '1',
      (100 until 150) -> '2',
      (150 until 151) -> '3',
      (151 until 256) -> '4'
    )
    val asciiArt = new ASCIIArt(values, shader)
    assert((0 until 10).forall(x => asciiArt.getAt(x%16, x/16) == '0'))
    assert((10 until 100).forall(x => asciiArt.getAt(x%16, x/16) == '1'))
    assert((100 until 150).forall(x => asciiArt.getAt(x%16, x/16) == '2'))
    assert((150 until 151).forall(x => asciiArt.getAt(x%16, x/16) == '3'))
    assert((151 until 256).forall(x => asciiArt.getAt(x%16, x/16) == '4'))
  }
}
