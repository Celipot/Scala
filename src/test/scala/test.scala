import com.rocketfuel.sdbc.PostgreSql._
import utility.parseurNoRegex._
import model.Airport._
import model.Country._
import model.Runway._

import org.scalatest.FunSuite

 class CubeCalculatorTest extends FunSuite {
  test("parseur airport") {
     assert(FromFile[model.Airport]("src/main/ressource/airports.csv",model.Airport.airportFromLine)(1)== Right(model.Airport(6523,"00A","Total Rf Heliport","US")))
   }
  test("parseur country") {
     assert(FromFile[model.Country]("src/main/ressource/countries.csv",model.Country.countryFromLine)(1)== Right(model.Country(302672,"AD","Andorra")))
   }
  test("parseur runway") {
     assert(FromFile[model.Runway]("src/main/ressource/runways.csv",model.Runway.runwayFromLine)(1)== Right(model.Runway(269408,6523,"00A","ASPH-G","H1")))
   }

  test("select"){
    val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
    Select.iterator[model.Country]("select * from country Where ID = 302672"  /*"select * from country Where name like 'Aus%'"*/).foreach{line=>
      assert(line == model.Country(302672,"AD","Andorra"))
     }
    }
   }
  test("insert and creation"){
    val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
    Ignore.ignore("DROP TABLE IF EXISTS TestC")
    Ignore.ignore("CREATE TABLE TestC (id int PRIMARY KEY,code varchar(255), name varchar(255))")
    val request = (s"INSERT INTO TestC (id,code,name) VALUES (302672,'AD','Andorra')");
    Ignore.ignore(request)  
    Select.iterator[model.Country]("select * from TestC Where ID = 302672"  /*"select * from country Where name like 'Aus%'"*/).foreach{line=>
      assert(line == model.Country(302672,"AD","Andorra"))
     }
    }
   }
  test("Test string"){
    val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
    Select.iterator[String]("select iso_country, count (*) As nbr_airport  from airport group by iso_country order by nbr_airport DESC limit 10 ").foreach{line=>
      println(line)
     }
    }
   }

 }