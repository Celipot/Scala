package model

class IsoCountry(val iso: String) extends AnyVal

object IsoCountry {
    def fromString(str: String): Either[String,IsoCountry] = str.size match {
        case 2 => val regex = """^[A-Z]+""";
            str.matches(regex) match {
            case true => Right(new IsoCountry(str))
            case false => Left("IsoCountry is not valid")
        }
        case _ => Left("IsoCountry is not valid")
    }
}
