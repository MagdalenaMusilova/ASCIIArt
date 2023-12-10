package ShaderLoaders

import org.scalatest.FunSuite

import java.io.{File, PrintWriter}
import scala.io.Source

class FileShaderLoaderTest extends FunSuite{
  test("FileShaderLoaderTest") {
    val shader : Map[Range, Char] = Map(
      (0 until 100) -> 'X',
      (0 until 200) -> 'I',
      (0 until 256) -> 'O'
    )

    val file = new File("tmp.txt")
    val pw = new PrintWriter(file)
    shader.foreach(row => {
      pw.write(s"${row._1.start}..${row._1.end} ${row._2}\n")
    })
    pw.close()

    val shaderLoader = new FileShaderLoader("tmp.txt")
    val res = shaderLoader.Get()

    Source.fromFile("tmp.txt").close()
    assert(res.equals(shader))
  }
}
