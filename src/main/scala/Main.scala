import parseurNoRegex._
object main extends App {

  println(FromFile[Point]("textfile.csv",Point.pointFromLine(_)))

}
