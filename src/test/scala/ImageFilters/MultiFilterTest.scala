package ImageFilters

import Images.ASCIIArt
import org.scalatest.FunSuite

class MultiFilterTest extends FunSuite{
  test("InvertFilterTest") {
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
    val filter0 = EmptyFilter
    val filter1 = new InvertFilter()
    val filter2 = new FlipFilter(true)
    val filter3 = new FlipFilter(false)
    val filter =
      new MultiFilter(filter3,
        new MultiFilter(filter2,
          new MultiFilter(filter1, filter0)))
    val invertImage = filter.EditImage(orgImage)

    val expectedRes = Vector(
      Vector('O', 'O', 'O'),
      Vector('O', 'I', 'X'),
      Vector('X', 'X', 'X')
    )
    assert((0 until 3).forall(y => expectedRes(y).equals(invertImage.GetRow(y))))
  }

}
