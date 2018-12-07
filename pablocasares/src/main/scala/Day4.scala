import java.text.SimpleDateFormat
import java.util.Date

object Day4_1 extends DayApp {

  val DATE_FORMAT = "yyyy-MM-dd hh:mm"

  override def dayAlgorithm(inputLines: Iterator[String]): String = {
    val inputList: List[String] = inputLines.toList
    val sortedInputList = inputList.sortWith(sortByTime)
    val guardsMap = scala.collection.mutable.Map[Int, scala.collection.mutable.Map[Int, Int]]()

    var currentGuard = 0
    var fallAsleepTime = 0
    var isAsleep = false

    sortedInputList.foreach(f => {
      val currentTime = convertStringToMinutes(f.split("] ")(0).drop(1))
      if (f.contains("Guard #")) {
        currentGuard = f.split(" ")(3).drop(1).toInt
      } else if (f.contains("falls asleep") && !isAsleep) {
        fallAsleepTime = currentTime
        val minuteMap = guardsMap.getOrElse(currentGuard, scala.collection.mutable.Map[Int, Int]())
        minuteMap.put(currentTime, minuteMap.getOrElse(currentTime, 0) + 1)
        guardsMap.put(currentGuard, minuteMap)
      } else if (f.contains("falls asleep") && isAsleep) {
        fallAsleepTime = currentTime
        for (time <- fallAsleepTime until currentTime) {
          val minuteMap = guardsMap.getOrElse(currentGuard, scala.collection.mutable.Map[Int, Int]())
          minuteMap.put(time, minuteMap.getOrElse(time, 0) + 1)
          guardsMap.put(currentGuard, minuteMap)
        }
      } else if (f.contains("wakes up")) {
        val timeAsleep: Int = currentTime - fallAsleepTime
        isAsleep = false
        for (time <- fallAsleepTime until currentTime) {
          val minuteMap = guardsMap.getOrElse(currentGuard, scala.collection.mutable.Map[Int, Int]())
          minuteMap.put(time, minuteMap.getOrElse(time, 0) + 1)
          guardsMap.put(currentGuard, minuteMap)
        }
      }
    })

    val selectedGuard = guardsMap.map { case (k, v) => (k, v.values.sum) }.maxBy(_._2)._1
    val selectedMinute = guardsMap.get(selectedGuard).head.maxBy(_._2)._1
    println("Selected guard: " + selectedGuard)
    println("Selected minute: " + selectedMinute)

    (selectedGuard * selectedMinute).toString
  }

  def sortByTime(s1: String, s2: String) = {
    val string1 = s1.split("] ")(0).drop(1)
    val string2 = s2.split("] ")(0).drop(1)
    convertStringToDate(string1).getTime < convertStringToDate(string2).getTime
  }

  def convertStringToDate(s: String): Date = {
    val dateFormat = new SimpleDateFormat(DATE_FORMAT)
    dateFormat.parse(s)
  }

  def convertStringToMinutes(s: String): Int = {
    s.split(":")(1).toInt
  }

  runDay("Day 4 problem 1:", "input4.txt")
}

object Day4_2 extends DayApp {

  val DATE_FORMAT = "yyyy-MM-dd hh:mm"

  override def dayAlgorithm(inputLines: Iterator[String]): String = {
    val inputList: List[String] = inputLines.toList
    val sortedInputList = inputList.sortWith(sortByTime)
    val guardsMap = scala.collection.mutable.Map[Int, scala.collection.mutable.Map[Int, Int]]()

    var currentGuard = 0
    var fallAsleepTime = 0
    var isAsleep = false

    sortedInputList.foreach(f => {
      val currentTime = convertStringToMinutes(f.split("] ")(0).drop(1))
      if (f.contains("Guard #")) {
        currentGuard = f.split(" ")(3).drop(1).toInt
      } else if (f.contains("falls asleep") && !isAsleep) {
        fallAsleepTime = currentTime
        val minuteMap = guardsMap.getOrElse(currentGuard, scala.collection.mutable.Map[Int, Int]())
        minuteMap.put(currentTime, minuteMap.getOrElse(currentTime, 0) + 1)
        guardsMap.put(currentGuard, minuteMap)
      } else if (f.contains("falls asleep") && isAsleep) {
        fallAsleepTime = currentTime
        for (time <- fallAsleepTime until currentTime) {
          val minuteMap = guardsMap.getOrElse(currentGuard, scala.collection.mutable.Map[Int, Int]())
          minuteMap.put(time, minuteMap.getOrElse(time, 0) + 1)
          guardsMap.put(currentGuard, minuteMap)
        }
      } else if (f.contains("wakes up")) {
        val timeAsleep: Int = currentTime - fallAsleepTime
        isAsleep = false
        for (time <- fallAsleepTime until currentTime) {
          val minuteMap = guardsMap.getOrElse(currentGuard, scala.collection.mutable.Map[Int, Int]())
          minuteMap.put(time, minuteMap.getOrElse(time, 0) + 1)
          guardsMap.put(currentGuard, minuteMap)
        }
      }
    })

    val selectedGuard = guardsMap.map { case (k, v) => (k, v.maxBy(_._2)) }.maxBy(_._2._2)._1
    val selectedMinute = guardsMap.map { case (k, v) => (k, v.maxBy(_._2)) }.maxBy(_._2._2)._2._1
    println("Selected guard: " + selectedGuard)
    println("Selected minute: " + selectedMinute)

    (selectedGuard * selectedMinute).toString
  }

  def sortByTime(s1: String, s2: String) = {
    val string1 = s1.split("] ")(0).drop(1)
    val string2 = s2.split("] ")(0).drop(1)
    convertStringToDate(string1).getTime < convertStringToDate(string2).getTime
  }

  def convertStringToDate(s: String): Date = {
    val dateFormat = new SimpleDateFormat(DATE_FORMAT)
    dateFormat.parse(s)
  }

  def convertStringToMinutes(s: String): Int = {
    s.split(":")(1).toInt
  }

  runDay("Day 4 problem 2:", "input4.txt")
}