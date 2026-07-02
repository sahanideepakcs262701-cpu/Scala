object Question2 {
  def main(args: Array[String]): Unit = {


    val numbers = List(10, 20, 30, 20, 40, 20, 50,45,
      65,76,77,45,76,98,34,22,77,99,67,45)

    // Mean
    val mean = numbers.sum.toDouble / numbers.length

    // Median
    val sorted = numbers.sorted
    val n = sorted.length
    val median =
      if (n % 2 == 0)
        (sorted(n / 2 - 1) + sorted(n / 2)).toDouble / 2
      else
        sorted(n / 2)

    // Mode
    val mode = numbers.groupBy(identity).maxBy(_._2.size)._1

    println("Numbers: " + numbers)
    println("Mean = " + mean)
    println("Median = " + median)
    println("Mode = " + mode)
  }
}


