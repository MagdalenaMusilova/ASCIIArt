package UI.Commands

class CICommand[T](command: String,
                   arguments: Vector[String],
                   description: String,
                   val linkedClass:T) extends Command(command, arguments, description) {}
