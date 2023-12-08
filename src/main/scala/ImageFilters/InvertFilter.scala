package ImageFilters
import Images.ASCIIArt

class InvertFilter extends ImageFilter {
  override def EditImage(image: ASCIIArt): ASCIIArt = {
    val value : Vector[Vector[Int]] =
      (0 until image.height).map(y =>
        (0 until image.width).map(x =>
          255 - image.GetValueAt(x,y)).toVector).toVector
    new ASCIIArt(value, image.GetShader())
  }
}