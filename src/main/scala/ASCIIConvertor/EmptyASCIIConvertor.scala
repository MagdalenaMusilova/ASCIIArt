package ASCIIConvertor
import Images.{ASCIIArt, BitmapImage}

object EmptyASCIIConvertor extends ASCIIConvertor {
  override def GetASCIIArt(image: BitmapImage): ASCIIArt = {
    throw new Exception("Invalid ASCII convertor chosen")
  }
}
