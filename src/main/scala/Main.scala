import exo._
import DataConnec._
object main extends App {
  exo.execute()
  DataConnec.tablecreation()
  DataConnec.insertAirport()
  DataConnec.insertCountry()
  DataConnec.insertRunway()


}


