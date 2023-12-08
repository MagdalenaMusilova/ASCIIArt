package Images

import org.w3c.dom.ranges.RangeException

case class RGBColor(val red : Int, val green : Int, val blue: Int){
  if (!(0 until 256).contains(red) ||
    !(0 until 256).contains(green) ||
    !(0 until 256).contains(blue)){
    throw new Exception("Invalid RGB values")
  }
}
