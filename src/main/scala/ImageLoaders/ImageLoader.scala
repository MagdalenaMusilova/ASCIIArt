package ImageLoaders

import Images.BitmapImage

trait ImageLoader {
  def GetImage() : BitmapImage
}
