package model

class IdentAirport(val ident: String) extends AnyVal

object IdentAirport {
    def fromString(str: String): Either[String,IdentAirport] = { val regex = """^[A-Z\-0-9]+""";
        str.matches(regex) match {
            case true => Right(new IdentAirport(str))
            case false => Left("IdentAirport is not valid")
        }
    }
}
