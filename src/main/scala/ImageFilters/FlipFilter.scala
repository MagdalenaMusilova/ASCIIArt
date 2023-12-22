package ImageFilters

import Images.ASCIIArt

/**
 * Flips the image on either the X or Y axis
 *
 * @param flipX Should the image be flipped on the X axis (true) or on the Y axis (false)
 */
class FlipFilter(flipX: Boolean) extends ImageFilter {
  override def EditImage(image: ASCIIArt): ASCIIArt = {
    val value: Seq[Seq[Int]] =
      (0 until image.height).map(y =>
        (0 until image.width).map(x =>
          if (flipX) {
            image.GetValueAt(x, image.height - y - 1)
          } else {
            image.GetValueAt(image.width - x - 1, y)
          }
        )
      )
    new ASCIIArt(value, image.GetShader())
  }
}
