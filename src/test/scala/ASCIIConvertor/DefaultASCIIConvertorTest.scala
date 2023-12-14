package ASCIIConvertor

import Images.{BitmapImage, RGBColor}
import org.scalatest.FunSuite

trait DefaultASCIIConvertorTest extends FunSuite{
  protected def runTest(convertor : ASCIIConvertor, shader : String){
    val expectedRes = shader + " "

    val values: Vector[RGBColor] = (0 to shader.length)
      .map(i => 255.min((i * (256d / shader.length)).toInt))
      .map(j => RGBColor(j, j, j)).toVector
    val image = new BitmapImage(Vector(values))
    val res = convertor.GetASCIIArt(image)
    val stringRes = (0 until res.width).map(x => res.GetAt(x, 0)).mkString("")
    assert(expectedRes.length == res.width && res.height == 1)
    assert(stringRes.equals(expectedRes))
  }
}
