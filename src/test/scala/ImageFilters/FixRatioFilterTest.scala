package ImageFilters

import Images.ASCIIArt

class FixRatioFilterTest extends ImageFilterTest(new FixRatioFilter) {
  test("FixRatioFilterTest") {
    val values: Seq[Seq[Int]] = Seq(
      Seq(255, 255, 128),
      Seq(130, 130, 130),
      Seq(0, 0, 255)
    )
    val shader: Map[Range, Char] = Map(
      (0 until 128) -> 'X',
      (128 until 255) -> 'I',
      (255 until 256) -> 'O'
    )
    val orgImage = new ASCIIArt(values, shader)
    val filter = new FixRatioFilter
    val res = filter.EditImage(orgImage)

    val expectedRes = Seq(
      "OOOOII",
      "IIIIII",
      "XXXXOO"
    )
    assert(res.height == expectedRes.length &&
          res.width == expectedRes.head.length)
    assert((0 until res.height).forall(y =>
      res.GetLineAt(y).equals(expectedRes(y))
    ))
  }
}
