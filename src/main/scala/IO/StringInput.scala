package IO

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class StringInput extends Input {
  val values: mutable.Queue[Vector[String]] = mutable.Queue()

  def WriteLine(text : Vector[String]): Unit = {
    values.enqueue(text)
  }
  override def ReadLine(): Vector[String] = {
    values.dequeue()
  }
}
