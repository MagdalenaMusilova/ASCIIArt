package UI.Commands

import UI.Commands.ExactCommandType.ExactCommandType

case class Command(command: String,
                   arguments: Seq[String],
                   description: String,
                   exactType: ExactCommandType){
  def RecommendedPadding : Int = command.length + arguments.map(a => a.length + 1).sum + 4

  def ToOneLine(paddingToDescription: Int) : String = {
    val res = s"$command ${arguments.mkString(" ")}"
    s"$res${(res.length until paddingToDescription).mkString(" ")}$description"
  }
}
