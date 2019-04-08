package packt.spark.sec5

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object SparkWordcount {
	def main(args: Array[String]) {

  		// Create Spark configuration object
  		val conf = new SparkConf().setAppName("Spark Word Count")
  
    	// create Spark context with Spark configuration
  		val sc = new SparkContext(conf)
  
  		// Check whether sufficient params are supplied
  		if (args.length < 3) {
  				println("Usage: ScalaWordCount <threshold> <input> <output>")
  				System.exit(1)
  		}
  		
  		// get threshold
      val threshold = args(0).toInt
  		
  		//Read file and create RDD
  		val fileData = sc.textFile(args(1))		
  
  		// convert the lines into words using flatMap operation
  		// for each line, split the line in word by word.
  		val words = fileData.flatMap(line => line.split(" "))
  
  		// count the individual words using map and reduceByKey operation
  		// for each word, return a key/value tuple, with the word as key and 1 as value
  		// Sum all of the value with same key
  		val wordCount = words.map(word => (word, 1)).reduceByKey(_ + _)
  		
  		// filter out words with lesser than threshold value
      val filteredCount = wordCount.filter(_._2 >= threshold)
  
  		//Save to a text file
  		filteredCount.saveAsTextFile(args(2))
  
  		//stop the spark context
  		sc.stop
  }
}