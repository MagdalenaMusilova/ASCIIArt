package ImageExporters

import IO.StringOutput
import Images.ASCIIArt
import org.scalatest.FunSuite

import java.io.File
import scala.io.Source

class OutputImageExporterTest extends FunSuite{
  test("OutputImageExporterTest") {
    val values: Vector[Vector[Int]] = Vector(
      Vector(0, 100, 0),
      Vector(100, 200, 100),
      Vector(0, 100, 200)
    )
    val shader: Map[Range, Char] = Map(
      (0 until 50) -> 'O',
      (50 until 150) -> 'I',
      (150 until 256) -> 'X'
    )
    val image: ASCIIArt = new ASCIIArt(values, shader)
    val output = new StringOutput()
    val saver: OutputImageExporter = new OutputImageExporter(output)
    saver.Export(image)

    val expectedRes: Vector[String] = Vector(
      "OIO",
      "IXI",
      "OIX"
    )

    assert((0 until 3).forall(y => expectedRes(y) == output.ReadLine()))
  }
}
