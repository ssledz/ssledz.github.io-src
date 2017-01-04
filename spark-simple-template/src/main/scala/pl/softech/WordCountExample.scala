package pl.softech

import org.apache.spark.{SparkConf, SparkContext}


object WordCountExample {

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("spark-simple-app").setMaster("local[*]")

    val sc = new SparkContext(conf)

    val textFile = sc.textFile("src/main/resources/words.txt")

    val counts = textFile.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .sortBy(-_._2)

    printf(counts.collect().mkString("\n"))

    sc.stop()
  }

}
