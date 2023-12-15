package ImageExporters

import IO.StringOutput
import Images.ASCIIArt
import org.scalatest.FunSuite

import java.io.File
import scala.io.Source

class MultiImageExporterTest extends FunSuite {
  test("MultiImageExporterTest") {
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
    val saver1: FileImageExporter = new FileImageExporter("tmp.txt")
    val outputA = new StringOutput()
    val saver2: OutputImageExporter = new OutputImageExporter(outputA)
    val outputB = new StringOutput()
    val saver3: OutputImageExporter = new OutputImageExporter(outputB)
    val saver =
      new MultiImageExporter(saver3,
        new MultiImageExporter(saver2, saver1))
    saver.Export(image)

    val expectedRes: Seq[String] = Seq(
      "OIO",
      "IXI",
      "OIX"
    )
    //check file exporter
    val source = Source.fromFile("tmp.txt")
    val res = source.getLines.toSeq
    source.close()
    new File("tmp.txt").delete()
    assert(res.equals(expectedRes))
    //check output exporter A
    assert((0 until 3).forall(y => expectedRes(y) == outputA.ReadLine()))
    //check output exporter B
    assert((0 until 3).forall(y => expectedRes(y) == outputB.ReadLine()))
  }

  test("MultiImageExporterTestEmptyImage"){
    val values: Seq[Seq[Int]] = Seq(Seq())
    val shader: Map[Range, Char] = Map((0 until 256) -> 'O')
    val image: ASCIIArt = new ASCIIArt(values, shader)
    val output1 = new StringOutput
    val output2 = new StringOutput
    val saver = new MultiImageExporter(
      new OutputImageExporter(output1),
      new OutputImageExporter(output2)
    )
    saver.Export(image)

    assert(output1.ReadLine().isEmpty)
    assert(output2.ReadLine().isEmpty)
  }

  test("MultiImageExporterTestInvalidExporter"){
    val values: Seq[Seq[Int]] = Seq(Seq())
    val shader: Map[Range, Char] = Map((0 until 256) -> 'O')
    val image: ASCIIArt = new ASCIIArt(values, shader)
    val output1 = new StringOutput
    val saver = new MultiImageExporter(
      new OutputImageExporter(output1),
      EmptyImageExporter
    )
    intercept[Exception] {
      saver.Export(image)
    }
  }

}
