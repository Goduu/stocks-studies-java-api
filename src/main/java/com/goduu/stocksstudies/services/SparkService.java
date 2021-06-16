package com.goduu.stocksstudies.services;

import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SparkService {

	@Autowired
	JavaSparkContext sc;

	public Map<String, Long> getCount(List<String> wordList) {
		JavaRDD<String> words = sc.parallelize(wordList);
		Map<String, Long> wordCounts = words.countByValue();
		return wordCounts;
	}

	public SparkSession getSpark() {
		return SparkSession.builder().appName("Java Spark SQL basic example")
				.config("spark.some.config.option", "some-value").getOrCreate();
	}

	// public Map<String, Long> aggregateSeries(Map<String, Long> quantities,
	// Map<String, Object> series) {

	// SparkSession spark = SparkSession.builder().appName("Java Spark SQL basic
	// example")
	// .config("spark.some.config.option", "some-value").getOrCreate();

	// String path =
	// "https://cdn.jsdelivr.net/gh/highcharts/highcharts@v7.0.0/samples/data/usdeur.json";

	// Dataset<Row> df = spark.read().json(path);
	// // df.show();
	// // // +----+-------+
	// // // | age| name|
	// // // +----+-------+
	// // // |null|Michael|
	// // // | 30| Andy|
	// // // | 19| Justin|
	// // // +----+-------+
	// // df.printSchema();
	// // // root
	// // // |-- age: long (nullable = true)
	// // // |-- name: string (nullable = true)

	// // df.select("name").show();
	// // // +-------+
	// // // | name|
	// // // +-------+
	// // // |Michael|
	// // // | Andy|
	// // // | Justin|
	// // // +-------+

	// // // Select everybody, but increment the age by 1
	// // df.select(col("name"), col("age").plus(1)).show();
	// // // +-------+---------+
	// // // | name|(age + 1)|
	// // // +-------+---------+
	// // // |Michael| null|
	// // // | Andy| 31|
	// // // | Justin| 20|
	// // // +-------+---------+

	// // // Select people older than 21
	// // df.filter(col("age").gt(21)).show();
	// // // +---+----+
	// // // |age|name|
	// // // +---+----+
	// // // | 30|Andy|
	// // // +---+----+

	// // // Count people by age
	// // df.groupBy("age").count().show();
	// // // +----+-----+
	// // // | age|count|
	// // // +----+-----+
	// // // | 19| 1|
	// // // |null| 1|
	// // // | 30| 1|
	// // +----+-----+
	// // Dataset<Row> df_start =
	// // df.groupBy("value").min("start").withColumnRenamed("min(start)",
	// // "start").withColumnRenamed("value", "value_start");
	// // Dataset<Row> df_end =
	// // df.groupBy("value").max("end").withColumnRenamed("max(end)",
	// // "end").withColumnRenamed("value", "value_end");

	// // Dataset<Row> df_combined = df_start.join(df_end,
	// //
	// df_start.col("value_start").equalTo(df_end.col("value_end"))).drop("value_end").withColumnRenamed("value_start",
	// // "value").orderBy("value");

	// return null;
	// }

}