package ImageFilters

import Images.ASCIIArt
import org.scalatest.FunSuite

class BrightnessFilterTest extends FunSuite{
  test("BrightnessFilterTest") {
    val values: Vector[Vector[Int]] = Vector(
      Vector(0, 120, 128, 130, 250, 255),
    )
    val shader: Map[Range, Char] = Map(
      (0 until 128) -> 'X',
      (128 until 255) -> 'I',
      (255 until 256) -> 'O'
    )
    val orgImage = new ASCIIArt(values, shader)
    val brightFilter = new BrightnessFilter(10)
    val darkFilter = new BrightnessFilter(-10)
    val brightImage = brightFilter.EditImage(orgImage)
    val darkImage = darkFilter.EditImage(orgImage)

    val expectedBrightRes = Vector('X', 'I', 'I', 'I', 'O', 'O')
    val expectedDarkRes = Vector('X', 'X', 'X', 'X', 'I', 'I')
    assert(expectedBrightRes.equals(brightImage.GetRow(0)))
    assert(expectedDarkRes.equals(darkImage.GetRow(0)))
  }
}
