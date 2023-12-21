package ImageFilters
import Images.{ASCIIArt, BitmapImage}

import scala.math.log10

/**
 * Scales or shrinks an image
 * @param scale How much bigger or smaller the image should be (can't be less or equal to zero)
 */
class ScaleFilter(scale : Double) extends ImageFilter {
  private def Shrink(image: ASCIIArt) : ASCIIArt = {
    val values =
    (0 until image.height/2).map(y =>
      (0 until image.width/2).map(x =>
        (image.GetValueAt(x*2,y*2) + image.GetValueAt(x*2+1,y*2) +
          image.GetValueAt(x*2,y*2+1) + image.GetValueAt(x*2+1,y*2+1))/4
      ))
    new ASCIIArt(values, image.GetShader())
  }

  private def IncreaseSize(image: ASCIIArt) : ASCIIArt = {
    val values =
      (0 until image.height*2).map(y =>
        (0 until image.width*2).map(x =>
          image.GetValueAt(x/2, y/2)))
    new ASCIIArt(values, image.GetShader())
  }

  override def EditImage(image: ASCIIArt): ASCIIArt = {
    val numberOfIncreases : Int = (log10(scale)/log10(2)).abs.toInt
    if (scale < 1){
      (0 until numberOfIncreases)
        .foldLeft(image)((img, _) => Shrink(img))
    } else {
      (0 until numberOfIncreases)
        .foldLeft(image)((img, n) => IncreaseSize(img))
    }
  }

  if (scale < 0){
    throw new Exception("Image can't be scaled by a negative value")
  }
  if ((log10(scale)/log10(2)) % 1 != 0){
    throw new Exception("Image can be scaled only by a value that is a power of 2")
  }
}
