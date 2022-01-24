case class Nes(nes : Option[String])

object Nes {
    def apply(string : String): Option[String] = string.isEmpty match {
        case true => None
        case false => Some(string)
    }
}