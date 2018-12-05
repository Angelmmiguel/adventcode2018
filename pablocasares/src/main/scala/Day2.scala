
object Day2_1 extends DayApp {

  override def dayAlgorithm(inputLines : Iterator[String]): String ={
    var twiceCount = 0
    var threeTimesCount = 0
    for(line <- inputLines ){
      var foundTwice = false
      var foundThreeTimes = false
      line.chars().distinct().forEach(char => {
        if(line.count(_ == char) == 2 && !foundTwice){
          twiceCount += 1
          foundTwice = true
        }
        if(line.count(_ == char) == 3 && !foundThreeTimes){
          threeTimesCount += 1
          foundThreeTimes = true
        }
      })
    }
    (twiceCount*threeTimesCount).toString
  }

  runDay("Day 2 problem 1:", "input2.txt")
}

object Day2_2 extends DayApp {

  override def dayAlgorithm(inputLines : Iterator[String]): String = {

    val inputLinesList = inputLines.toList
    var found = false
    var solution = ""
    for(line <- inputLinesList if !found){
      for(line2 <- inputLinesList if !found){
        if(line.zip(line2).count{
          case (charA, charB) => charA!=charB
        } == 1){
         found = true
         solution = line.zip(line2).filter( p => p._1.equals(p._2)).map(_._1).mkString("")
        }
      }
    }
    solution
  }
  runDay("Day 2 problem 2:", "input2.txt")
}
