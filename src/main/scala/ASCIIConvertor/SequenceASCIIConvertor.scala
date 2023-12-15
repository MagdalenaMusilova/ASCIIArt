package ASCIIConvertor

class SequenceASCIIConvertor(shader : String)
  extends RangeASCIIConvertor(SequenceToRangeShaderConvertor.Convert(shader)) {

  if (shader.length > 256 || shader.isEmpty){
    throw new Exception("Invalid size of ASCII conversion table")
  }
}
