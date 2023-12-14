package ImageFilters
import Images.ASCIIArt

class RotateFilter(degree: Int) extends ImageFilter{
  private def Rotate90(image: ASCIIArt): ASCIIArt = {
    val values =
    (0 until  image.width).map(y =>
      (0 until image.height).map(x =>
        image.GetValueAt(y, image.height-x-1)
      ).toVector
    ).toVector
    new ASCIIArt(values, image.GetShader())
  }

  override def EditImage(image: ASCIIArt): ASCIIArt = {
    (0 until degree/90)
      .foldLeft(image)((img, _) => Rotate90(img))
  }
}
