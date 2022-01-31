import com.rocketfuel.sdbc.PostgreSql._
import parseurNoRegex._
import Airport._
import Country._
import Runway._

import org.scalatest.FunSuite

 class CubeCalculatorTest extends FunSuite {
  test("d'un test") {
     assert(Math.pow(3,3) === 27)
   }

  test("select"){
    val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
    Select.iterator[Country]("select * from country Where ID = 302672"  /*"select * from country Where name like 'Aus%'"*/).foreach{line=>
      assert(line == Country(302672,"AD","Andorra"))
     }
    }
   }
  test("insert and creation"){
    val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
    Ignore.ignore("DROP TABLE IF EXISTS TestC")
    Ignore.ignore("CREATE TABLE TestC (id int PRIMARY KEY,code varchar(255), name varchar(255))")
    val request = (s"INSERT INTO TestC (id,code,name) VALUES (302672,'AD','Andorra')");
    Ignore.ignore(request)  
    Select.iterator[Country]("select * from TestC Where ID = 302672"  /*"select * from country Where name like 'Aus%'"*/).foreach{line=>
      assert(line == Country(302672,"AD","Andorra"))
     }
    }
   }


 }