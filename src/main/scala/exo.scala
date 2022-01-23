  object exo{
  def compteVote(votes: List[(Integer,String)], bibli: Bibli): List[(Integer,String)] = {
    def aux(result: List[(Integer,String)], votes: List[(Integer,String)]): List[(Integer,String)] = votes match {
      case Nil =>  result
      case head::tail => (bibli.contains(head._2.toLowerCase())) match {
        case None => aux(result,tail)
        case Some(x) if (result.map(y => y._2).contains(x.ecritures.head.toLowerCase())) => {val index = result.map(y => y._2).indexOf(x.ecritures.head.toLowerCase());
          aux(result.updated(index,(result(index)._1 + head._1, x.ecritures.head.toLowerCase())), tail)
        }
        case Some(x) if (!result.map(y => y._2).contains(x.ecritures.head.toLowerCase())) => aux((head._1,head._2.toLowerCase())::result, tail)
      }
    }
    aux(List(), votes)
  }

  case class Lang(ecritures: List[String])


  case class Bibli(langs: List[Lang]) {
    def contains(ecriture: String): Option[Lang] =  {
      def aux(ecriture: String, langs: List[Lang]): Option[Lang] = langs match {
        case Nil => None
        case head::tail if (head.ecritures.contains(ecriture)) => Some(head)
        case head::tail => aux(ecriture, tail)
      }
      aux(ecriture, langs)
    }
  }

  val bibli = Bibli(List(Lang(List("ook!","ook ook")), Lang(List("lolcode")), Lang(List("intercal")), Lang(List("brainfuck")), Lang(List("ArnoldC"))))
  
  
  def execute(): Unit = {println(compteVote(List((1, "oOk!"), (1, "lolcode"), (3, "intercal"), (3, "ook ook"), (1, "brainfuck"), (2, "ArnoldC")), bibli))}
  }