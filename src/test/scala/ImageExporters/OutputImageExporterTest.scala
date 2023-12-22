package ImageExporters

import IO.StringOutput
import Images.ASCIIArt
import org.scalatest.FunSuite

class OutputImageExporterTest extends FunSuite {
  test("OutputImageExporterTest") {
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
    val output = new StringOutput()
    val saver: OutputImageExporter = new OutputImageExporter(output)
    saver.Export(image)

    val expectedRes: Seq[String] = Seq(
      "OIO",
      "IXI",
      "OIX"
    )

    assert((0 until 3).forall(y => expectedRes(y) == output.ReadLine()))
  }

  test("OutputImageExporterTestEmptyImage") {
    val values: Seq[Seq[Int]] = Seq(Seq())
    val shader: Map[Range, Char] = Map((0 until 256) -> 'O')
    val image: ASCIIArt = new ASCIIArt(values, shader)
    val output = new StringOutput
    val saver = new OutputImageExporter(output)
    saver.Export(image)

    assert(output.ReadLine().isEmpty)
  }
}
