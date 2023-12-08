package ASCIIConvertor

import Images.ASCIIArt

trait ASCIIConvertor {
  def GetASCIIArt(shader : Map[Range, Char]) : ASCIIArt
}
