package IO

trait Output {
  def Print(text : String) : Unit
  def PrintLn(text : String) : Unit
  def PrintLines(texts: Vector[String], prefix : String = "") : Unit

  def Flush() : Unit
}
