import db.DataConnec._

object main extends App {
  db.DataConnec.tablecreation()
  db.DataConnec.insertAirport()
  db.DataConnec.insertCountry()
  db.DataConnec.insertRunway()
  db.DataConnec.top10()
  db.DataConnec.toplatitude()
  db.DataConnec.countrysurface()
  db.DataConnec.queryCountryName("zimb")
  db.DataConnec.queryCountryCode("dz")
}


