package ImageLoaders
import Images.BitmapImage

/**
 * Invalid loader that doesn't get image from anywhere
 */
object EmptyImageLoader extends ImageLoader {
  override def Get(): BitmapImage = {
    throw new Exception("Invalid image loader chosen")
  }
}
