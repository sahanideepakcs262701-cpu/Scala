import breeze.linalg.{DenseVector, euclideanDistance}

object M2_7 {

  case class DataPoint(features: DenseVector[Double], label: String)

  def main(args: Array[String]): Unit = {

    val dataset = Seq(
      DataPoint(DenseVector(1.0, 2.0), "A"),
      DataPoint(DenseVector(1.5, 2.5), "A"),
      DataPoint(DenseVector(5.0, 6.0), "B"),
      DataPoint(DenseVector(5.5, 6.5), "B")
    )

    println("Training data points:")
    dataset.foreach(p =>
      println(s"Features: ${p.features}, Label: ${p.label}")
    )

    val newPointFeatures = DenseVector(1.8, 2.3)

    println(s"\nNew data point to classify: $newPointFeatures")

    var minDistance = Double.MaxValue
    var predictedLabel = ""

    for (point <- dataset) {
      val dist = euclideanDistance(newPointFeatures, point.features)

      println(s"Distance to point with label '${point.label}': $dist")

      if (dist < minDistance) {
        minDistance = dist
        predictedLabel = point.label
      }
    }

    println("\nClassification Result:")
    println(s"The nearest neighbor is at a distance of: $minDistance")
    println(s"The predicted label for the new point is: $predictedLabel")
  }
}