package Images

class ASCIIArt(values : Seq[Seq[Int]], shader : Map[Range, Char]) {
  private val shadeScale : Seq[Char] = {
    (0 until 256)
      .map(a => shader.find(b => b._1.contains(a)).head._2)
  }

  def height: Int = {
    if (width == 0) {
      0
    } else {
      values.length
    }
  }

  def width: Int = {
    if (values.isEmpty) {
      0
    } else {
      values.head.length
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
    values(y)(x)
  }

  def GetAt(x : Int, y : Int) : Char = {
    shadeScale(GetValueAt(x,y))
  }

  def GetLineAt(y: Int) : String = {
    (0 until width).map(x => GetAt(x,y)).mkString("")
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

  if (!values.forall(_.length == values.head.length)) {
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
  if (!values.forall(row => row.forall(x => (0 until 256).contains(x)))){
    throw new Exception("Invalid value of pixels")
  }
}
