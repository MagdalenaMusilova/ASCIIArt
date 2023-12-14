package Images

import org.scalatest.FunSuite

class BitmapImageTest extends FunSuite{
  test("BitmapImageTestSuccess") {
    val values = Vector(
      Vector(RGBColor(0,0,0), RGBColor(5,100,244)),
      Vector(RGBColor(255,255,255), RGBColor(255,100,0))
    )
    val img = new BitmapImage(values)
    assert(img.height == values.length && img.width == values.head.length)
    assert((0 until img.height).forall(y =>
      (0 until img.width).forall(x =>
        img.GetAt(x,y) == values(y)(x)
      )
    ))
  }

  test("BitmapImageTestFail") {
    val values = Vector(
      Vector(RGBColor(0, 0, 0), RGBColor(5, 100, 244)),
      Vector(RGBColor(255, 255, 255))
    )
    intercept[Exception] {
      new BitmapImage(values)
    }
  }
}
