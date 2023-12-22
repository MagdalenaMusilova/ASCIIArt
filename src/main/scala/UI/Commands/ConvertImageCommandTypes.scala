package UI.Commands

/**
 * Types of commands that are used for image conversion
 */
object ConvertImageCommandTypes extends Enumeration {
  type ConvertImageCommandType = Value
  val LOADER, EXPORTER, FILTER, CONVERTOR = Value
}
