package ImageLoaders

import Images.{BitmapImage, RGBColor}

import scala.util.Random

/**
 * Creates random noise of pixels and returns it as an image
 */
class RandomImageLoader extends ImageLoader {
  private val rnd = new Random

  override def Get(): BitmapImage = {
    val height = rnd.nextInt().abs % (MaxHeight - MinHeight) + MinHeight
    val width = rnd.nextInt().abs % (MaxWidth - MinWidth) + MinWidth
    val res: Seq[Seq[RGBColor]] =
      (0 until height).map(y =>
        (0 until width).map(x =>
          RandomColor()
        )
      )
    new BitmapImage(res)
  }

  def MinWidth = 5

  def MaxWidth = 100

  def MinHeight = 5

  def MaxHeight = 100

  private def RandomColor(): RGBColor = {
    RGBColor(rnd.nextInt().abs % 256, rnd.nextInt().abs % 256, rnd.nextInt().abs % 256)
  }
}
