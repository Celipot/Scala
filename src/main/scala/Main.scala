import db.DataConnec._

object main extends App {
  // DataConnec.tablecreation()
  // DataConnec.insertAirport()
  // DataConnec.insertCountry()
  // DataConnec.insertRunway()
  db.DataConnec.top10()
  db.DataConnec.toplatitude()
  db.DataConnec.countrysurface()
  db.DataConnec.queryCountryName("zimb")
  db.DataConnec.queryCountryCode("dz")
}


