package UI.Commands

import UI.Commands.ExactCommandType.ExactCommandType

/**
 * Stores information about possible command from input
 *
 * @param command     The command name (how it should be inputted by user/showed to user)
 * @param arguments   The arguments of the commands
 * @param description What the command does
 * @param exactType   What exactly the command changes (exact type of filter, loader,...)
 */
case class Command(command: String,
                   arguments: Seq[String],
                   description: String,
                   exactType: ExactCommandType) {
  /**
   * How many characters does it take to print the whole format of command
   */
  def SizeOfCommand: Int = command.length + arguments.map(a => a.length + 1).sum

  /**
   * Converts command to one line string.
   *
   * @param paddingToDescription How much characters (blank and not blank) should be before the description
   */
  def ToOneLine(paddingToDescription: Int): String = {
    val res = s"$command ${arguments.mkString(" ")}"
    s"$res${(res.length until paddingToDescription).map(_ => " ").mkString("")}$description"
  }
}
