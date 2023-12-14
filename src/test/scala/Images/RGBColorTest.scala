package Images

import org.scalatest.FunSuite

class RGBColorTest extends FunSuite{
  test("RGBColorTestSuccess") {
    RGBColor(0,0,0)
    RGBColor(24,128,5)
    RGBColor(255,255,255)
  }

  test("RGBColorTestFail") {
    intercept[Exception] {
      RGBColor(0, -1, 0)
    }
    intercept[Exception] {
      RGBColor(0, 2300, 0)
    }
    intercept[Exception] {
      RGBColor(256, 256, 256)
    }
    intercept[Exception] {
      RGBColor(0, -1, 256)
    }
  }
}
