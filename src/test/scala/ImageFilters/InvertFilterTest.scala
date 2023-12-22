package ImageFilters

import Images.ASCIIArt

class InvertFilterTest extends ImageFilterTest(new InvertFilter) {
  test("InvertFilterTest") {
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
    val filter = new InvertFilter()
    val invertImage = filter.EditImage(orgImage)

    val expectedRes = Seq(
      "XXX",
      "XIO",
      "OOO"
    )
    assert((0 until 3).forall(y => expectedRes(y).equals(invertImage.GetLineAt(y))))
  }

  test("InvertFilterTestDoubleInvert") {
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
    val filter = new InvertFilter()
    val res1 = filter.EditImage(orgImage)
    val res2 = filter.EditImage(res1)

    val expectedRes = Seq(
      "OOO",
      "OIX",
      "XXX"
    )
    assert((0 until 3).forall(y => expectedRes(y).equals(res2.GetLineAt(y))))
  }
}
