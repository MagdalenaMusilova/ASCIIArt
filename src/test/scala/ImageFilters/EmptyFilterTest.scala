package ImageFilters

import Images.ASCIIArt
import org.scalatest.FunSuite

class EmptyFilterTest extends FunSuite{
  test("EmptyFilterTest") {
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
    val filter = EmptyFilter
    val res = filter.EditImage(orgImage)
    assert(res == orgImage)
  }
}
