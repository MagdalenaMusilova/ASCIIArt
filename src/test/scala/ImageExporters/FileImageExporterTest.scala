package ImageExporters

import Images.ASCIIArt
import org.scalatest.FunSuite

import java.io.File
import scala.io.Source

class FileImageExporterTest extends FunSuite {
  test("FileImageExporterTest") {
    val values: Seq[Seq[Int]] = Seq(
      Seq(0, 100, 0),
      Seq(100, 200, 100),
      Seq(0, 100, 200)
    )
    val shader: Map[Range, Char] = Map(
      (0 until 50) -> 'O',
      (50 until 150) -> 'I',
      (150 until 256) -> 'X'
    )
    val image: ASCIIArt = new ASCIIArt(values, shader)
    val saver: FileImageExporter = new FileImageExporter("tmp.txt")
    saver.Export(image)

    val expectedRes: Seq[String] = Seq(
      "OIO",
      "IXI",
      "OIX"
    )
    val source = Source.fromFile("tmp.txt")
    val res = source.getLines.toSeq
    source.close()
    new File("tmp.txt").delete()

    assert(res.length == expectedRes.length)
    assert((0 until res.length).forall(y =>
      res(y).equals(expectedRes(y))
    ))
  }

  test("FileImageExporterTestCorruptedFile") {
    val img = new ASCIIArt(Seq(Seq(0, 1)), Map((0 until 256) -> 'X'))
    val exporter = new FileImageExporter("\\/@")
    intercept[Exception](
      exporter.Export(img)
    )
  }

  test("FileImageExporterTestEmptyImage") {
    val values: Seq[Seq[Int]] = Seq(Seq())
    val shader: Map[Range, Char] = Map((0 until 256) -> 'O')
    val image: ASCIIArt = new ASCIIArt(values, shader)
    val saver: FileImageExporter = new FileImageExporter("tmp.txt")
    saver.Export(image)

    val source = Source.fromFile("tmp.txt")
    val res = source.getLines.toSeq
    source.close()
    new File("tmp.txt").delete()

    assert(res.isEmpty)
  }
}
