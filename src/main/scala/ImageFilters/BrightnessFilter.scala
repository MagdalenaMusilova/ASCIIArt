package ImageFilters

import Images.ASCIIArt

/**
 * Brightens or darkens an image
 *
 * @param brightness How much brighter the image should be (can be negative)
 */
class BrightnessFilter(brightness: Int) extends ImageFilter {
  override def EditImage(image: ASCIIArt): ASCIIArt = {
    val values: Seq[Seq[Int]] =
      (0 until image.height).map(y =>
        (0 until image.width).map(x =>
          (image.GetValueAt(x, y) + brightness).max(0).min(255)
        )
      )
    new ASCIIArt(values, image.GetShader())
  }
}
