package model
import Nes._
import IsoCountry._

case class Country (id : Int , code : IsoCountry , name : Nes)

object Country {
    def countryFromLine(line: Array[String]): Either[String,Country] = line.size < 3 match {
        case true => Left("Champs manquants in countries")
        case _ => (util.Try(line(0).toInt).toOption,
        IsoCountry.fromString(line(1).replace("\"","").replace("'"," ")),
        Nes.fromString(line(2).replace("\"","").replace("'"," "))) match {
          case (Some(x),Right(y),Right(z)) => Right(Country(x, y, z))
          case (x,y,z) => Left("Invalid field : " + 
            {if (x.equals(None)){line(0) +" is not an int"}} + " " +
            {if (y.isLeft) {y.left.getOrElse("empty") +" : "+ line(1)}} + " " +
            {if (z.isLeft) {"Name : " + z.left.getOrElse("empty") +" : "+ line(2)}}
          )
        }
    }
}