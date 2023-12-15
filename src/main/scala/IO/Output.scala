package IO

trait Output {
  def Print(text : String) : Unit
  def PrintLn(text : String) : Unit
  def PrintLines(texts: Vector[String], prefix : String = "") : Unit = {
    if (texts.isEmpty) {
      PrintLn("")
    } else {
      texts.foreach(line => PrintLn(prefix + line))
    }
  }

  def Flush() : Unit
}
