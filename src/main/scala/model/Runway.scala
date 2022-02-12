package model

case class Runway (id : Int , airport_ref : Int, airport_ident : String, surface : String, le_ident :String)

object Runway {
    def runwayFromLine(line: Array[String]): Either[String,Runway] = line.size < 9 match {
        case true => Left("Champs manquants")
        case _ => (util.Try(line(0).toInt).toOption, util.Try(line(1).toInt).toOption, Nes(line(2)), Nes(line(5)), Nes(line(8))) match {
          case (Some(v),Some(w),Some(x),Some(y),Some(z)) => Right(Runway(v, w, x.replace("\"","").replace("'"," "), y.replace("\"","").replace("'"," "), z.replace("\"","").replace("'"," ")))
          case _ => Left("id or airport ref not an int or a field is empty")
        }
    }
}