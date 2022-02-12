package db

import com.rocketfuel.sdbc.PostgreSql._
import utility.parseurNoRegex._
import model.Airport._
import model.Country._
import model.Runway._

object DataConnec{
      case class Person(id: Int, name: String)

  object Person {
    case class Name(name: String)
  }
  case class Result(str : String, count : Int)
  case class ResultQuery(country: String, airport: String ,surface: String)
  case class ResultString(countryName : String, surfaces : String)
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
  val a = FromFile[model.Airport]("src/main/ressource/airports.csv",model.Airport.airportFromLine)
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
  val a = FromFile[model.Country]("src/main/ressource/countries.csv",model.Country.countryFromLine)
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
  val a = FromFile[model.Runway]("src/main/ressource/runways.csv",model.Runway.runwayFromLine)
  a.foreach{line =>
    line match {
      case Right(line)=> 
      val request = (s"INSERT INTO Runway (id,airport_ref,airport_ident,surface,le_ident) VALUES (${line.id},${line.airport_ref},'${line.airport_ident}','${line.surface}','${line.le_ident}')");
      Ignore.ignore(request)
      case Left(line) => println(line)
    } }
  }
}

 def top10 (): Unit={
    val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
    println("Highest number of airports")
    Select.iterator[Result]("""select iso_country, 
                            count (*) As nbr_airport 
                            from airport 
                            group by iso_country 
                            order by nbr_airport DESC 
                            limit 10 """).foreach{top10=>
                            println(s"Iso_country : ${top10.str} with ${top10.count} airport(s)")
    }
     println("\n Lowest number of airports")
    Select.iterator[Result]("""select iso_country, count (*) As nbr_airport 
                            from airport 
                            group by iso_country 
                            order by nbr_airport ASC 
                            limit 10 """).foreach{top10=>
      println(s"Iso_country : ${top10.str} with ${top10.count} airport(s)")
     }
    }
   }

  def countrysurface():Unit={
    val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
    println("\n List of the countries' surfaces")
    Select.iterator[ResultString](s"""SELECT country.name as "CountryName", string_agg(distinct surface, ', ') as "Surfaces" 
                    FROM runway 
                    INNER JOIN airport 
                    ON airport_ref = airport.id
				          	INNER JOIN country
				          	ON iso_country = country.code
					          GROUP BY country.name""").foreach{countrySurface=>
                      println(s"CountryName : ${countrySurface.countryName}, with surfaces : ${countrySurface.surfaces}")

                    }
  }
}
  def toplatitude():Unit = {
    val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
    println("\n most common le_ident and their count")
    Select.iterator[Result]("""SELECT le_ident, count(*) 
                            FROM runway 
                            GROUP BY le_ident 
                            ORDER BY count DESC 
                            LIMIT 10""").foreach{toplatitude=>
                            println(s"le_ident : ${toplatitude.str} with count : ${toplatitude.count}")
        }
    }
    }
  def queryCountryName(str : String): Unit={
    val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
    println("\n most common le_ident and their count")
    Select.iterator[ResultQuery](s"""SELECT country.name as "Cname", airport.name as "Airport",surface 
                    FROM runway 
                    INNER JOIN airport 
                    ON airport_ref = airport.id
				          	INNER JOIN country
				           	ON iso_country = country.code
				          	WHERE country.name Ilike '%${str}%'""").foreach{queryCountryName=>
                            println(s"Country Name : ${queryCountryName.country}, Airport : ${queryCountryName.airport}, Surface : ${queryCountryName.surface}")
        }
    }
  }
    def queryCountryCode(str : String): Unit={
    val conn = Connection.using("jdbc:postgresql://localhost:5432/test?user=postgres&password=password"){implicit connection =>
    println("\n most common le_ident and their count")
    Select.iterator[ResultQuery](s"""SELECT country.name as "Cname", airport.name as "Airport",surface,country.code 
                    FROM runway 
                    INNER JOIN airport 
                    ON airport_ref = airport.id
					          INNER JOIN country
					          ON iso_country = country.code
				          	WHERE country.code = UPPER('${str}')""").foreach{queryCountryCode=>
                            println(s"Country Name : ${queryCountryCode.country}, Airport : ${queryCountryCode.airport}, Surface : ${queryCountryCode.surface}")
        }
    }
  }
}