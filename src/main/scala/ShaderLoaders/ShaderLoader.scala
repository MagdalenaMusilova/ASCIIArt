package ShaderLoaders

trait ShaderLoader {
  def Get() : Map[Range, Char]
}
