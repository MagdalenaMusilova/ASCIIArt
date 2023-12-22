package ASCIIConvertor

/**
 * Converts linear shader table to non-linear shader table.
 */
object SequenceToRangeShaderConvertor {
  /**
   * Returns non-linear shader table.
   *
   * @param sequence Linear shader table
   * @return Non-linear shader table
   */
  def Convert(sequence: String): Map[Range, Char] = {
    val rangeSize: Double = 256d / sequence.length
    (0 until sequence.length).map(i =>
      ((i * rangeSize).toInt until ((i + 1) * rangeSize).toInt) -> sequence(i)).toMap
  }
}
