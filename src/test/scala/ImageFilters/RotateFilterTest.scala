package ImageFilters

import Images.ASCIIArt
import org.scalatest.FunSuite

class RotateFilterTest extends FunSuite{
  test("RotateFilterTest") {
    val values: Vector[Vector[Int]] = Vector(
      Vector(0,  10, 20, 30),
      Vector(40, 50, 60, 70),
      Vector(80, 90, 100, 110),
    )
    val shader: Map[Range, Char] = Map(
      (0 until 5) -> 'A',
      (5 until 15) -> 'B',
      (15 until 25) -> 'C',
      (25 until 35) -> 'D',
      (35 until 45) -> 'E',
      (45 until 55) -> 'F',
      (55 until 65) -> 'G',
      (65 until 75) -> 'I',
      (75 until 85) -> 'J',
      (85 until 95) -> 'K',
      (95 until 105) -> 'L',
      (105 until 256) -> 'M',

    )
    val orgImage = new ASCIIArt(values, shader)
    val filter = new RotateFilter(990)
    val rotatedImage = filter.EditImage(orgImage)
    val expectedRes = Vector(
      "DIM",
      "CGL",
      "BFK",
      "AEJ"
    )
    val resString = (0 until rotatedImage.height).map(y =>
      rotatedImage.GetRow(y).mkString(""))
    assert(expectedRes.length == rotatedImage.height)
    assert((0 until 3).forall(y => expectedRes(y).equals(resString(y))))
  }
}
