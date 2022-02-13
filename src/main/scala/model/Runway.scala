package model
import Nes._
import IdentAirport._

case class Runway (id : Int , airport_ref : Int, airport_ident : IdentAirport, surface : Nes, le_ident :Nes)

object Runway {
    def runwayFromLine(line: Array[String]): Either[String,Runway] = line.size < 9 match {
        case true => Left("Champs manquants")
        case _ => (util.Try(line(0).toInt).toOption,
        util.Try(line(1).toInt).toOption, 
        IdentAirport.fromString(line(2).replace("\"","").replace("'"," ")), 
        Nes.fromString(line(5).replace("\"","").replace("'"," ")), 
        Nes.fromString(line(8).replace("\"","").replace("'"," "))) match {
          case (Some(v),Some(w),Right(x),Right(y),Right(z)) => Right(Runway(v, w, x, y, z))
          case _ => Left("id or airport ref not an int or a field is empty")
        }
    }
}