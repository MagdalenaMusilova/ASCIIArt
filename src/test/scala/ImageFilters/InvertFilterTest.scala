package ImageFilters

import Images.ASCIIArt
import org.scalatest.FunSuite

class InvertFilterTest extends ImageFilterTest(new InvertFilter) {
  test("InvertFilterTest"){
    val values : Vector[Vector[Int]] = Vector(
      Vector(255,255,255),
      Vector(255,128,0),
      Vector(0,0,0)
    )
    val shader: Map[Range, Char] = Map(
      (0 until 50) -> 'X',
      (50 until 150) -> 'I',
      (150 until 256) -> 'O'
    )
    val orgImage = new ASCIIArt(values,shader)
    val filter = new InvertFilter()
    val invertImage = filter.EditImage(orgImage)

    val expectedRes = Vector(
      Vector('X','X','X'),
      Vector('X','I','O'),
      Vector('O','O','O')
    )
    assert((0 until 3).forall(y => expectedRes(y).equals(invertImage.GetRow(y))))
  }

  test("InvertFilterTestDoubleInvert") {
    val values: Vector[Vector[Int]] = Vector(
      Vector(255, 255, 255),
      Vector(255, 128, 0),
      Vector(0, 0, 0)
    )
    val shader: Map[Range, Char] = Map(
      (0 until 50) -> 'X',
      (50 until 150) -> 'I',
      (150 until 256) -> 'O'
    )
    val orgImage = new ASCIIArt(values, shader)
    val filter = new InvertFilter()
    val res1 = filter.EditImage(orgImage)
    val res2 = filter.EditImage(res1)

    val expectedRes = Vector(
      Vector('O', 'O', 'O'),
      Vector('O', 'I', 'X'),
      Vector('X', 'X', 'X')
    )
    assert((0 until 3).forall(y => expectedRes(y).equals(res2.GetRow(y))))
  }
}
