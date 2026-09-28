
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.classification.LogisticRegressionModel
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

object M2_13 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("M2_13_Spark_MLlib_Classification")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._

    val data = (0 until 100).map { i =>

      val age = 20 + (i % 40)
      val salary = 20000.0 + ((i * 1379) % 60000)

      val label =
        if (age + salary / 2000.0 >= 55.0) 1.0
        else 0.0

      (age.toDouble, salary, label)
    }.toDF("age", "salary", "label")

    println("=" * 70)
    println("       SPARK MLLIB LOGISTIC REGRESSION CLASSIFICATION")
    println("=" * 70)

    println("\n1. GENERATED CUSTOMER DATA")
    data.show(10, truncate = false)

    val Array(trainingData, testData) =
      data.randomSplit(Array(0.8, 0.2), seed = 42L)

    println(s"Training Records: ${trainingData.count()}")
    println(s"Testing Records : ${testData.count()}")

    val assembler = new VectorAssembler()
      .setInputCols(Array("age", "salary"))
      .setOutputCol("features")

    val logisticRegression = new LogisticRegression()
      .setFeaturesCol("features")
      .setLabelCol("label")
      .setMaxIter(50)

    val pipeline = new Pipeline()
      .setStages(Array(assembler, logisticRegression))

    println("\n2. TRAINING THE MODEL")

    val model = pipeline.fit(trainingData)

    println("Model trained successfully.")

    println("\n3. MAKING PREDICTIONS")

    val predictions = model.transform(testData)

    predictions.select(
      "age",
      "salary",
      "label",
      "prediction",
      "probability"
    ).show(20, truncate = false)

    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("label")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")

    val accuracy = evaluator.evaluate(predictions)

    val lrModel =
      model.stages(1).asInstanceOf[LogisticRegressionModel]

    println("\n4. MODEL RESULTS")
    println("-" * 70)

    println(s"Feature Coefficients : ${lrModel.coefficients}")
    println(f"Model Accuracy       : ${accuracy * 100}%.2f%%")

    println("-" * 70)
    println("CLASSIFICATION COMPLETED SUCCESSFULLY")
    println("=" * 70)

    spark.stop()
  }
}