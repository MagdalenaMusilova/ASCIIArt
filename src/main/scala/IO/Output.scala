package IO

/**
 * Prints to an output
 */
trait Output {
  /**
   * Prints text
   */
  def Print(text : String) : Unit

  /**
   * Prints text and created a new line
   * @param text
   */
  def PrintLn(text : String) : Unit

  /**
   * Prints multiple lines
   * @param prefix What should be printed before each line
   */
  def PrintLines(texts: Seq[String], prefix : String = "") : Unit = {
    if (texts.isEmpty) {
      PrintLn("")
    } else {
      texts.foreach(line => PrintLn(prefix + line))
    }
  }

  def Flush() : Unit
}
