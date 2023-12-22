package IO

import org.scalatest.FunSuite

class StringOutputTest extends FunSuite {
  test("StringOutputTest") {
    val out = new StringOutput
    val jokes = Seq(
      "Why did the programmer quit their job? They didn't get arrays of opportunities.",
      "How do you comfort a JavaScript bug? You console it.",
      "Why was the programming language so good at networking? It had great connections.",
      "What do you call a programmer from Finland? Nerdic.",
      "Why do programmers prefer dark mode? Because the light attracts bugs!",
      "What did one SQL database say to another? \"You complete me.\"",
      "Why did the programmer break up with Python? It couldn't commit.",
      "How do you organize a space party? You planet.",
      "Why did the developer go broke? Too many cache misses.",
      "What's a programmer's favorite place in New York? Times Square."
    )
    //joke0
    out.Print(jokes(0))
    //joke0
    out.PrintLn(jokes(0))
    //joke1 + joke2
    out.PrintLn(jokes(1))
    out.Print(jokes(2))
    //joke3 + joke4 + joke5
    out.PrintLn(jokes(3))
    out.Print(jokes(4))
    out.Print(jokes(5))
    //check if true
    assert(out.ReadLine() == jokes(0))
    assert(out.ReadLine() == jokes(0))
    assert(out.ReadLine() == jokes(1) + jokes(2))
    assert(out.ReadLine() == jokes(3) + jokes(4) + jokes(5))
    //check if printing and then reading works
    out.Print(jokes(9))
    assert(out.ReadLine() == jokes(9))
    //check if PrintLines work
    val prefix = "Joke: "
    out.PrintLines(jokes, prefix)
    assert((0 until 10).forall(y => out.ReadLine() == (prefix + jokes(y))))
  }
}
