package ImageFilters

import Images.ASCIIArt

trait ImageFilter {
  def EditImage(image : ASCIIArt) : ASCIIArt
}
