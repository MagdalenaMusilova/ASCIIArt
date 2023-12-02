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

  def getAt(x : Int, y : Int) : RGBColor = {
    if (x < 0 || x >= width ||
        y < 0 || y >= height){
      throw new ArrayIndexOutOfBoundsException()
    }
    value(y)(x)
  }

  assert(value.isEmpty || value.forall(_.length == value.head.length))
}
