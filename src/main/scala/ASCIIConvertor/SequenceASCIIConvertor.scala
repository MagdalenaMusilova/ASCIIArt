package ASCIIConvertor

/**
 * Converts image to ASCII art with linear shader table.
 *
 * @param shader What ranges of pixel values should be translated to what character
 */
class SequenceASCIIConvertor(shader: String)
  extends RangeASCIIConvertor(SequenceToRangeShaderConvertor.Convert(shader)) {

  if (shader.length > 256 || shader.isEmpty) {
    throw new Exception("Invalid size of ASCII conversion table")
  }
}
