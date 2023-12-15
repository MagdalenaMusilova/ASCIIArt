package ImageFilters

import Images.ASCIIArt

class FixRatioFilterTest extends ImageFilterTest(new FixRatioFilter) {
  test("FixRatioFilterTest") {
    val values: Vector[Vector[Int]] = Vector(
      Vector(0, 10, 128),
      Vector(130, 130, 130),
      Vector(255, 255, 25)
    )
    val shader: Map[Range, Char] = Map(
      (0 until 128) -> 'X',
      (128 until 255) -> 'I',
      (255 until 256) -> 'O'
    )
    val orgImage = new ASCIIArt(values, shader)
    val filter = new FixRatioFilter
    val res = filter.EditImage(orgImage)

    val expectedRes = Vector(
      Vector('O', 'O', 'O', 'O', 'I', 'I'),
      Vector('I', 'I', 'I', 'I', 'I', 'I'),
      Vector('X', 'X', 'X', 'X', 'O', 'O')
    )
    assert(res.height == expectedRes.length &&
          res.width == expectedRes.head.length)
    assert((0 until res.height).forall(y => res.GetRow(y).equals(expectedRes(y))))
  }
}
