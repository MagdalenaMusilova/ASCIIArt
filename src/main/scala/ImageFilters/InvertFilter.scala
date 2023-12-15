package ImageFilters
import Images.ASCIIArt

class InvertFilter extends ImageFilter {
  override def EditImage(image: ASCIIArt): ASCIIArt = {
    val values : Seq[Seq[Int]] =
      (0 until image.height).map(y =>
        (0 until image.width).map(x =>
          255 - image.GetValueAt(x,y)))
    new ASCIIArt(values, image.GetShader())
  }
}