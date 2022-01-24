import com.rocketfuel.sdbc.PostgreSql._

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
}