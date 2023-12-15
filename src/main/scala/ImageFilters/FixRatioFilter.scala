package ImageFilters
import Images.ASCIIArt

class FixRatioFilter extends ImageFilter {
  override def EditImage(image: ASCIIArt): ASCIIArt = {
    val values =
    (0 until image.height).map(y =>
      (0 until image.width*2).map(x =>
        image.GetValueAt(x/2, y)
      )
    )
    new ASCIIArt(values, image.GetShader())
  }
}
