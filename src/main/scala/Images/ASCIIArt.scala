package Images

class ASCIIArt(value : Vector[Vector[Int]], shader : Map[Range, Char]) {
  private val shadeScale : Vector[Char] = {
    (0 until 256)
      .map(a => shader.find(b => b._1.contains(a)).head._2).toVector
  }

  def height: Int = {
    if (width == 0) {
      0
    } else {
      value.length
    }
  }

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

  override def equals(b : Any): Boolean = {
    b match {
      case img : ASCIIArt =>
        shadeScale.equals(img.shadeScale) &&
          (0 until height).forall(y =>
            (0 until width).forall(x =>
              GetAt(x,y) == img.GetAt(x,y)
            )
          )
      case _ => false
    }
  }

  if (!value.forall(_.length == value.head.length)) {
    throw new Exception("Image has varying widths")
  }
  if (shader.exists(a => shader.exists(b => !a._1.equals(b._1) && (a._1 intersect b._1).nonEmpty))) {
    throw new Exception("Overlapping values in shader")
  }
  if (!(0 until 256).forall(i =>
    shader.keys.exists(a => a.contains(i)))) {
    throw new Exception("Shader doesn't cover from 0 to 255")
  }
  if (!shader.forall(x => x._1.min >= 0 && x._1.max < 256)) {
    throw new Exception("Shader contains invalid values")
  }
}
