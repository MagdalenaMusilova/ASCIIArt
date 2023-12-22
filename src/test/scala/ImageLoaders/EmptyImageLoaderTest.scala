package ImageLoaders

import org.scalatest.FunSuite

class EmptyImageLoaderTest extends FunSuite {
  test("EmptyImageLoaderTest") {
    val loader = EmptyImageLoader
    intercept[Exception] {
      loader.Get()
    }
  }
}
