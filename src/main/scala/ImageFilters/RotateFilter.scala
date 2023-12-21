package ImageFilters
import Images.ASCIIArt

/**
 * Rotates the image by given amount of degrees
 * @param degree Degrees by which the image will be rotated (must be multiple of 90, can be negative, can be over 360)
 */
class RotateFilter(degree: Int) extends ImageFilter{
  private def Rotate90(image: ASCIIArt): ASCIIArt = {
    val values =
    (0 until  image.width).map(y =>
      (0 until image.height).map(x =>
        image.GetValueAt(y, image.height-x-1)
      )
    )
    new ASCIIArt(values, image.GetShader())
  }

  override def EditImage(image: ASCIIArt): ASCIIArt = {
    val num = {
      if (degree > 0) (degree%360)/90
      else (360 + degree%360)/90
    }
    (0 until num)
      .foldLeft(image)((img, _) => Rotate90(img))
  }

  if (degree % 90 != 0) {
    throw new Exception("Image can be rotated only by degree divisible by 90")
  }
}
