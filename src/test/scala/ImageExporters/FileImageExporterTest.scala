package ImageExporters

import Images.ASCIIArt
import org.scalatest.FunSuite

import java.io.File
import scala.io.Source

class FileImageExporterTest extends FunSuite{
  test("FileImageExporterTest") {
    val values : Vector[Vector[Int]] = Vector(
      Vector(0, 100, 0),
      Vector(100, 200, 100),
      Vector(0, 100, 200)
    )
    val shader : Map[Range, Char] = Map(
      (0 until 50) -> 'O',
      (50 until 150) -> 'I',
      (150 until 256) -> 'X'
    )
    val image : ASCIIArt = new ASCIIArt(values, shader)
    val saver : FileImageExporter = new FileImageExporter( "tmp.txt")
    saver.Export(image)

    val expectedRes : Vector[String] = Vector(
      "OIO",
      "IXI",
      "OIX"
    )
    val source = Source.fromFile("tmp.txt")
    val res = source.getLines.toVector
    source.close()
    new File("tmp.txt").delete()

    assert(res.equals(expectedRes))
  }

  test("FileImageExporterTestCorruptedFile") {
    val img = new ASCIIArt(Vector(Vector(0,1)), Map((0 until 256) -> 'X'))
    val exporter = new FileImageExporter("\\/@")
    intercept[Exception](
      exporter.Export(img)
    )
  }

  test("FileImageExporterTestEmptyImage") {
    val values: Vector[Vector[Int]] = Vector(Vector())
    val shader: Map[Range, Char] = Map((0 until 256) -> 'O')
    val image: ASCIIArt = new ASCIIArt(values, shader)
    val saver: FileImageExporter = new FileImageExporter("tmp.txt")
    saver.Export(image)

    val source = Source.fromFile("tmp.txt")
    val res = source.getLines.toVector
    source.close()
    new File("tmp.txt").delete()

    assert(res.length == 0)
  }
}
