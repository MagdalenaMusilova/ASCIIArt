package Images

case class RGBColor(red : Int, green : Int, blue: Int){
  require((0 until 256).contains(red) &&
    (0 until 256).contains(green) &&
    (0 until 256).contains(blue))
}
