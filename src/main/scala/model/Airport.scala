package model
import Nes._
case class Airport (id : Int , ident : String, name : String, iso_country : String)

object Airport {
      def airportFromLine(line: Array[String]): Either[String,Airport] = line.size < 9 match {
        case true => Left("Champs manquants")
        case _ => (util.Try(line(0).toInt).toOption, Nes(line(1)), Nes(line(3)), Nes(line(8))) match {
          case (Some(w),Some(x),Some(y),Some(z)) => Right(Airport(w, x.replace("\"","").replace("'"," "), y.replace("\"","").replace("'"," "), z.replace("\"","").replace("'"," ")))
          case _ => Left("id not an int or a field is empty")
        }
    }
}