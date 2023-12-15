package ShaderLoaders

import scala.io.Source

class FileShaderLoader(filePath: String) extends ShaderLoader {
  override def Get(): Map[Range, Char] = {
    val source = Source.fromFile(filePath)
    val textInput = source.getLines.toSeq
    source.close()

    var res: Map[Range, Char] = Map()
    textInput.foreach(line => res+=LineToRange(line))
    res
  }

  private def LineToRange(line: String) : (Range, Char) = {
    val lines : Seq[String] = line.trim.replace("..","->").split("->")
    if (lines.size != 3 ||
        !lines(0).forall(_.isDigit) ||
        !lines(1).forall(_.isDigit) ||
        lines(2).length != 1){
      throw new Exception("Corrupted shader file")
    }
    (lines(0).toInt until lines(1).toInt) -> lines(2).head
  }
}
