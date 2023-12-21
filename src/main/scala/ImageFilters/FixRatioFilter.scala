package ImageFilters
import Images.ASCIIArt

/**
 * Doubles every character in row, not in column, thus fixing the image ratio
 */
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
