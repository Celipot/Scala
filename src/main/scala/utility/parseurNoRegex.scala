package utility

import scala.io.Source

object ParseurNoRegex {
  def FromFile[T](path: String, f: Array[String] => Either[String,T]): List[Either[String,T]] = {
        Source.fromFile(path).getLines()
            .toList
            .map(_.split(",")
            .map(_.trim))
            .map(line => f(line))
  }
  def Check[T](list : List[Either[String,T]]): Boolean= {
   val listRight =list.map(x => x match {case Right(x) => x case Left(x) => ()})
    if (listRight.size>=list.size*0.4){
      true
    }
    else{
      false
    }
  }
}