package ASCIIConvertor

class SequenceASCIIConvertor(shader : String)
  extends RangeASCIIConvertor(SequenceToRangeShaderConvertor.Convert(shader)) {

}
