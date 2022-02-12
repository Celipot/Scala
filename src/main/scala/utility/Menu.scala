package utility
import scala.io.StdIn.readLine
import db.DataConnec._


object Menu {
    def home(): Unit = { 
        println("Welcome to the Airport Database");
        println("Type \"1\" to search airports of a country");
        println("Type \"2\" to access reports");
        println("Type \"exit\" to exit.");
        readLine() match {
            case "1" => query()
            case "2" => report()
            case "exit" => ()
        }
    }

    def query(): Unit = {
        println("Query:");
        println("Type \"1\" to search country by name");
        println("Type \"2\" to to search country by code");
        println("Type \"exit\" to exit.");
        readLine() match {
            case "1" => queryName()
            case "2" => queryCode()
            case "exit" => home()
        }
    }

    def queryName(): Unit = {
        println("Enter the name of the country you want to search.");
        readLine() match {
            case x => println(x);
            query()
        }

    }

    
    def queryCode(): Unit = {
        println("Enter the code of the country you want to search.");
        readLine() match {
            case x => println(x)
            query()
        }
    }

    def report(): Unit = {
        println("Report:");
        println("Type \"1\" to see top 10 countries with highest number of airports and countries  with lowest number of airports");
        println("Type \"2\" to see type of runways  per country");
        println("Type \"3\" to see top 10 most common runway latitude");
        println("Type \"exit\" to exit.");
        readLine() match {
            case "1" => top10();
                report()
            case "2" => countrysurface();
                report()
            case "3" => toplatitude();
                report()
            case "exit" => home()
        }
    }
}
