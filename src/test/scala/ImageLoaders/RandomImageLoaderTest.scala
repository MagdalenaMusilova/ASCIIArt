package ImageLoaders

import org.scalatest.FunSuite

class RandomImageLoaderTest extends FunSuite {
  test("RandomImageLoaderTest") {
    val img = new RandomImageLoader().Get()
    assert(
      (new RandomImageLoader().MinWidth until new RandomImageLoader().MaxWidth).contains(img.width) &&
        (new RandomImageLoader().MinHeight until new RandomImageLoader().MaxHeight).contains(img.height)
    )
  }
}
