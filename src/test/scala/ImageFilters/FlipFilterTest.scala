package ImageFilters

import Images.ASCIIArt
import org.scalatest.FunSuite

class FlipFilterTest extends FunSuite{
  test("InvertFilterTest") {
    val values: Vector[Vector[Int]] = Vector(
      Vector(0, 100),
      Vector(150,200)
    )
    val shader: Map[Range, Char] = Map(
      (0 until 50) -> 'A',
      (50 until 120) -> 'B',
      (120 until 175) -> 'C',
      (175 until 256) -> 'D'
    )
    val orgImage = new ASCIIArt(values, shader)
    val filterX = new FlipFilter(true)
    val filterY = new FlipFilter(false)
    val invertImageX = filterX.EditImage(orgImage)
    val invertImageY = filterY.EditImage(orgImage)

    val expectedResX = Vector(
      Vector('C', 'D'),
      Vector('A', 'B')
    )
    val expectedResY = Vector(
      Vector('B', 'A'),
      Vector('D', 'C')
    )
    assert((0 until 2).forall(y => expectedResX(y).equals(invertImageX.GetRow(y))))
    assert((0 until 2).forall(y => expectedResY(y).equals(invertImageY.GetRow(y))))
  }

}
