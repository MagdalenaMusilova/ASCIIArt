package IO

class ConsoleOutput extends Output {
  override def Print(text: String): Unit = {
    print(text)
  }

  override def PrintLn(text: String): Unit = {
    println(text)
  }

  override def PrintLines(texts: Vector[String], prefix : String): Unit = {
    texts.foreach(line => println(prefix + line))
  }

  override def Flush(): Unit = {
    Console.flush()
  }
}
