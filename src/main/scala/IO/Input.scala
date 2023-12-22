package IO

trait Input {
  /**
   * Reads program arguments
   *
   * @return Array of arguments given at the start of the program
   */
  def ReadInputArgs(): Seq[String]
}
