package ImageFilters

import Images.ASCIIArt
import org.scalatest.FunSuite

class ScaleFilterTest extends FunSuite{
  test("ScaleFilterTest") {
    val bigValues: Vector[Vector[Int]] = Vector(
      Vector(255, 255, 255, 255),
      Vector(255, 255, 0,   200),
      Vector(128, 0, 255, 10),
      Vector(0, 128, 255, 10)
    )
    val smallValues: Vector[Vector[Int]] = Vector(
      Vector(0,   128),
      Vector(130, 255)
    )
    val shader: Map[Range, Char] = Map(
      (0 until 128) -> 'X',
      (128 until 255) -> 'I',
      (255 until 256) -> 'O'
    )
    val bigImage = new ASCIIArt(bigValues, shader)
    val smallImage = new ASCIIArt(smallValues, shader)
    val scaleFilter = new ScaleFilter(4)
    val shrinkFilter = new ScaleFilter(0.5)
    val shrunkenImage = shrinkFilter.EditImage(bigImage)
    val scaledImage = scaleFilter.EditImage(smallImage)

    val shrunkenRes = Vector(
      Vector('O', 'I'),
      Vector('X', 'I')
    )
    val scaledRes = Vector(
      Vector('X', 'X', 'X', 'X', 'I', 'I', 'I', 'I'),
      Vector('X', 'X', 'X', 'X', 'I', 'I', 'I', 'I'),
      Vector('X', 'X', 'X', 'X', 'I', 'I', 'I', 'I'),
      Vector('X', 'X', 'X', 'X', 'I', 'I', 'I', 'I'),
      Vector('I', 'I', 'I', 'I', 'O', 'O', 'O', 'O'),
      Vector('I', 'I', 'I', 'I', 'O', 'O', 'O', 'O'),
      Vector('I', 'I', 'I', 'I', 'O', 'O', 'O', 'O'),
      Vector('I', 'I', 'I', 'I', 'O', 'O', 'O', 'O'),
    )
    assert(shrunkenImage.height == shrunkenRes.length)
    assert((0 until shrunkenImage.height).forall(y => shrunkenRes(y).equals(shrunkenImage.GetRow(y))))
    assert(scaledImage.height == scaledRes.length)
    assert((0 until scaledImage.height).forall(y => scaledRes(y).equals(scaledImage.GetRow(y))))
  }
}
