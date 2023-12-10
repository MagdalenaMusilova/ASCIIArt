package ImageLoaders
import Images.BitmapImage

object EmptyImageLoader extends ImageLoader {
  override def Get(): BitmapImage = {
    throw new Exception("Invalid image loader chosen")
  }
}
