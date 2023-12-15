package ImageFilters

import Images.ASCIIArt
import org.scalatest.FunSuite

class MultiFilterTest extends ImageFilterTest(new MultiFilter(new FlipFilter(true), new InvertFilter())) {
  test("MultiFilterTest") {
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
    val filter0 = EmptyFilter
    val filter1 = new InvertFilter()
    val filter2 = new FlipFilter(true)
    val filter3 = new FlipFilter(false)
    val filter =
      new MultiFilter(filter3,
        new MultiFilter(filter2,
          new MultiFilter(filter1, filter0)))
    val invertImage = filter.EditImage(orgImage)

    val expectedRes = Seq(
      "OOO",
      "OIX",
      "XXX"
    )
    assert((0 until 3).forall(y => expectedRes(y).equals(invertImage.GetLineAt(y))))
  }

  test("MultiFilterTestNullified"){
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
    val img = new ASCIIArt(values, shader)
    val doubleInvertFilter= new MultiFilter(new InvertFilter, new InvertFilter)
    val doubleFlipFilter = new MultiFilter(
      new MultiFilter(new FlipFilter(true), new FlipFilter(true)),
      new MultiFilter(new FlipFilter(false), new FlipFilter(false))
    )
    val doubleRotateFilter = new MultiFilter(
      new RotateFilter(-270),
      new MultiFilter(new RotateFilter(180), new RotateFilter(90))
    )
    val doubleScaleFilter = new MultiFilter(new ScaleFilter(4), new ScaleFilter(0.25))
    val filter = new MultiFilter(
      new MultiFilter(doubleInvertFilter, doubleFlipFilter),
      new MultiFilter(doubleRotateFilter, doubleScaleFilter)
    )
    val res = filter.EditImage(img)
    assert(res.equals(img))
  }
}
