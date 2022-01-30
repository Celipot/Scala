import com.rocketfuel.sdbc.PostgreSql._
import parseurNoRegex._
import Airport._
import Country._
import Runway._
object DataConnec{
      case class Person(id: Int, name: String)

  object Person {
    case class Name(name: String)
  }
  case class Student (id : Int, name: String)

def connection():Unit={
  
  val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
    Ignore.ignore("CREATE TABLE IF NOT EXISTS people (id SERIAL PRIMARY KEY, name varchar(255))")

    //Use named parameters and a case class to insert a row.
    Ignore.ignore("INSERT INTO people (name) VALUES (@name)", Parameters.product(Person.Name("jeff")))

    //prints "Person(1, jeff)"
    for (x <- Select.iterator[Person]("SELECT * FROM people"))
      println(x)

    /*
    You can select directly to tuples if you name your columns appropriately.
    If you select Tuple2s, you can create a map from the results.
    */

    //yields Map(1 -> "jeff")
    Select.iterator[(Int, String)]("SELECT id AS _1, name AS _2 FROM people").toMap
    Ignore.ignore("DROP TABLE people")
  }
}
def tablecreation ():Unit ={
  val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
    Ignore.ignore("DROP TABLE IF EXISTS Airport ")
    Ignore.ignore("DROP TABLE IF EXISTS Country")
    Ignore.ignore("DROP TABLE IF EXISTS Runway")
    Ignore.ignore("CREATE TABLE Airport (id int PRIMARY KEY, ident varchar(255),name varchar(255), iso_country varchar(255))")
    Ignore.ignore("CREATE TABLE Country (id int PRIMARY KEY,code varchar(255), name varchar(255))")
    Ignore.ignore("CREATE TABLE Runway (id int PRIMARY KEY,airport_ref int, airport_ident varchar(255),surface varchar(255),le_ident varchar(255))")
  }
}

def insertAirport (): Unit={
  val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
  val a = FromFile[Airport]("src/main/ressource/airports.csv",Airport.airportFromLine)
  a.foreach{line =>
    line match { 
      case Right(line)=>
      val request =(s"INSERT INTO Airport (id,ident,name,iso_country) VALUES (${line.id},'${line.ident}','${line.name}','${line.iso_country}')");
      Ignore.ignore(request)
      case Left(line) => println(line)
    } }
  }
}

def insertCountry (): Unit={
  val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
  val a = FromFile[Country]("src/main/ressource/countries.csv",Country.countryFromLine)
  a.foreach{line =>
    line match {
      case Right(line)=> 
      val request = (s"INSERT INTO Country (id,code,name) VALUES (${line.id},'${line.code}','${line.name}')");
      Ignore.ignore(request)
      case Left(line) => println(line)
    } }
  }
}
def insertRunway (): Unit={
  val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
  val a = FromFile[Runway]("src/main/ressource/runways.csv",Runway.runwayFromLine)
  a.foreach{line =>
    line match {
      case Right(line)=> 
      val request = (s"INSERT INTO Runway (id,airport_ref,airport_ident,surface,le_ident) VALUES (${line.id},${line.airport_ref},'${line.airport_ident}','${line.surface}','${line.le_ident}')");
      Ignore.ignore(request)
      case Left(line) => println(line)
    } }
  }
}
}