package ImageLoaders
import Images.{BitmapImage, RGBColor}

import scala.util.Random

class RandomImageLoader extends ImageLoader {
  def MinWidth = 5
  def MaxWidth = 100
  def MinHeight = 5
  def MaxHeight = 100
  private val rnd = new Random

  def RandomColor() : RGBColor = {
    RGBColor(rnd.nextInt() % 256, rnd.nextInt() % 256, rnd.nextInt() % 256)
  }

  override def Get(): BitmapImage = {
    val height = rnd.nextInt() % (MaxHeight-MinHeight) + MinHeight
    val width = rnd.nextInt() % (MaxWidth-MinWidth) + MinWidth
    val res : Vector[Vector[RGBColor]] =
      (0 until height).map(y =>
        (0 until width). map(x => RandomColor()).toVector).toVector
    new BitmapImage(res)
  }
}
