package ImageLoaders

import Images.BitmapImage

trait ImageLoader {
  def Get() : BitmapImage
}
