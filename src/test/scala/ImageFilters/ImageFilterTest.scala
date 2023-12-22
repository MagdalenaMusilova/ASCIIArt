package ImageFilters

import Images.ASCIIArt
import org.scalatest.FunSuite

abstract class ImageFilterTest(defaultFilter: ImageFilter) extends FunSuite {
  test("EmptyImageTest") {
    val orgImage = new ASCIIArt(Seq(Seq()), Map((0 until 256) -> 'X'))
    val res = defaultFilter.EditImage(orgImage)
    assert(res.height == 0 && res.width == 0)
    assert(res.equals(orgImage))
  }
}
