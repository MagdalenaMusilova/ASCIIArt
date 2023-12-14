package ImageFilters
import Images.{ASCIIArt, BitmapImage}

import scala.math.log10

class ScaleFilter(scale : Double) extends ImageFilter {
  private def Shrink(image: ASCIIArt) : ASCIIArt = {
    val values =
    (0 until image.height/2).map(y =>
      (0 until image.width/2).map(x =>
        (image.GetValueAt(x*2,y*2) + image.GetValueAt(x*2+1,y*2) +
          image.GetValueAt(x*2,y*2+1) + image.GetValueAt(x*2+1,y*2+1))/4
      ).toVector).toVector
    new ASCIIArt(values, image.GetShader())
  }

  private def IncreaseSize(image: ASCIIArt) : ASCIIArt = {
    val values =
      (0 until image.height*2).map(y =>
        (0 until image.width*2).map(x =>
          image.GetValueAt(x/2, y/2)).toVector).toVector
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
}
