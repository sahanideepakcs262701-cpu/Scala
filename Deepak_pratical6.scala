import breeze.linalg._

object Deepak_pratical6 {
  def main(args: Array[String]): Unit = {
    
    val matrix = DenseMatrix(
      (1, 2, 3, 4),
      (5, 6, 7, 8),
      (9, 10, 11, 12),
      (13, 14, 15, 16)
    )

    println("Original Matrix:")
    println(matrix)
    
    val subMatrix = matrix(1 to 2, 1 to 3)

    println("\nSub-Matrix:")
    println(subMatrix)
    
    val rowSums = sum(subMatrix(*, ::))
    
    val colSums = sum(subMatrix(::, *))

    println("\nRow Sums:")
    println(rowSums)

    println("\nColumn Sums:")
    println(colSums)
  }
}
