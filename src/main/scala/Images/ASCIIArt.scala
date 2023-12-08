package Images

class ASCIIArt(value : Vector[Vector[Int]], shader : Map[Range, Char]) {
  val shadeScale : Vector[Char] = {
    (0 until 256)
      .map(a => shader.find(b => b._1.contains(a)).head._2).toVector
  }

  def height: Int = value.length

  def width: Int = {
    if (value.isEmpty) {
      0
    } else {
      value.head.length
    }
  }

  def GetAt(x : Int, y : Int) : Char = {
    if (x < 0 || x >= width ||
      y < 0 || y >= height) {
      throw new ArrayIndexOutOfBoundsException()
    }
    shadeScale(value(y)(x))
  }

  def GetLine(y: Int) : Vector[Char] = {
    value(y).map(a => shadeScale(a))
  }
}
