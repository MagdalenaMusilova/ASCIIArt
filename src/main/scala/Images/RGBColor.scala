package Images

/**
 * Color made of three values - red, green and blue. Values are from 0 to 255
 *
 * @param red   the value of blue
 * @param green the value of green
 * @param blue  the value of blue
 */
case class RGBColor(red: Int, green: Int, blue: Int) {
  require((0 until 256).contains(red) &&
    (0 until 256).contains(green) &&
    (0 until 256).contains(blue))
}
