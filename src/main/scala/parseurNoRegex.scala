import scala.io.Source
import Model._
object parseurNoRegex {

    case class Point(x:Int,y:Int,z:Option[Int] = None)

    object Point {
      def pointFromLine(line: Array[String]): Either[String,Point] = line match {
        case x if x.size == 2 => x.map(x => util.Try(x.toInt).toOption) match {
                                    case Array(Some(x),Some(y)) => Right(Point(x.toInt, y.toInt))
                                    case _ => Left("Format invalide")
                                }
        case x if x.size == 3 => x.map(x => util.Try(x.toInt).toOption) match {
                                    case Array(Some(x),Some(y),Some(z)) => Right(Point(x.toInt, y.toInt, Some(z.toInt)))
                                    case _ => Left("Format invalide")
                                }
        case _ => Left("Format invalide")
      }
    }

    object Country {
      def countryFromLine(line: Array[String]): Either[String,Country] = line.size match {
        case x < 3 => Left("Champs manquants")
        case _ => util.Try(line[0].toInt).toOption match {
          case Some(x) => Right(Country(x, line[1], line[2]))
          case _ => Left("id not an int")
        }
      }
    }

    object Airport {
      def airportFromLine(line: Array[String]): Either[String,Airport] = line.size match {
        case x < 4 => Left("Champs manquants")
        case _ => util.Try(line[0].toInt).toOption match {
          case Some(x) => Right(Airport(x, line[1], line[3]))
          case _ => Left("id not an int")
        }
      }
    }

    object Runway {
      def runwayFromLine(line: Array[String]): Either[String,Runway] = line.size match {
        case x < 9 => Left("Champs manquants")
        case _ => (util.Try(line[0].toInt).toOption,util.Try(line[1].toInt).toOption) match {
          case (Some(x),Some(y)) => Right(Runway(x, y, line[2], line[5], line[8]))
          case _ => Left("id or airport ref not an int")
        }
      }
    }
    
    def FromFile[T](path: String, f: Array[String] => Either[String,T]): List[Either[String,T]] = {
        Source.fromFile(path).getLines()
            .toList
            .map(_.split(",")
            .map(_.trim))
            .map(line => f(line))
    }
  }