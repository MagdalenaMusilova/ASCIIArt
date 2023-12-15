package ImageFilters

import Images.ASCIIArt
import org.scalatest.FunSuite

class EmptyFilterTest extends FunSuite {
  test("EmptyFilterTest") {
    val values: Seq[Seq[Int]] = Seq(
      Seq(255, 255, 255),
      Seq(255, 128, 0),
      Seq(0, 0, 0)
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
