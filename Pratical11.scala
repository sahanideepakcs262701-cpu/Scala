import scala.io.Source

object Pratical11 {
  def main(args: Array[String]): Unit = {
    val filename = "Deepak.txt"

    val lines = Source.fromFile(filename).getLines().toList

    val words = lines
      .flatMap(_.toLowerCase.split("\\W+"))
      .filter(_.nonEmpty)

    val wordCounts = words.groupBy(identity).view.mapValues(_.size).toMap
    println("Word Frequencies:")
    wordCounts.toSeq.sortBy(-_._2).foreach { case (word, count) =>
      println(f"$word%-15s -> $count")
    }
  }
}

