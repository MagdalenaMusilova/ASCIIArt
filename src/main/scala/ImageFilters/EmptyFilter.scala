package ImageFilters
import Images.ASCIIArt

/**
 * Doesn't change image in any way
 */
object EmptyFilter extends ImageFilter {
  override def EditImage(image: ASCIIArt): ASCIIArt = {
    image
  }
}
