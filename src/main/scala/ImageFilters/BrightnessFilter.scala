package ImageFilters
import Images.ASCIIArt

class BrightnessFilter(val brightness: Int) extends ImageFilter {
  override def EditImage(image: ASCIIArt): ASCIIArt = {
    val values: Vector[Vector[Int]] =
      (0 until image.height).map(y =>
        (0 until image.width).map(x =>
          (image.GetValueAt(x,y) + brightness).max(0).min(255)
        ).toVector
      ).toVector
    new ASCIIArt(values, image.GetShader())
  }
}
