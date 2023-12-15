package ImageFilters

import Images.ASCIIArt
import org.scalatest.FunSuite

class BrightnessFilterTest extends ImageFilterTest(new BrightnessFilter(5)) {
  test("BrightnessFilterTest") {
    val values: Seq[Seq[Int]] = Seq(
      Seq(0, 120, 128, 130, 250, 255),
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

    val expectedBrightRes = "XIIIOO"
    val expectedDarkRes = "XXXXII"
    assert(expectedBrightRes.equals(brightImage.GetLineAt(0)))
    assert(expectedDarkRes.equals(darkImage.GetLineAt(0)))
  }

  test("BrightnessFilterTestZeroValue"){
    val values: Seq[Seq[Int]] = Seq(
      Seq(0, 120, 128, 130, 250, 255),
    )
    val shader: Map[Range, Char] = Map(
      (0 until 128) -> 'X',
      (128 until 255) -> 'I',
      (255 until 256) -> 'O'
    )
    val orgImage = new ASCIIArt(values, shader)
    val filter = new BrightnessFilter(0)
    val res = filter.EditImage(orgImage)
    assert(res.equals(orgImage))
  }
}
