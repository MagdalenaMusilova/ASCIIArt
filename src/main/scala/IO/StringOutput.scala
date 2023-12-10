package IO

import scala.collection.mutable

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

  override def PrintLines(texts: Vector[String], prefix: String): Unit = {
    texts.foreach(line => PrintLn(prefix + line))
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
