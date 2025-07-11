package IO

import scala.collection.mutable.ArrayBuffer

/**
 * Returns what was stored inside of it
 */
class StringInput extends Input {
  private val values: ArrayBuffer[String] = ArrayBuffer()

  def WriteLine(text: Seq[String]): Unit = {
    values.addAll(text)
  }

  def WriteLine(text: String): Unit = {
    values.addOne(text)
  }

  override def ReadInputArgs(): Seq[String] = {
    values.toSeq
  }
}
