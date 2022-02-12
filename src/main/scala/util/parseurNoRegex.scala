package util

import scala.io.Source

object parseurNoRegex {
  def FromFile[T](path: String, f: Array[String] => Either[String,T]): List[Either[String,T]] = {
        Source.fromFile(path).getLines()
            .toList
            .map(_.split(",")
            .map(_.trim))
            .map(line => f(line))
  }
}