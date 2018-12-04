import scala.collection.mutable.ListBuffer

object Day3_1 extends DayApp {

  override def dayAlgorithm(inputLines : Iterator[String]): String ={

    val claimArray = Array.ofDim[Int](1000,1000)

    for(line <- inputLines){
      val startPoint = line.split(" ")(2)
      val dimensions = line.split(" ")(3)
      val startPointX = startPoint.split(",")(0).toInt
      val startPointY = startPoint.split(",")(1).dropRight(1).toInt
      val dimensionsX = dimensions.split("x")(0).toInt
      val dimensionsY = dimensions.split("x")(1).toInt
      for{
        i <- startPointX until startPointX+dimensionsX
        j <- startPointY until startPointY+dimensionsY
      }claimArray(i)(j) = claimArray(i)(j) + 1
    }
    var totalValue: Int = 0
    claimArray.foreach(f => f.foreach(v => if (v > 1) totalValue += 1 ))

    totalValue.toString
  }

  runDay("Day 3 problem 1:", "input3.txt")
}

object Day3_2 extends DayApp {

  override def dayAlgorithm(inputLines: Iterator[String]): String = {

    val claimArray = Array.ofDim[String](1000, 1000)
    val allIdList = new ListBuffer[String]()
    import scala.collection.mutable.ListBuffer
    val banIdList = new ListBuffer[String]()
    for (line <- inputLines) {
      val id = line.split(" ")(0).drop(1)
      allIdList += id
      val startPoint = line.split(" ")(2)
      val dimensions = line.split(" ")(3)
      val startPointX = startPoint.split(",")(0).toInt
      val startPointY = startPoint.split(",")(1).dropRight(1).toInt
      val dimensionsX = dimensions.split("x")(0).toInt
      val dimensionsY = dimensions.split("x")(1).toInt

      for (
        i <- startPointX until startPointX + dimensionsX;
        j <- startPointY until startPointY + dimensionsY
      ) {
        if (claimArray(i)(j) == null) {
          claimArray(i)(j) = id
        } else {
          if (!banIdList.contains(id)) {
            banIdList += id
          }
          if (!banIdList.contains(claimArray(i)(j))) {
            banIdList += claimArray(i)(j)
          }
        }
      }

    }
    allIdList.filterNot { id => banIdList.contains(id) }.toList(0)
  }
  runDay("Day 3 problem 2:", "input3.txt")
}