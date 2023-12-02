package Images

class BitmapImage(value : List[List[RGBColor]]){
  def height : Int = value.length
  def width : Int = {
    if (value.isEmpty){
      0
    } else {
      value.head.length
    }
  }

  assert(value.isEmpty || value.forall(_.length == value.head.length))
}
