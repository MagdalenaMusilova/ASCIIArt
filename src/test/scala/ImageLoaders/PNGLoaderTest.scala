package ImageLoaders

import Images.RGBColor
import org.scalatest.FunSuite

class PNGLoaderTest extends FunSuite{
  test("LoadingJPG") {
    val reader = new PNGLoader("loaderTest1.png")
    val image = reader.GetImage()
    //first row (greys)
    assert(image.getAt(0,0) == new RGBColor(255,255,255))
    assert(image.getAt(1,0) == new RGBColor(128,128,128))
    assert(image.getAt(2,0) == new RGBColor(0,0,0))
    //second row (only 1 color)
    assert(image.getAt(0,1) == new RGBColor(255, 0, 0))
    assert(image.getAt(1,1) == new RGBColor(0, 255, 0))
    assert(image.getAt(2,1) == new RGBColor(0, 0, 255))
    //third row (mixed colors)
    assert(image.getAt(0,2) == new RGBColor(255, 255, 0))
    assert(image.getAt(1,2) == new RGBColor(0, 255, 255))
    assert(image.getAt(2,2) == new RGBColor(255, 0, 255))
  }
}
