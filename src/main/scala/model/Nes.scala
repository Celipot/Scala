package model

class Nes(val nes: String) extends AnyVal

object Nes {
    def fromString(str: String): Either[String,Nes] = str.isEmpty match {
        case true => Left("String is empty or not valid")
        case false => Right(new Nes(str))
    }
}
