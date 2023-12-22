package ImageFilters

import Images.ASCIIArt

/**
 * Changes image by predefined rules
 */
trait ImageFilter {
  /**
   * Returns the changed image
   *
   * @param image Original image
   * @return Changed image
   */
  def EditImage(image: ASCIIArt): ASCIIArt
}
