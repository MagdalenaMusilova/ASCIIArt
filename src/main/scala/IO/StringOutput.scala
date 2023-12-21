package IO

import scala.collection.mutable

/**
 * Can read what to printed by it
 */
class StringOutput extends Output {
  private val value: mutable.Queue[String] = mutable.Queue()
  private var lastValue : String = ""
  private var isEnqueueLastValue = false

  override def Print(text: String): Unit = {
    lastValue += text
    isEnqueueLastValue = true
  }

  override def PrintLn(text: String): Unit = {
    if (isEnqueueLastValue){
      value.enqueue(lastValue)
    }
    lastValue = text
    isEnqueueLastValue = true
  }

  def ReadLine() : String = {
    if (value.isEmpty){
      if (isEnqueueLastValue){
        val res = lastValue
        isEnqueueLastValue = false
        lastValue = ""
        res
      } else {
        throw new Exception("No value in StringOutput")
      }
    } else {
      value.dequeue()
    }
  }

  override def Flush(): Unit = {
    value.clear()
    lastValue = ""
    isEnqueueLastValue = false
  }
}
