package ImageFilters

import Images.ASCIIArt
import org.scalatest.FunSuite

class ScaleFilterTest extends ImageFilterTest(new ScaleFilter(4)) {
  test("ScaleFilterTest") {
    val bigValues: Seq[Seq[Int]] = Seq(
      Seq(255, 255, 255, 255),
      Seq(255, 255, 0,   200),
      Seq(128, 0, 255, 10),
      Seq(0, 128, 255, 10)
    )
    val smallValues: Seq[Seq[Int]] = Seq(
      Seq(0,   128),
      Seq(130, 255)
    )
    val shader: Map[Range, Char] = Map(
      (0 until 128) -> 'X',
      (128 until 255) -> 'I',
      (255 until 256) -> 'O'
    )
    val bigImage = new ASCIIArt(bigValues, shader)
    val smallImage = new ASCIIArt(smallValues, shader)
    val scaleFilter = new ScaleFilter(4)
    val shrinkFilter = new ScaleFilter(0.5)
    val shrunkenImage = shrinkFilter.EditImage(bigImage)
    val scaledImage = scaleFilter.EditImage(smallImage)

    val shrunkenRes = Seq(
      "OI",
      "XI"
    )
    val scaledRes = Seq(
      "XXXXIIII",
      "XXXXIIII",
      "XXXXIIII",
      "XXXXIIII",
      "IIIIOOOO",
      "IIIIOOOO",
      "IIIIOOOO",
      "IIIIOOOO",
    )
    assert(shrunkenImage.height == shrunkenRes.length)
    assert((0 until shrunkenImage.height).forall(y => shrunkenRes(y).equals(shrunkenImage.GetLineAt(y))))
    assert(scaledImage.height == scaledRes.length)
    assert((0 until scaledImage.height).forall(y => scaledRes(y).equals(scaledImage.GetLineAt(y))))
  }

  test("ScaleFilterTestFail"){
    val values: Seq[Seq[Int]] = Seq(
      Seq(0, 128),
      Seq(130, 255)
    )
    val shader: Map[Range, Char] = Map(
      (0 until 128) -> 'X',
      (128 until 255) -> 'I',
      (255 until 256) -> 'O'
    )
    val img = new ASCIIArt(values, shader)
    intercept[Exception] {
      val filter = new ScaleFilter(3)
      filter.EditImage(img)
    }
    intercept[Exception]{
      val filter = new ScaleFilter(0.3)
      filter.EditImage(img)
    }
  }

  test("ScaleFilterTestShrinkScale") {
    val values: Seq[Seq[Int]] = Seq(
      Seq(0, 130),
      Seq(130, 255)
    )
    val shader: Map[Range, Char] = Map(
      (0 until 128) -> 'X',
      (128 until 255) -> 'I',
      (255 until 256) -> 'O'
    )
    val img = new ASCIIArt(values, shader)
    val scaleFilter = new ScaleFilter(4)
    val shrinkFilter = new ScaleFilter(0.5)
    val res1 = scaleFilter.EditImage(img)
    val res = shrinkFilter.EditImage(res1)

    val expectedRes = Seq(
      "XXII",
      "XXII",
      "IIOO",
      "IIOO"
    )
    assert(res.width == expectedRes.head.length &&
        res.height == expectedRes.length)
    assert((0 until res.height).forall(y =>
      res.GetLineAt(y).equals(expectedRes(y))))
  }
}
