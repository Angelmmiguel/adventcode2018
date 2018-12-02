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
    var size = 0
    var freqList: mutable.MutableList[Int] = mutable.MutableList()
    var freq = 0
    var found = false
    val inputLinesList = inputLines.toList
    var solutionFound: List[(Int, mutable.MutableList[Int])] = List()
    freqList += 0
    while(!found) {
      for (elem <- inputLinesList) {
        freq += elem.toInt
        freqList += freq
      }
      solutionFound = freqList.groupBy(identity).find(_._2.size >= 2).toList
      if(solutionFound.nonEmpty){
        found = true
      }
    }
    //This is the solution
    solutionFound.head._1.toString
  }
  runDay("Day 1 problem 2:", "input1.txt")
}
