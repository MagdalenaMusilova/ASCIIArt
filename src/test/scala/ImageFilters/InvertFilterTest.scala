package ImageFilters

import Images.ASCIIArt
import org.scalatest.FunSuite

class InvertFilterTest extends FunSuite{
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
    assert((0 until 3).forall(y => expectedRes(y).equals(invertImage.GetLine(y))))
  }
}
