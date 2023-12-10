package ImageFilters
import Images.ASCIIArt

object EmptyFilter extends ImageFilter {
  override def EditImage(image: ASCIIArt): ASCIIArt = {
    image
  }
}
