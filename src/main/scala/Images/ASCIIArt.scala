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

  def GetShader() : Map[Range, Char] = {
    shader
  }

  def GetValueAt(x : Int, y : Int) : Int = {
    if (x < 0 || x >= width ||
      y < 0 || y >= height) {
      throw new ArrayIndexOutOfBoundsException()
    }
    value(y)(x)
  }

  def GetAt(x : Int, y : Int) : Char = {
    shadeScale(GetValueAt(x,y))
  }

  def GetLine(y: Int) : Vector[Char] = {
    value(y).map(a => shadeScale(a))
  }
}
