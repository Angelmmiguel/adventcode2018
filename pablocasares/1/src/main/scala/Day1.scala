import scala.collection.mutable

object Day1_1 extends DayApp {

  override def dayAlgorithm(inputLines : Iterator[String]): String ={
    var freq : Int = 0
    inputLines.foreach(inc_freq => freq += inc_freq.toInt)
    freq.toString
  }

  runDay("Day 1 problem 1:", "input1.txt")
}

object Day1_2 extends DayApp {

  override def dayAlgorithm(inputLines : Iterator[String]): String ={
    var freq = 0
    var found = false
    val inputLinesList = inputLines.toList
    var solutionFound: Int = 0
    var iteration = 0
    var freqSeen: Seq[Int] = Seq()
    freqSeen :+ 0
    while(!found) {
      iteration += 1
      println(iteration)
      for (elem <- inputLinesList if !found) {
        freq += elem.toInt
        if(!freqSeen.contains(freq)){
          freqSeen = freqSeen :+ freq
        }else{
          found = true
          solutionFound = freq
        }
      }
    }

    //This is the solution
    solutionFound.toString
  }
  runDay("Day 1 problem 2:", "input1.txt")
}
