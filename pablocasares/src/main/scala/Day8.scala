import com.sun.tools.internal.ws.wsdl.framework.DuplicateEntityException

import scala.collection.mutable.ListBuffer

object Day8_1 extends DayApp {

  override def dayAlgorithm(inputLines: Iterator[String]): String = {

    val serialInput: List[String] = inputLines.toList.head.split(" ").toList
    val license: List[Int] = serialInput.map(_.toInt)

    processLicense(license)
    metadataSum.toString
  }

  var metadataSum = 0

  def processLicense(license: List[Int]): Int = {
    processLicense(license, 0)
  }

  def processLicense(license: List[Int], index: Int): Int = {
    val childs = license(index)
    val metaData = license(index + 1)
    var indexVar: Int = index + 2
    for (childNum <- 0 until childs) {
      indexVar = processLicense(license, indexVar)
    }
    val metaDataValue = license.slice(indexVar, indexVar + metaData).sum
    metadataSum += metaDataValue
    indexVar + metaData
  }

  runDay("Day 8 problem 1:", "input8.txt")
}



object Day8_2 extends DayApp {

  override def dayAlgorithm(inputLines: Iterator[String]): String = {

    val serialInput: List[String] = inputLines.toList.head.split(" ").toList
    val license: List[Int] = serialInput.map(_.toInt)

    processLicense(license).toString
  }

  def processLicense(license: List[Int]): Int = {
    processLicense(license, 0)._2
  }

  def processLicense(license: List[Int], index: Int): (Int, Int) = {
    val children = license(index)
    val metaData = license(index + 1)
    var indexVar: Int = index + 2
    var childValues = new ListBuffer[Int]()
    var result = (indexVar, 0)

    for (i <- 0 until children) {
      result = processLicense(license, result._1)
      childValues += result._2
    }

    val metaDataIndexes = license.slice(result._1, result._1 + metaData)
    var value = 0

    if (children == 0) {
      value = metaDataIndexes.sum
    } else {
      for (metadataIndex <- metaDataIndexes) {
        value += childValues.lift(metadataIndex-1).getOrElse(0)
      }
    }
    (result._1 + metaData, value)
  }

  runDay("Day 8 problem 2:", "input8.txt")
}
