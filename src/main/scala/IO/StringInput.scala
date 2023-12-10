package IO

import scala.collection.mutable

class StringInput extends Input {
  val values: mutable.Queue[String] = mutable.Queue()

  def WriteLine(text : String): Unit = {
    values.enqueue(text)
  }
  override def ReadLine(): String = {
    values.dequeue()
  }
}
