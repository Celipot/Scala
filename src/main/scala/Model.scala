case class country (id : Int , code : String , name : String, )
case class Airport (id : Int , ident : String, name : String, iso_country : String)
case class Runways (id : Int , airport_ref : Int, airport_ident : String, surface : String, le_ident :String)