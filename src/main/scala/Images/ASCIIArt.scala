package Images

class ASCIIArt(value : Vector[Vector[Int]], shader : Map[Range, Char]) {
  private val shadeScale : Vector[Char] = {
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
    if (!(0 until width).contains(x) ||
      !(0 until height).contains(y)) {
      throw new ArrayIndexOutOfBoundsException()
    }
    value(y)(x)
  }

  def GetAt(x : Int, y : Int) : Char = {
    shadeScale(GetValueAt(x,y))
  }

  def GetRow(y: Int) : Vector[Char] = {
    value(y).map(a => shadeScale(a))
  }

  require(value.isEmpty || value.forall(_.length == value.head.length))
}
