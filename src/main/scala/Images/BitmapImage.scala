package Images

class BitmapImage(value : Vector[Vector[RGBColor]]){
  def height : Int = value.length
  def width : Int = {
    if (value.isEmpty){
      0
    } else {
      value.head.length
    }
  }

  def GetAt(x : Int, y : Int) : RGBColor = {
    if (!(0 until width).contains(x) ||
        !(0 until height).contains(y)){
      throw new ArrayIndexOutOfBoundsException()
    }
    value(y)(x)
  }

  if (!value.forall(_.length == value.head.length)) {
    throw new Exception("Image has varying widths")
  }
}
