package UI.Commands

/**
 * General types of a command
 */
object CommandTypes extends Enumeration {
  type CommandType = Value
  val HELP, CONVERTIMAGE, OTHER = Value
}
