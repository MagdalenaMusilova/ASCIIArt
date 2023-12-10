package UI.Commands

case class Command(val command: String,
                   val arguments: Vector[String],
                   val description: String){
  def RecommendedPadding : Int = command.length + arguments.map(a => a.length + 1).sum + 4

  def ToOneLine(paddingToDescription: Int) : String = {
    val res = s"$command ${arguments.mkString(" ")}"
    s"$res${(res.length until paddingToDescription).mkString(" ")}$description"
  }
}
