import scala.io.Source

trait DayApp extends App {

  def readInput(inputFile: String) : Iterator[String] = {
    Source.fromResource(inputFile).getLines()
  }

  def runDay(day: String, fileInput: String): Unit = {
    println(day)
    println("Solution: "+ dayAlgorithm(readInput(fileInput)))
  }

  def dayAlgorithm(inputLines : Iterator[String]): String

}
