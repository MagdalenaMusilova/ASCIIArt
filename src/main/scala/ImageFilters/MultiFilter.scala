package ImageFilters
import Images.ASCIIArt

class MultiFilter(filter1: ImageFilter, filter2: ImageFilter) extends ImageFilter {
  override def EditImage(image: ASCIIArt): ASCIIArt = {
    val tmp = filter1.EditImage(image)
    filter2.EditImage(tmp)
  }
}
