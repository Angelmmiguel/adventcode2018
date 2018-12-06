
object Day5_1 extends DayApp {

  override def dayAlgorithm(inputLines: Iterator[String]): String = {
    val polymer = inputLines.next()
    val reactedPolymer = polymerReaction(polymer, 0, 1);

    reactedPolymer.size.toString
  }

  runDay("Day 5 problem 1:", "input5.txt")

  def polymerReaction(polymer: String, unitA: Int, unitB: Int): String = {
    if (unitB > polymer.length - 1) {
      println("Polymer completely reacted.")
      polymer
    }
    else {
      if (unitsReaction(polymer.charAt(unitA), polymer.charAt(unitB))) {
        polymerReaction(removeCharacterByIndex(removeCharacterByIndex(polymer, unitA), unitA), if (unitA > 0) unitA - 1 else 0, if (unitB > 1) unitB - 1 else 1)
      } else {
        polymerReaction(polymer, unitA + 1, unitB + 1)
      }
    }
  }

  def unitsReaction(unitA: Char, unitB: Char): Boolean = {
    unitA.toLower.equals(unitB.toLower) && ((unitA.isUpper && unitB.isLower) || unitA.isLower && unitB.isUpper)
  }

  def removeCharacterByIndex(s: String, index: Int): String = {
    s.take(index) ++ s.drop(index + 1)
  }
}


object Day5_2 extends DayApp {

  override def dayAlgorithm(inputLines: Iterator[String]): String = {
    val polymer = inputLines.next()
    val reactedPolymer = polymerReaction(polymer, 0, 1);
    val alreadyTestedChars = new scala.collection.mutable.ListBuffer[Char]

    var lowestSize = -1;
    reactedPolymer.foreach(c =>
      if (!alreadyTestedChars.contains(c.toLower)) {
        alreadyTestedChars.append(c.toLower)
        val newPolymer = polymer.filterNot(char => char.equals(c.toUpper) || char.equals(c.toLower))
        val polymerSize = polymerReaction(newPolymer, 0, 1).length
        if(lowestSize == -1 || lowestSize > polymerSize){
          lowestSize = polymerSize
        }
      })
    lowestSize.toString
  }

  runDay("Day 5 problem 2:", "input5.txt")

  def polymerReaction(polymer: String, unitA: Int, unitB: Int): String = {
    if (unitB > polymer.length - 1) {
      println("Polymer completely reacted.")
      polymer
    }
    else {
      if (unitsReaction(polymer.charAt(unitA), polymer.charAt(unitB))) {
        polymerReaction(removeCharacterByIndex(removeCharacterByIndex(polymer, unitA), unitA), if (unitA > 0) unitA - 1 else 0, if (unitB > 1) unitB - 1 else 1)
      } else {
        polymerReaction(polymer, unitA + 1, unitB + 1)
      }
    }
  }

  def unitsReaction(unitA: Char, unitB: Char): Boolean = {
    unitA.toLower.equals(unitB.toLower) && ((unitA.isUpper && unitB.isLower) || unitA.isLower && unitB.isUpper)
  }

  def removeCharacterByIndex(s: String, index: Int): String = {
    s.take(index) ++ s.drop(index + 1)
  }
}

