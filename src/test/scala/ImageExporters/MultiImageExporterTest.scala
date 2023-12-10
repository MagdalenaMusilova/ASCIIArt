package ImageExporters

import IO.StringOutput
import Images.ASCIIArt
import org.scalatest.FunSuite

import java.io.File
import scala.io.Source

class MultiImageExporterTest extends FunSuite {
  test("MultiImageExporterTest") {
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
    val saver0 = EmptyImageExporter
    val saver1: FileImageExporter = new FileImageExporter("tmp.txt")
    val outputA = new StringOutput()
    val saver2: OutputImageExporter = new OutputImageExporter(outputA)
    val outputB = new StringOutput()
    val saver3: OutputImageExporter = new OutputImageExporter(outputB)
    val saver =
      new MultiImageExporter(saver3,
        new MultiImageExporter(saver2,
          new MultiImageExporter(saver1, saver0)))
    saver.Export(image)

    val expectedRes: Vector[String] = Vector(
      "OIO",
      "IXI",
      "OIX"
    )
    //check file exporter
    val source = Source.fromFile("tmp.txt")
    val res = source.getLines.toVector
    source.close()
    new File("tmp.txt").delete()
    assert(res.equals(expectedRes))
    //check output exporter A
    assert((0 until 3).forall(y => expectedRes(y) == outputA.ReadLine()))
    //check output exporter B
    assert((0 until 3).forall(y => expectedRes(y) == outputB.ReadLine()))
  }
}
