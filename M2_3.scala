import scala.io.Source
import java.io.PrintWriter
import java.io.File

object M2_3 {

  def main(args: Array[String]): Unit = {

    val fileName = "D:\\Deepak\\scala\\M2_3\\indexData.csv"
    val file = new File(fileName)

    if (!file.exists()) {
      val writer = new PrintWriter(file)
      writer.println("Index")
      writer.println("10")
      writer.println("20")
      writer.println("10")
      writer.println("30")
      writer.println("20")
      writer.println("10")
      writer.println("40")
      writer.println("30")
      writer.println("20")
      writer.println("50")
      writer.close()

      println("indexData.csv file created successfully.")
    }

    val source = Source.fromFile(fileName)

    val data = source
      .getLines()
      .drop(1)
      .filter(_.trim.nonEmpty)
      .map(_.trim.toInt)
      .toList

    source.close()

    val frequency = data
      .groupBy(identity)
      .view
      .mapValues(_.size)
      .toMap

    var cumulativeFrequency = 0

    println()
    println("Frequency Distribution")
    println("======================")
    println("Value\tFrequency\tCumulative Frequency")
    println("-------------------------------------------")

    frequency.toSeq.sortBy(_._1).foreach { case (value, freq) =>
      cumulativeFrequency += freq
      println(s"$value\t$freq\t\t$cumulativeFrequency")
    }

    println("-------------------------------------------")
    println(s"Total\t${data.size}\t\t$cumulativeFrequency")
  }
}