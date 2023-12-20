package ImageFilters

import Images.ASCIIArt
import org.scalatest.FunSuite

class FlipFilterTest extends ImageFilterTest(new FlipFilter(true)) {
  test("FlipFilterTestFlipX") {
    val values: Seq[Seq[Int]] = Seq(
      Seq(0, 100),
      Seq(150,200)
    )
    val shader: Map[Range, Char] = Map(
      (0 until 50) -> 'A',
      (50 until 120) -> 'B',
      (120 until 175) -> 'C',
      (175 until 256) -> 'D'
    )
    val orgImage = new ASCIIArt(values, shader)
    val filterX = new FlipFilter(true)
    val invertImageX = filterX.EditImage(orgImage)

    val expectedResX = Seq(
      "CD",
      "AB"
    )
    assert((0 until 2).forall(y => expectedResX(y).equals(invertImageX.GetLineAt(y))))
  }

  test("FlipFilterTestFlipY") {
    val values: Seq[Seq[Int]] = Seq(
      Seq(0, 100),
      Seq(150, 200)
    )
    val shader: Map[Range, Char] = Map(
      (0 until 50) -> 'A',
      (50 until 120) -> 'B',
      (120 until 175) -> 'C',
      (175 until 256) -> 'D'
    )
    val orgImage = new ASCIIArt(values, shader)
    val filterY = new FlipFilter(false)
    val invertImageY = filterY.EditImage(orgImage)

    val expectedResY = Seq(
      "BA",
      "DC"
    )
    assert((0 until 2).forall(y => expectedResY(y).equals(invertImageY.GetLineAt(y))))
  }

  test("FlipFilterTestDoubleFlip") {
    val values: Seq[Seq[Int]] = Seq(
      Seq(0, 100, 100),
      Seq(150, 200, 200),
      Seq(150, 0, 200),
    )
    val shader: Map[Range, Char] = Map(
      (0 until 50) -> 'A',
      (50 until 120) -> 'B',
      (120 until 175) -> 'C',
      (175 until 256) -> 'D'
    )
    val img = new ASCIIArt(values, shader)
    val filter = new FlipFilter(true)
    val res1 = filter.EditImage(img)
    val res = filter.EditImage(res1)
    assert(res.equals(img))
  }
}
