import scala.io.Source
import breeze.linalg._

object M2 {

  def main(args: Array[String]): Unit = {

    val stream = getClass.getResourceAsStream("/cancer.csv")

    if (stream == null) {
      println("Error: File not found in resources folder!")
      return
    }

    val file = Source.fromInputStream(stream)

    val data = file.getLines()
      .drop(1)
      .flatMap { line =>
        val cols = line.split(",")
        if (cols.length > 2)
          cols(2).trim.toDoubleOption
        else
          None
      }
      .toList

    file.close()

    val cleanData = data.filter(!_.isNaN)

    val window = 3

    val sma = cleanData.sliding(window)
      .map(x => x.sum / window)
      .toList


    val weights = List(1, 2, 3)
    val weightSum = weights.sum.toDouble

    val wma = cleanData.sliding(window).map { values =>
      values.zip(weights)
        .map { case (v, w) => v * w }
        .sum / weightSum
    }.toList


    val alpha = 2.0 / (window + 1)

    var ema = List(cleanData.head)

    for (i <- 1 until cleanData.length) {
      val value = alpha * cleanData(i) + (1 - alpha) * ema.last
      ema = ema :+ value
    }


    println("\nIndex\tOriginal\tSMA\t\tWMA\t\tEMA")
    println("-------------------------------------------------------")


    for (i <- cleanData.indices) {

      val original = f"${cleanData(i)}%.2f"

      val smaValue =
        if (i >= window - 1)
          f"${sma(i - window + 1)}%.2f"
        else
          "-"

      val wmaValue =
        if (i >= window - 1)
          f"${wma(i - window + 1)}%.2f"
        else
          "-"

      val emaValue =
        f"${ema(i)}%.2f"


      println(
        s"$i\t$original\t\t$smaValue\t\t$wmaValue\t\t$emaValue"
      )
    }
  }
}