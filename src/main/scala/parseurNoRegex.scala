import scala.io.Source
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
    
    def FromFile[T](path: String, f: Array[String] => Either[String,T]): List[Either[String,T]] = {
        Source.fromFile(path).getLines()
            .toList
            .map(_.split(",")
            .map(_.trim))
            .map(line => f(line))
    }
  }