import breeze.linalg._

object Deepak_pratical7 {
  def main(args: Array[String]): Unit = {

    // Define two matrices
    val matrixA = DenseMatrix(
      (1.0, 2.0),
      (4.0, 5.0)
    )

    val matrixB = DenseMatrix(
      (6.0, 5.0),
      (3.0, 2.0)
    )

    println("Matrix A:")
    println(matrixA)

    println("\nMatrix B:")
    println(matrixB)

    // Element-wise Addition
    val addition = matrixA + matrixB

    // Element-wise Subtraction
    val subtraction = matrixA - matrixB

    // Element-wise Multiplication
    val multiplication = matrixA * matrixB

    // Element-wise Division
    val division = matrixA / matrixB

    println("\nElement-wise Addition:")
    println(addition)

    println("\nElement-wise Subtraction:")
    println(subtraction)

    println("\nElement-wise Multiplication:")
    println(multiplication)

    println("\nElement-wise Division:")
    println(division)
  }
}
