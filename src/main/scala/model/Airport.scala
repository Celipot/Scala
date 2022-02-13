package model
import Nes._
import IsoCountry._
import IdentAirport._

case class Airport (id : Int , ident : IdentAirport, name : Nes, iso_country : IsoCountry)

object Airport {
      def airportFromLine(line: Array[String]): Either[String,Airport] = line.size < 9 match {
        case true => Left("Champs manquants in airports")
        case _ => (util.Try(line(0).toInt).toOption,
        IdentAirport.fromString(line(1).replace("\"","").replace("'"," ")),
        Nes.fromString(line(3).replace("\"","").replace("'"," ")),
        IsoCountry.fromString(line(8).replace("\"","").replace("'"," "))) match {
          case (Some(w),Right(x),Right(y),Right(z)) => Right(Airport(w, x, y, z))
          case (w,x,y,z) => Left("Invalid field : " + 
            {if (w.equals(None)){line(0) +" is not an int"}} + " " +
            {if (x.isLeft){x.left.getOrElse("empty") +" : "+ line(1) }} + " " +
            {if (y.isLeft){"Name : "+ y.left.getOrElse("empty") +" : "+ line(3)}} + " " +
            {if (z.isLeft){z.left.getOrElse("empty") +" : "+ line(8)}}
          )
        }
    }
}