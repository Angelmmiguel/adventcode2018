

object Day6_1 extends DayApp {

  override def dayAlgorithm(inputLines: Iterator[String]): String = {
    val coordinatesString = inputLines.toList
    val coordinatesTuples = coordinatesString.map(s => (s.split(", ")(0).toInt, s.split(", ")(1).toInt))
    val minX: Int = coordinatesTuples.minBy(_._1)._1
    val minY: Int = coordinatesTuples.minBy(_._2)._2
    val maxX: Int = coordinatesTuples.maxBy(_._1)._1
    val maxY: Int = coordinatesTuples.maxBy(_._2)._2


    val proposedGrid = Array.ofDim[(Int, Int)](maxX + 1, maxY + 1)

    for (coordX <- minX until maxX + 1) {
      for (coordY <- minY until maxY + 1) {
        val currentPoint = (coordX, coordY)
        val proposedClosestPoint = coordinatesTuples.minBy(manhattanDistance(currentPoint, _))
        val proposedPathLength: Int = manhattanDistance(currentPoint, proposedClosestPoint)
        if (coordinatesTuples.count(t => manhattanDistance(currentPoint, t) == proposedPathLength) < 2) {
          proposedGrid(coordX)(coordY) = proposedClosestPoint
        }
      }
    }

    var proposedGridList: List[(Int, Int)] = proposedGrid.flatMap(f => f.toList).toList.filterNot(_ == null)

    for (xBorder <- Set(minX, maxX)) {
      for (yBorder <- minY until maxY + 1) {
        val pointToFilter = proposedGrid(xBorder)(yBorder)
        proposedGridList = proposedGridList.filterNot(_.equals(pointToFilter))
      }
    }

    for (yBorder <- Set(minY, maxY)) {
      for (xBorder <- minX until maxX + 1) {
        val pointToFilter = proposedGrid(xBorder)(yBorder)
        proposedGridList = proposedGridList.filterNot(_.equals(pointToFilter))
      }
    }

    proposedGridList.groupBy(identity).maxBy(_._2.size)._2.size.toString
  }

  def manhattanDistance(tuple1: (Int, Int), tuple2: (Int, Int)): Int = {
    Math.abs(tuple1._1 - tuple2._1) + Math.abs(tuple1._2 - tuple2._2)
  }

  runDay("Day 6 problem 1:", "input6.txt")
}
