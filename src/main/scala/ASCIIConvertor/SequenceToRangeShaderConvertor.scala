package ASCIIConvertor

object SequenceToRangeShaderConvertor {
  def Convert(sequence : String): Map[Range, Char] = {
    val rangeSize: Double = 256d / sequence.length
    (0 until sequence.length).map(i =>
      (((i * rangeSize)).toInt until ((i + 1) * rangeSize).toInt) -> sequence(i)).toMap
  }
}
