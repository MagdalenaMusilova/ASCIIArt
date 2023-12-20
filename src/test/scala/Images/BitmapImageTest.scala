package Images

import org.scalatest.FunSuite

class BitmapImageTest extends FunSuite{
  test("BitmapImageTestSuccess") {
    val values = Seq(
      Seq(RGBColor(0,0,0), RGBColor(5,100,244)),
      Seq(RGBColor(255,255,255), RGBColor(255,100,0))
    )
    val img = new BitmapImage(values)
    assert(img.height == values.length && img.width == values.head.length)
    assert((0 until img.height).forall(y =>
      (0 until img.width).forall(x =>
        img.GetAt(x,y) == values(y)(x)
      )
    ))
  }

  test("BitmapImageTestUnevenRows") {
    val values = Seq(
      Seq(RGBColor(0, 0, 0), RGBColor(5, 100, 244)),
      Seq(RGBColor(255, 255, 255))
    )
    intercept[Exception] {
      new BitmapImage(values)
    }
  }

  test("BitmapImageTestInvalidIndex") {
    val black = RGBColor(0,0,0)
    val white = RGBColor(255,255,255)
    val values = Seq(
      Seq(black, white, white),
      Seq(black, white, black),
      Seq(black, black, white)
    )
    val img = new BitmapImage(values)
    intercept[Exception] {
      img.GetAt(0, 3)
    }
    intercept[Exception] {
      img.GetAt(3, 2)
    }
    intercept[Exception] {
      img.GetAt(-1, 2)
    }
    intercept[Exception] {
      img.GetAt(1, -2)
    }
    intercept[Exception] {
      img.GetAt(3, -2)
    }
    intercept[Exception] {
      img.GetAt(2, 4)
    }
    intercept[Exception] {
      img.GetAt(10, 2)
    }
    intercept[Exception] {
      img.GetAt(100, 20)
    }
  }
}
