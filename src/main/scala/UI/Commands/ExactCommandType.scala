package UI.Commands

/**
 * The exact type of a command (meaning not only a "loader command", but "file loader command")
 */
object ExactCommandType extends Enumeration {
  type ExactCommandType = Value
  val HELP,
  FILELOADER, RANDOMLOADER,
  FILEEXPORTER, OUTPUTEXPORTER,
  DEFAULTCONVERTOR, SEQUENCECONVERTOR, RANGECONVERTOR,
  ROTATEFILTER, SCALEFILTER, INVERTFILTER, FLIPFILTER, BRIGHTNESSFILTER, FIXRATIO
  = Value
}
