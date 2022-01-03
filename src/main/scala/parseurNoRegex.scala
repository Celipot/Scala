import scala.io.Source

object parseur {

    case class Point(x:Int,y:Int,z:Option[Int] = None)

    object Point {
      def pointFromLine(entry: String): Either[String,Point] = Option(entry).map(_.trim) match {
        case x if x.get.split(",").size == 2 => x.map{_.split(",")
                                            .map(x => util.Try(x.toInt).toOption) match {
                                              case Array(Some(x),Some(y)) => Right(Point(x.toInt, y.toInt))
                                              case _ => Left("Format invalide")
                                            }
                                          }.get
        case x if x.get.split(",").size == 3 => x.map{_.split(",")
                                            .map(x => util.Try(x.toInt).toOption) match {
                                              case Array(Some(x),Some(y),Some(z)) => Right(Point(x.toInt, y.toInt, Some(z.toInt)))
                                              case _ => Left("Format invalide")
                                            }
                                          }.get
        case _ => Left("Format invalide")
      }
    }

    def pointsFromLines(lines: List[String], f: String => Either[String,Point]): List[Either[String,Point]] = lines.map(x => f(x))
    
    def pointsFromFile(path: String): List[Either[String,Point]] = pointsFromLines(Source.fromFile(path).getLines().toList, Point.pointFromLine(_))

    println(pointsFromFile("points.txt"))
  }