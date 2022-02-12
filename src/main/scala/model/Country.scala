package model
import Nes._
case class Country (id : Int , code : String , name : String)

object Country {
    def countryFromLine(line: Array[String]): Either[String,Country] = line.size < 3 match {
        case true => Left("Champs manquants")
        case _ => (util.Try(line(0).toInt).toOption,Nes(line(1)),Nes(line(2))) match {
          case (Some(x),Some(y),Some(z)) => Right(Country(x, y.replace("\"","").replace("'"," "), z.replace("\"","").replace("'"," ")))
          case _ => Left("id not an int or a field is empty")
        }
    }
}