package IO

class ConsoleOutput extends Output {
  override def Print(text: String): Unit = {
    print(text)
  }

  override def PrintLn(text: String): Unit = {
    println(text)
  }

  override def Flush(): Unit = {
    Console.flush()
  }
}
