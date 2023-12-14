package ImageExporters

import Images.ASCIIArt
import org.scalatest.FunSuite

class EmptyImageExporterTest extends FunSuite{
  test("EmptyImageExporterTest") {
    val values: Vector[Vector[Int]] = Vector(Vector(0))
    val shader: Map[Range, Char] = Map((0 until 256) -> 'O')
    val image: ASCIIArt = new ASCIIArt(values, shader)
    val saver = EmptyImageExporter
    intercept[Exception] {
      saver.Export(image)
    }
  }
}
