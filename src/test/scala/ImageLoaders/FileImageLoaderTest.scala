package ImageLoaders

import Images.RGBColor
import org.scalatest.FunSuite

class FileImageLoaderTest extends FunSuite {
  test("FileImageLoaderTest") {
    val reader = new FileImageLoader("loaderTest1.png")
    val image = reader.Get()
    //first row (greys)
    assert(image.GetAt(0, 0) == new RGBColor(255, 255, 255))
    assert(image.GetAt(1, 0) == new RGBColor(128, 128, 128))
    assert(image.GetAt(2, 0) == new RGBColor(0, 0, 0))
    //second row (only 1 color)
    assert(image.GetAt(0, 1) == new RGBColor(255, 0, 0))
    assert(image.GetAt(1, 1) == new RGBColor(0, 255, 0))
    assert(image.GetAt(2, 1) == new RGBColor(0, 0, 255))
    //third row (mixed colors)
    assert(image.GetAt(0, 2) == new RGBColor(255, 255, 0))
    assert(image.GetAt(1, 2) == new RGBColor(0, 255, 255))
    assert(image.GetAt(2, 2) == new RGBColor(255, 0, 255))
  }

  test("FileImageLoaderTestFail") {
    val reader = new FileImageLoader("invalid.png")
    intercept[Exception] {
      val image = reader.Get()
    }
  }
}
