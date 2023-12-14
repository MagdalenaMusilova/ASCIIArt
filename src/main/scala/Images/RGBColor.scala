package Images

case class RGBColor(val red : Int, val green : Int, val blue: Int){
  require((0 until 256).contains(red) &&
    (0 until 256).contains(green) &&
    (0 until 256).contains(blue))
}
