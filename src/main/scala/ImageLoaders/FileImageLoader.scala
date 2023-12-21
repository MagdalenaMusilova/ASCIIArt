package ImageLoaders

import Images.{BitmapImage, RGBColor}

import java.io.File
import javax.imageio.ImageIO

/**
 * Gets image from a file. Supports default image files
 * @param filePath Path to the file with an image
 */
class FileImageLoader(filePath : String) extends ImageLoader {
  override def Get(): BitmapImage = {
    val img = ImageIO.read(new File(filePath))
    val imageData: Seq[Seq[RGBColor]] =
      (0 until img.getHeight())
        .map(y => (0 until img.getWidth)
          .map(x =>
            RGBColor(
              (img.getRGB(x, y) & 0xff0000) >> 16,
              (img.getRGB(x, y) & 0xff00) >> 8,
              (img.getRGB(x, y) & 0xff)
            )))
    new BitmapImage(imageData)
  }
}
