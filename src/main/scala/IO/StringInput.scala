package IO

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class StringInput extends Input {
  val values: ArrayBuffer[String] = ArrayBuffer()

  def WriteLine(text : Vector[String]): Unit = {
    values.addAll(text)
  }

  def WriteLine(text: String): Unit = {
    values.addOne(text)
  }

  override def ReadInputArgs(): Vector[String] = {
    values.toVector
  }
}
