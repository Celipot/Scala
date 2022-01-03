import parseurNoRegex._
import org.scalatest.FunSuite
 
 class CubeCalculatorTest extends FunSuite {
   test("assert") {
     val p = Point(2,1)
     assert(Point.pointFromLine(Array("2","1"))==Right(Point(2,1)))
   }
 }