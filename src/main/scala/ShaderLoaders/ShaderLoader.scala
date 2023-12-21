package ShaderLoaders

/**
 * Loads non-linear shader table
 */
trait ShaderLoader {
  /**
   * Returns non-linear shader table
   */
  def Get() : Map[Range, Char]
}
