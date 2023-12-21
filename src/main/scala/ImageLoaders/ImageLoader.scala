package ImageLoaders

import Images.BitmapImage

/**
 * Returns an image
 */
trait ImageLoader {
  /**
   * Returns an image
   */
  def Get() : BitmapImage
}
