import java.time.LocalDate
import scala.util.Random

object M2_14 {

  case class DailySale(date: LocalDate, sales: Int)

  def main(args: Array[String]): Unit = {

    val random = new Random(42)
    val startDate = LocalDate.of(2026, 1, 1)

    val data = (0 until 30).map { day =>

      val date = startDate.plusDays(day)

      val weekendBonus =
        if (date.getDayOfWeek.getValue >= 6) 2000 else 0

      val sales =
        10000 + (day * 150) + weekendBonus +
          random.nextInt(2000) - 1000

      DailySale(date, sales)
    }.toVector

    println("===== DAILY SALES TIME SERIES =====")
    println(f"${"Date"}%-15s ${"Day"}%-12s ${"Sales (Rs.)"}%12s")
    println("-" * 42)

    data.foreach { record =>
      println(
        f"${record.date}%-15s " +
          f"${record.date.getDayOfWeek.toString}%-12s " +
          f"${record.sales}%12d"
      )
    }

    val totalSales = data.map(_.sales).sum
    val averageSales = totalSales.toDouble / data.length

    val highestSale = data.maxBy(_.sales)
    val lowestSale = data.minBy(_.sales)

    val firstWeekAverage =
      data.take(7).map(_.sales).sum / 7.0

    val lastWeekAverage =
      data.takeRight(7).map(_.sales).sum / 7.0

    val change = lastWeekAverage - firstWeekAverage

    println("\n===== TIME SERIES ANALYSIS =====")
    println(s"Total Sales: Rs. $totalSales")
    println(f"Average Daily Sales: Rs. $averageSales%.2f")
    println(s"Highest Sales: Rs. ${highestSale.sales} on ${highestSale.date}")
    println(s"Lowest Sales: Rs. ${lowestSale.sales} on ${lowestSale.date}")
    println(f"First 7 Days Average: Rs. $firstWeekAverage%.2f")
    println(f"Last 7 Days Average: Rs. $lastWeekAverage%.2f")

    if (change > 0) {
      println(f"Sales Trend: Increasing by Rs. $change%.2f")
    } else if (change < 0) {
      println(f"Sales Trend: Decreasing by Rs. ${-change}%.2f")
    } else {
      println("Sales Trend: Stable")
    }
  }
}