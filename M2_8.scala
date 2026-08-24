import breeze.linalg._
import breeze.stats.distributions.Rand

object M2_8 {

  def euclideanDistance(a: DenseVector[Double], b: DenseVector[Double]): Double = {
    norm(a - b)
  }

  def main(args: Array[String]): Unit = {

    val numSamples = 200
    val numFeatures = 2
    val k = 2
    val maxIterations = 100

    println("Generating a synthetic dataset...")

    val cluster1 = DenseMatrix.zeros[Double](numSamples / 2, numFeatures)

    for (i <- 0 until numSamples / 2) {
      val point =
        DenseVector(1.0, 2.0) + DenseVector.rand(numFeatures, Rand.gaussian)

      cluster1(i, ::) := point.t
    }

    val cluster2 = DenseMatrix.zeros[Double](numSamples / 2, numFeatures)

    for (i <- 0 until numSamples / 2) {
      val point =
        DenseVector(5.0, 6.0) + DenseVector.rand(numFeatures, Rand.gaussian)

      cluster2(i, ::) := point.t
    }

    val data = DenseMatrix.vertcat(cluster1, cluster2)

    println(
      s"Generated dataset with ${data.rows} samples and ${data.cols} features."
    )

    var centroids = DenseMatrix.zeros[Double](k, numFeatures)

    val randomIndices =
      (0 until data.rows).toSeq.sortBy(_ => Rand.uniform.draw()).take(k)

    for (i <- 0 until k) {
      centroids(i, ::) := data(randomIndices(i), ::)
    }

    println(s"\nInitial centroids:\n$centroids")

    var assignments = DenseVector.zeros[Int](data.rows)
    var previousAssignments = DenseVector.fill[Int](data.rows)(-1)

    var iteration = 0
    var converged = false

    while (iteration < maxIterations && !converged) {

      println(s"\n--- Iteration ${iteration + 1} ---")

      for (i <- 0 until data.rows) {

        val point = data(i, ::).t

        var minDistance = Double.MaxValue
        var closestCentroid = -1

        for (j <- 0 until k) {

          val centroid = centroids(j, ::).t

          val distance = euclideanDistance(point, centroid)

          if (distance < minDistance) {
            minDistance = distance
            closestCentroid = j
          }
        }

        assignments(i) = closestCentroid
      }

      if (assignments == previousAssignments) {
        converged = true
      } else {
        previousAssignments = assignments.copy
      }

      val newCentroids =
        DenseMatrix.zeros[Double](k, numFeatures)

      val clusterCounts =
        DenseVector.zeros[Int](k)

      for (i <- 0 until data.rows) {

        val clusterId = assignments(i)

        val currentRow = newCentroids(clusterId, ::).t
        val dataRow = data(i, ::).t

        newCentroids(clusterId, ::) :=
          (currentRow + dataRow).t

        clusterCounts(clusterId) += 1
      }

      for (i <- 0 until k) {

        if (clusterCounts(i) > 0) {

          val centroid = newCentroids(i, ::).t

          newCentroids(i, ::) :=
            (centroid / clusterCounts(i).toDouble).t
        }
      }

      centroids = newCentroids

      println(s"Updated centroids:\n$centroids")

      iteration += 1
    }

    println("\n--- Final Results ---")

    println(
      s"K-means algorithm converged in $iteration iterations."
    )

    println(s"\nFinal centroids:\n$centroids")

    println(s"\nFinal cluster assignments:\n$assignments")
  }
}