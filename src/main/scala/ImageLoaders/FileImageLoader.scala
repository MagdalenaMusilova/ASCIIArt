package ImageLoaders

import Images.{BitmapImage, RGBColor}

import java.io.File
import javax.imageio.ImageIO

class FileImageLoader(filePath : String) extends ImageLoader {
  override def Get(): BitmapImage = {
    val img = ImageIO.read(new File(filePath))
    val imageData: Vector[Vector[RGBColor]] =
      (0 until img.getHeight())
        .map(y => (0 until img.getWidth)
          .map(x =>
            RGBColor(
              (img.getRGB(x, y) & 0xff0000) >> 16,
              (img.getRGB(x, y) & 0xff00) >> 8,
              (img.getRGB(x, y) & 0xff)
            )).toVector).toVector
    new BitmapImage(imageData)
  }
}
