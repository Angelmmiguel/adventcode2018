import scala.collection.mutable.ListBuffer

object Day7_1 extends DayApp {

  override def dayAlgorithm(inputLines: Iterator[String]): String = {

    val pattern = """.[a-z]+ (.) [a-z\s]+ (.) [a-z\s.]*""".r
    val jobs = scala.collection.mutable.Map[String, List[String]]()
    val parsedJobs = inputLines.toList.map(f => {
      val pattern(from, to) = f
      from -> to
    })

    val jobsAsMap = parsedJobs.groupBy(_._1).map { case (k, v) => k -> v.map(_._2) }

    NodeBuilder.createNodes(jobsAsMap)

    val root: Node = NodeBuilder.findRoot()

    NodeWorker.doJobs()

    NodeWorker.workString.toString()
  }

  object NodeBuilder {
    val nodes = scala.collection.mutable.ListBuffer[Node]()

    def createNodes(jobs: Map[String, List[String]]): Unit = {
      for (elem <- jobs.keys) {
        nodes += new Node(elem)
      }
      for (childs <- jobs.values) {
        for (child <- childs) {
          if (!nodes.exists(_.data == child)) {
            nodes += new Node(child)
          }
        }
      }
      fillNodes(jobs)
    }

    def fillNodes(jobs: Map[String, List[String]]): Unit = {
      nodes.foreach(node => {
        node.child ++= nodes.filter(n => jobs.getOrElse(node.data, List()).contains(n.data))
        node.child.foreach(child => if (!child.parents.contains(node)) child.parents += node)
      })
    }

    def findHeight(node: Node): Int = {
      if (node.child.isEmpty) {
        0
      } else {
        findHeight(node.child.maxBy(findHeight)) + 1
      }
    }

    def findRoot(): Node = {
      nodes.maxBy(findHeight)
    }
  }

  case class Node(data: String, child: scala.collection.mutable.ListBuffer[Node]) {
    var ready = false
    var done = false
    val parents: ListBuffer[Node] = scala.collection.mutable.ListBuffer[Node]()

    def this(value: String) = this(value, scala.collection.mutable.ListBuffer[Node]())
  }

  object NodeWorker {
    val workString: StringBuilder = scala.collection.mutable.StringBuilder.newBuilder

    def doJobs(): Unit = {
      while (NodeBuilder.nodes.count(_.done) != NodeBuilder.nodes.length) {
        val currentJob = NodeBuilder.nodes.filter(_.parents.isEmpty).filter(!_.done).minBy(_.data)
        workString.append(currentJob.data)
        currentJob.done = true
        currentJob.child.foreach(child => child.parents -= currentJob)
      }
    }
  }

  runDay("Day 7 problem 1:", "input7.txt")
}


object Day7_2 extends DayApp {

  override def dayAlgorithm(inputLines: Iterator[String]): String = {

    val pattern = """.[a-z]+ (.) [a-z\s]+ (.) [a-z\s.]*""".r
    val jobs = scala.collection.mutable.Map[String, List[String]]()
    val parsedJobs = inputLines.toList.map(f => {
      val pattern(from, to) = f
      from -> to
    })

    val jobsAsMap = parsedJobs.groupBy(_._1).map { case (k, v) => k -> v.map(_._2) }
    NodeBuilder.createNodes(jobsAsMap)

    val root: Node = NodeBuilder.findRoot()

    NodeWorker.doJobsWithTime().toString
  }

  object NodeBuilder {
    val nodes = scala.collection.mutable.ListBuffer[Node]()

    def createNodes(jobs: Map[String, List[String]]): Unit = {
      for (elem <- jobs.keys) {
        nodes += new Node(elem)
      }
      for (childs <- jobs.values) {
        for (child <- childs) {
          if (!nodes.exists(_.data == child)) {
            nodes += new Node(child)
          }
        }
      }
      fillNodes(jobs)
    }

    def fillNodes(jobs: Map[String, List[String]]): Unit = {
      nodes.foreach(node => {
        node.child ++= nodes.filter(n => jobs.getOrElse(node.data, List()).contains(n.data))
        node.child.foreach(child => if (!child.parents.contains(node)) child.parents += node)
      })
    }

    def findHeight(node: Node): Int = {
      if (node.child.isEmpty) {
        0
      } else {
        findHeight(node.child.maxBy(findHeight)) + 1
      }
    }

    def findRoot(): Node = {
      nodes.maxBy(findHeight)
    }
  }

  case class Node(data: String, child: scala.collection.mutable.ListBuffer[Node], timeToComplete: Int) {
    var ready = false
    var done = false
    val parents: ListBuffer[Node] = scala.collection.mutable.ListBuffer[Node]()

    def this(value: String) = this(value, scala.collection.mutable.ListBuffer[Node](), value.charAt(0).toInt - 4)
  }

  object NodeWorker {
    val workString: StringBuilder = scala.collection.mutable.StringBuilder.newBuilder
    val workers = 5

    def doJobsWithTime(): Int = {
      val workersTime = scala.collection.mutable.Map[String, Int]()
      var time = 0

      while (NodeBuilder.nodes.count(_.done) != NodeBuilder.nodes.length) {
        time += 1
        val currentJobs = NodeBuilder.nodes.filter(_.parents.isEmpty).filter(!_.done).sortBy(_.data).take(workers)
        currentJobs.foreach(jobToDo => {
          if (!workersTime.keys.toList.contains(jobToDo.data) && workersTime.keys.size <= workers) {
            workersTime.put(jobToDo.data, 0)
          }
        })
        currentJobs.foreach(job => {
          workersTime.put(job.data, workersTime(job.data) + 1)
          if (workersTime(job.data) == job.timeToComplete) {
            job.done = true
            job.child.foreach(child => child.parents -= job)
            workString.append(job.data)
            workersTime -= job.data
          }
        })
      }
      time
    }
  }

  runDay("Day 7 problem 2:", "input7.txt")
}

