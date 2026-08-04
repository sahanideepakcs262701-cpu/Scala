import scala.io.Source

object M2_4 {

  def main(args: Array[String]): Unit = {

    val fileName = "D:\\Deepak\\scala\\M2_4\\indexData.csv"

    val source = Source.fromFile(fileName)

    val lines = source.getLines().filter(_.trim.nonEmpty).toList

    source.close()

    if (lines.isEmpty) {
      println("CSV file is empty.")
      return
    }

    val header = lines.head.split(",").map(_.trim)

    val data = lines.tail.map(_.split(",").map(_.trim))

    val columnIndex = 2

    val top5 = data
      .sortBy(row => row(columnIndex).toDouble)
      .reverse
      .take(5)

    println("Top 5 Rows")
    println("===========")
    println(header.mkString("\t"))

    top5.foreach(row => println(row.mkString("\t")))
  }
}