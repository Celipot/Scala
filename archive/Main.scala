import scala.io.Source;
import org.apache.spark;
import scala.annotation.tailrec


object Main extends App {

val path = ("textfile.csv")
val lines = Source.fromFile(path).getLines.toList
def readlist[String](list: List[String]):Unit = {
  list match {
    case head :: tail =>
      val p = deserialization(head.toString)
      println(printNewPoint(p))
      readlist(tail)
    case Nil =>
  }
}
case class point (val x: Int, val y: Int, val z: Option[Int] = None) {}

/*
val spark = org.apache.spark.sql.SparkSession.builder
        .master("local")
        .appName("Spark CSV Reader")
        .getOrCreate;

val df = spark.read.options(Map("delimiter"->",")).csv("path")
df.printSchema()
*/
def newPoint(x: Int, y: Int): point = point(x,y)
def newPoint(x: Int, y: Int, z:Int): point = point(x,y,Some(z))
def printNewPoint(p : Option[point]) : String = p match{
    case None => "The string is not a valid point"
    case p => p.toString
  }
def deserialization(line: String): Option[point] = {
    val pattern2 = """-?\d+,-?\d+""".r
    val pattern3 = """-?\d+,-?\d+,-?\d+""".r
    line match {
      case pattern2() => Some(newPoint(
          line.split(",")(0).toInt,
          line.split(",")(1).toInt
      ))
      case pattern3() => Some(newPoint(
          line.split(",")(0).toInt,
          line.split(",")(1).toInt,
          line.split(",")(2).toInt
      ))
      case _ => None
    }
  }
readlist(lines)

}
